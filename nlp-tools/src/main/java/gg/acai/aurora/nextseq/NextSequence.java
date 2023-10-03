package gg.acai.aurora.nextseq;

import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.ResizableEncoder;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.policy.AbstractDecayPolicy;
import gg.acai.aurora.publics.io.Bar;
import gg.acai.aurora.token.Tokenizer;

import java.util.*;

/**
 * @author Clouke
 * @since 17.07.2023 00:26
 * © Aurora - All Rights Reserved
 */
public class NextSequence extends AbstractNextSequence {

  private final EpochAction<NeuralNetwork>[] actions;

  public NextSequence(NextSequenceBuilder builder) {
    super(builder);
    this.actions = builder.actions;
  }

  public void fit(Iterable<String> corpus) {
    Set<String> corpSet = new HashSet<>();
    corpus.forEach(corpSet::add);
    corpSet.forEach(s -> {
      Collection<String> tokens = tokenizer.tokenize(s);
      tokenizer.fit(tokens);
    });

    if (encoder instanceof ResizableEncoder) {
      ((ResizableEncoder) encoder).resize(tokenizer.size());
    }

    int globalSize = tokenizer.size();
    if (network == null) {
      network = new NeuralNetworkBuilder()
        .layers(mapper -> mapper
          .inputLayers(globalSize)
          .hiddenLayers(hiddenLayerNodes)
          .outputLayers(globalSize))
        .name(name)
        .learningRate(learningRate)
        .epochs(epochs)
        .seed(seed)
        .activationFunction(activation)
        .printing(Bar.MODERN)
        .learningRateDecay(new AbstractDecayPolicy<>(
          new HashMap<Integer, Double>(){{
            put(0, learningRate);
            put(100, 0.1);
            put(200, 0.01);
            put(400, 0.001);
            put(600, 0.0001);
            put(800, 0.00001);
          }}
        ))
        //.noise(NoiseContext.GAUSSIAN)
        //.weightInitializer(Initializer.HE)
        //.regularization(new L2(0.01))
        .epochActions(actions)
        .build();
    }

    /*
     * Phase:
     *  Seed: "Hello! How "
     *  Output: "are"
     *  Next Output: "you"
     *  Next Output: "doing?"
     */
    int totalTokens = 0;
    for (String word : corpSet)
      totalTokens += tokenizer.tokenizeToArray(word).length - 1;

    Set<Character> chars = new HashSet<>();
    for (String word : corpSet) {
      for (char c : word.toCharArray()) {
        chars.add(c);
      }
    }
    System.out.println("Total Tokens: " + totalTokens);
    System.out.println("Total Chars: " + chars.size());
    System.out.println("Total Words: " + corpSet.size());
    System.out.println("Characters: " + chars);
    double[][] inputs = new double[totalTokens][];
    double[][] outputs = new double[totalTokens][];
    int i = 0;
    for (String word : corpSet) {
      String[] tokens = tokenizer.tokenizeToArray(word);
      for (int j = 0; j < tokens.length - 1; j++) {
        inputs[i] = encode(tokens[j]);
        outputs[i] = encode(tokens[j + 1]);
        i++;
      }
    }

    network.train(
      inputs,
      outputs
    );
  }

  public String generate(String seed, int maxLength, String endingSequence) {
    double[] encodedInput = encode(seed);
    StringBuilder generated = new StringBuilder();
    generated.append(seed).append(" ");
    int i = 0;
    while (i < maxLength) {
      double[] output = network.predict(encodedInput);
      int winner = 0;
      for (int j = 0; j < output.length; j++) {
        if (output[j] > output[winner]) {
          winner = j;
        }
      }

      String word = tokenizer.wordOf(winner);
      if (word == null)
        continue;

      if (word.equals(endingSequence))
        break;

      generated.append(word).append(" ");
      encodedInput = encode(word);
      i++;
    }

    return generated.toString();
  }

  public String generate(String seed, int maxLength, String endingSequence, int topK) {
    double[] encodedInput = encode(seed);
    StringBuilder generated = new StringBuilder();
    generated.append(seed).append(" ");
    int i = 0;
    Random random = new Random();
    while (i < maxLength) {
      double[] output = network.predict(encodedInput);

      // Top-K sampling
      List<Integer> topKIndices = new ArrayList<>();
      for (int j = 0; j < output.length; j++) {
        topKIndices.add(j);
      }
      topKIndices.sort((a, b) -> Double.compare(output[b], output[a]));
      topKIndices = topKIndices.subList(0, topK);

      // Randomly select from the top-K candidates
      int selectedIdx = topKIndices.get(random.nextInt(topK));

      String word = tokenizer.wordOf(selectedIdx);
      if (word == null)
        continue;

      if (word.equals(endingSequence))
        break;

      generated.append(word).append(" ");
      encodedInput = encode(word);
      i++;
    }

    return generated.toString();
  }

  public double[] encode(String word) {
    return encode(word, tokenizer);
  }

  public double[] encode(String word, Tokenizer tokenizer) {
    tokenizer = tokenizer == null ? this.tokenizer : tokenizer;
    Objects.requireNonNull(tokenizer, "Tokenizer cannot be null");
    return encoder.encode(
      word,
      tokenizer
    );
  }

  public double[] encode(List<String> ctx) {
    List<double[]> encodedTokens = new ArrayList<>();
    for (String token : ctx) {
      encodedTokens.add(encode(token));
    }
    double[] encodedContext = new double[encodedTokens.get(0).length];
    for (double[] encodedToken : encodedTokens) {
      for (int i = 0; i < encodedToken.length; i++) {
        encodedContext[i] += encodedToken[i];
      }
    }
    for (int i = 0; i < encodedContext.length; i++) {
      encodedContext[i] /= encodedTokens.size();
    }
    return encodedContext;
  }

  public String randomWord() {
    return tokenizer.wordOf((int) (Math.random() * tokenizer.size()));
  }

}
