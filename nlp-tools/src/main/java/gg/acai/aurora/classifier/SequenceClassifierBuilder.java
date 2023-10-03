package gg.acai.aurora.classifier;

import gg.acai.aurora.Encoder;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.OneHotEncoder;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 25.08.2023 21:45
 * © Aurora - All Rights Reserved
 */
public class SequenceClassifierBuilder {

  private static final Encoder ONE_HOT = new OneHotEncoder();

  public String name;
  public Tokenizer tokenizer;
  public Encoder encoder = ONE_HOT;
  public ActivationFunction activation;
  public int epochs;
  public double learningRate = 0.01;
  public int hiddenLayerNodes = 64;
  public long seed = System.currentTimeMillis();
  public NeuralNetworkModel pretrained;

  public SequenceClassifierBuilder name(String name) {
    this.name = name;
    return this;
  }

  public SequenceClassifierBuilder tokenizer(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    return this;
  }

  public SequenceClassifierBuilder encoder(Encoder encoder) {
    this.encoder = encoder;
    return this;
  }

  public SequenceClassifierBuilder activation(ActivationFunction activation) {
    this.activation = activation;
    return this;
  }

  public SequenceClassifierBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public SequenceClassifierBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public SequenceClassifierBuilder hiddenLayerNodes(int hiddenLayerNodes) {
    this.hiddenLayerNodes = hiddenLayerNodes;
    return this;
  }

  public SequenceClassifierBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  public SequenceClassifierBuilder from(NeuralNetworkModel pretrained) {
    this.pretrained = pretrained;
    return this;
  }

  public SequenceClassifier build() {
    if (!tokenizer.supportsIndexing()) throw new IllegalStateException("Tokenizer must support indexing");
    return new SequenceClassifier(this);
  }

}
