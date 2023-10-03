package gg.acai.aurora.nextseq;

import gg.acai.aurora.Encoder;
import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.OneHotEncoder;
import gg.acai.aurora.generative.stream.EventStream;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 17.07.2023 00:34
 * © Aurora - All Rights Reserved
 */
public class NextSequenceBuilder {

  private static final Encoder ONE_HOT = new OneHotEncoder();

  protected String name;
  protected Tokenizer tokenizer;
  protected Encoder encoder = ONE_HOT;
  protected EventStream<String> eventStream;
  protected ActivationFunction activation;
  protected float temperature = 0.5f;
  protected int epochs;
  protected double learningRate = 0.01;
  protected int hiddenLayerNodes = 64;
  protected long seed = System.currentTimeMillis();
  protected EpochAction<NeuralNetwork>[] actions;
  protected NeuralNetworkModel pretrained;

  public NextSequenceBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NextSequenceBuilder tokenizer(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    return this;
  }

  public NextSequenceBuilder encoder(Encoder encoder) {
    this.encoder = encoder;
    return this;
  }

  public NextSequenceBuilder eventStream(EventStream<String> eventStream) {
    this.eventStream = eventStream;
    return this;
  }

  public NextSequenceBuilder activation(ActivationFunction activation) {
    this.activation = activation;
    return this;
  }

  public NextSequenceBuilder temperature(float temperature) {
    this.temperature = temperature;
    return this;
  }

  public NextSequenceBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public NextSequenceBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  @SafeVarargs
  public final NextSequenceBuilder actions(EpochAction<NeuralNetwork>... actions) {
    this.actions = actions;
    return this;
  }

  public NextSequenceBuilder hiddenLayerNodes(int hiddenLayerNodes) {
    this.hiddenLayerNodes = hiddenLayerNodes;
    return this;
  }

  public NextSequenceBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  public NextSequenceBuilder from(NeuralNetworkModel pretrained) {
    this.pretrained = pretrained;
    return this;
  }

  public NextSequence build() {
    if (!tokenizer.supportsIndexing()) throw new IllegalStateException("Tokenizer must support indexing");
    return new NextSequence(this);
  }

}
