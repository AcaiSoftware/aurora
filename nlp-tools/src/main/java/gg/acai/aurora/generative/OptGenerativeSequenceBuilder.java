package gg.acai.aurora.generative;

import gg.acai.aurora.Encoder;
import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.OneHotEncoder;
import gg.acai.aurora.generative.stream.EventStream;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.token.Tokenizer;

import java.util.List;

/**
 * @author Clouke
 * @since 05.07.2023 22:27
 * © Aurora - All Rights Reserved
 */
public class OptGenerativeSequenceBuilder {

  private static final Encoder ONE_HOT = new OneHotEncoder();

  public String name;
  public Tokenizer tokenizer;
  /**
   * Optional field,
   * may optimize the training process if the size of the output vocab size is smaller than the input vocab size
   */
  public Tokenizer outputTokenizer;
  public Encoder encoder = ONE_HOT;
  public EventStream<String> eventStream;
  public ActivationFunction activation;
  public float temperature = 0.5f;
  public int epochs;
  public double learningRate = 0.01;
  public int hiddenLayerNodes = 64;
  public long seed = System.currentTimeMillis();
  public List<EpochAction<NeuralNetwork>> actions;
  public NeuralNetworkModel pretrained;
  public int batchSize = -1;

  public OptGenerativeSequenceBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OptGenerativeSequenceBuilder tokenizer(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    return this;
  }

  public OptGenerativeSequenceBuilder outputTokenizer(Tokenizer outputTokenizer) {
    this.outputTokenizer = outputTokenizer;
    return this;
  }

  public OptGenerativeSequenceBuilder batchSize(int batchSize) {
    this.batchSize = batchSize;
    return this;
  }

  public OptGenerativeSequenceBuilder encoder(Encoder encoder) {
    this.encoder = encoder;
    return this;
  }

  public OptGenerativeSequenceBuilder eventStream(EventStream<String> eventStream) {
    this.eventStream = eventStream;
    return this;
  }

  public OptGenerativeSequenceBuilder activation(ActivationFunction activation) {
    this.activation = activation;
    return this;
  }

  public OptGenerativeSequenceBuilder temperature(float temperature) {
    this.temperature = temperature;
    return this;
  }

  public OptGenerativeSequenceBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public OptGenerativeSequenceBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public OptGenerativeSequenceBuilder hiddenLayerNodes(int hiddenLayerNodes) {
    this.hiddenLayerNodes = hiddenLayerNodes;
    return this;
  }

  public OptGenerativeSequenceBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  public OptGenerativeSequenceBuilder actions(List<EpochAction<NeuralNetwork>> actions) {
    this.actions = actions;
    return this;
  }

  public OptGenerativeSequenceBuilder from(NeuralNetworkModel pretrained) {
    this.pretrained = pretrained;
    return this;
  }

  public OptGenerativeSequence build() {
    if (!tokenizer.supportsIndexing()) throw new IllegalStateException("Tokenizer must support indexing");
    return new OptGenerativeSequence(this);
  }

}
