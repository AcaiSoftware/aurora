package gg.acai.aurora.classifier;

import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.ResizableEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author Clouke
 * @since 25.08.2023 21:34
 * © Aurora - All Rights Reserved
 */
public class SequenceClassifier extends AbstractSequenceClassifier {

  public SequenceClassifier(SequenceClassifierBuilder builder) {
    super(builder);
  }

  public void fit(Map<String, Integer> samples) {
    List<String> inputWords = new ArrayList<>(samples.keySet());
    int maxLen = 0;
    for (String word : inputWords) {
      Collection<String> tokens = tokenizer.tokenize(word);
      tokenizer.fit(tokens);
      int len = word.length();
      if (len > maxLen)
        maxLen = len;
    }

    if (encoder instanceof ResizableEncoder)
      ((ResizableEncoder) encoder).resize(maxLen);

    int globalSize = tokenizer.size();
    if (network == null) {
      network = new NeuralNetworkBuilder()
        .layers(mapper -> mapper
          .inputLayers(globalSize)
          .hiddenLayers(hiddenLayerNodes)
          .outputLayers(1))
        .name(name)
        .learningRate(learningRate)
        .epochs(epochs)
        .seed(seed)
        .activationFunction(activation)
        .build();
    }

    int size = samples.size();
    double[][] inputs = new double[size][];
    double[][] outputs = new double[size][];
    int i = 0;
    for (Map.Entry<String, Integer> entry : samples.entrySet()) {
      inputs[i] = encode(entry.getKey());
      outputs[i] = new double[]{entry.getValue()};
      i++;
    }

    network.train(inputs, outputs);
  }

  public double predict(String word) {
    double[] input = encode(word);
    double[] output = network.predict(input);
    return output[0];
  }

  public double[] encode(String word) {
    Objects.requireNonNull(tokenizer, "Tokenizer cannot be null");
    return encoder.encode(
      word,
      tokenizer
    );
  }

}
