package gg.acai.aurora.generative;

import gg.acai.aurora.*;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.token.Tokenizer;

import java.util.*;

/**
 * @author Clouke
 * @since 05.07.2023 22:22
 * © Aurora - All Rights Reserved
 */
public class OptGenerativeSequence extends AbstractOptGenerativeSequence {

  public OptGenerativeSequence(OptGenerativeSequenceBuilder builder) {
    super(builder);
  }

  public void modify(float temperature) {
    this.temperature = temperature;
  }

  public void fit(Map<String, String> samples) {
    List<String> inputWords = new ArrayList<>(samples.keySet());
    List<String> outputWords = new ArrayList<>(samples.values());
    int maxLen = 0;
    for (String word : inputWords) {
      Collection<String> tokens = tokenizer.tokenize(word);
      tokenizer.fit(tokens);
      int len = word.length();
      if (len > maxLen)
        maxLen = len;
    }

    Tokenizer out = outputTokenizer == null ? tokenizer : outputTokenizer;
    for (String word : outputWords) {
      Collection<String> tokens = out.tokenize(word);
      out.fit(tokens);
      int len = word.length();
      if (len > maxLen)
        maxLen = len;
    }

    if (encoder instanceof ResizableEncoder) {
      ((ResizableEncoder) encoder).resize(maxLen);
    }

    int globalSize = tokenizer.size();
    int outputSize = outputTokenizer == null ? globalSize : outputTokenizer.size();
    if (network == null) {
      network = new NeuralNetworkBuilder()
        .layers(mapper -> mapper
          .inputLayers(globalSize)
          .hiddenLayers(hiddenLayerNodes)
          .outputLayers(outputSize))
        .name(name)
        .learningRate(learningRate)
        .epochs(epochs)
        .seed(seed)
        .activationFunction(activation)
        .epochActions(actions == null ? new EpochAction[0] : actions.toArray(new EpochAction[0]))
        .build();
    }

    int size = samples.size();
    double[][] inputs = new double[size][];
    double[][] outputs = new double[size][];
    int i = 0;
    for (Map.Entry<String, String> entry : samples.entrySet()) {
      inputs[i] = encode(entry.getKey());
      outputs[i] = encode(entry.getValue(), outputTokenizer);
      i++;
    }

    network.train(inputs, outputs);
  }

  public String generate(String input, float temperature) {
    double[] encodedInput = encode(input);
    double[] output = network.predict(encodedInput);
    StringBuilder result = new StringBuilder();
    Tokenizer tokenizer = outputTokenizer == null ? this.tokenizer : outputTokenizer;

    for (int node = 0; node < output.length; node++) {
      if (output[node] >= temperature) {
        String next = tokenizer.wordOf(node);
        if (next == null)
          continue;
        result.append(next).append(" ");
        stream(next);
      }
    }
    return result.toString();
  }

  public String generateAny(String input, float temperature, float decayStep)
    throws RuntimeException {
      String result = "";
      int spins = 0;
      while (result.isEmpty()) {
        result = generate(input, temperature);
        temperature -= decayStep;
        spins++;
        if (temperature <= 0.0f && result.isEmpty())
          throw new RuntimeException("Could not generate any output, spins: " + spins);
      }
      return result;
    }

  public String generateAny(String input, float decayStep) {
    return generateAny(input, temperature, decayStep);
  }

  public String generate(String input) {
    return generate(input, temperature);
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

  private void stream(String word) {
    if (eventStream != null) eventStream.emit(word);
  }

}
