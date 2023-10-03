package gg.acai.aurora.nextseq;

import gg.acai.aurora.Encoder;
import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.NeuralNetworkTrainer;
import gg.acai.aurora.generative.stream.EventStream;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.Model;
import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 17.07.2023 00:33
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractNextSequence implements Model {

  protected NeuralNetworkTrainer network;

  protected String name;
  protected String saveDirectory;
  protected boolean saveOnClose;

  protected Tokenizer tokenizer;
  protected Encoder encoder;
  protected ActivationFunction activation;
  protected EventStream<String> eventStream;
  protected float temperature;
  protected int epochs;
  protected double learningRate;
  protected int hiddenLayerNodes;
  protected long seed;

  public AbstractNextSequence(NextSequenceBuilder builder) {
    this.name = builder.name;
    this.tokenizer = builder.tokenizer;
    this.activation = builder.activation;
    this.temperature = builder.temperature;
    this.epochs = builder.epochs;
    this.learningRate = builder.learningRate;
    this.hiddenLayerNodes = builder.hiddenLayerNodes;
    this.seed = builder.seed;
    this.encoder = builder.encoder;
    this.eventStream = builder.eventStream;
    NeuralNetworkModel model = builder.pretrained;
    if (model != null) {
      this.network = new NeuralNetworkBuilder()
        .from(builder.pretrained)
        .build();
    }
  }

  @Override
  public Model name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    this.saveOnClose = saveOnClose;
    return this;
  }

  @Override
  public String version() {
    return "1.0.0";
  }

  @Override
  public String saveDirectoryPath() {
    return saveDirectory;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void close() {
    if (saveOnClose) {
      save();
    }
  }

  public Tokenizer tokenizer() {
    return tokenizer;
  }

  @Override
  public String serialize() {
    return null;
  }

}
