package gg.acai.aurora.generative;

import gg.acai.aurora.*;
import gg.acai.aurora.generative.stream.EventStream;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.model.Model;
import gg.acai.aurora.token.Tokenizer;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Clouke
 * @since 05.07.2023 22:31
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractOptGenerativeSequence implements Model {

  protected NeuralNetworkTrainer network;

  protected String name;
  protected String saveDirectory;
  protected boolean saveOnClose;

  protected Tokenizer tokenizer;
  protected Tokenizer outputTokenizer;
  protected Encoder encoder;
  protected ActivationFunction activation;
  protected EventStream<String> eventStream;
  protected float temperature;
  protected int epochs;
  protected double learningRate;
  protected int hiddenLayerNodes;
  protected long seed;
  protected List<EpochAction<NeuralNetwork>> actions;
  protected int batchSize;

  public AbstractOptGenerativeSequence(OptGenerativeSequenceBuilder builder) {
    this.name = builder.name;
    this.tokenizer = builder.tokenizer;
    this.outputTokenizer = builder.outputTokenizer;
    this.activation = builder.activation;
    this.temperature = builder.temperature;
    this.epochs = builder.epochs;
    this.learningRate = builder.learningRate;
    this.hiddenLayerNodes = builder.hiddenLayerNodes;
    this.seed = builder.seed;
    this.encoder = builder.encoder;
    this.eventStream = builder.eventStream;
    this.actions = builder.actions;
    this.batchSize = builder.batchSize;
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
    JSONObject json = new JSONObject();
    name = name == null ? "OPTGenSeq" : name;
    json.put("name", name);
    json.put("saveDirectory", saveDirectory);
    json.put("saveOnClose", saveOnClose);
    json.put("version", version());
    json.put("tokenizer", tokenizer.serialize());
    if (outputTokenizer != null)
      json.put("outputTokenizer", outputTokenizer.serialize());
    json.put("activation", activation.toString());
    json.put("temperature", temperature);
    json.put("epochs", epochs);
    json.put("learningRate", learningRate);
    json.put("hiddenLayerNodes", hiddenLayerNodes);
    json.put("seed", seed);
    json.put("network", network.toModel(name).serialize());

    return json.toString();
  }

}
