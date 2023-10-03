package gg.acai.aurora.classifier;

import gg.acai.aurora.Encoder;
import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.NeuralNetworkTrainer;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.Model;
import gg.acai.aurora.token.Tokenizer;
import org.json.JSONObject;

/**
 * @author Clouke
 * @since 25.08.2023 21:51
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractSequenceClassifier implements Model {

  protected NeuralNetworkTrainer network;

  protected String name;
  protected String saveDirectory;
  protected boolean saveOnClose;

  protected Tokenizer tokenizer;
  protected Encoder encoder;
  protected ActivationFunction activation;
  protected int epochs;
  protected double learningRate;
  protected int hiddenLayerNodes;
  protected long seed;

  public AbstractSequenceClassifier(SequenceClassifierBuilder builder) {
    this.name = builder.name;
    this.tokenizer = builder.tokenizer;
    this.activation = builder.activation;
    this.epochs = builder.epochs;
    this.learningRate = builder.learningRate;
    this.hiddenLayerNodes = builder.hiddenLayerNodes;
    this.seed = builder.seed;
    this.encoder = builder.encoder;
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
    name = name == null ? "SequenceClassifier" : name;
    json.put("name", name);
    json.put("saveDirectory", saveDirectory);
    json.put("saveOnClose", saveOnClose);
    json.put("version", version());
    json.put("tokenizer", tokenizer.serialize());
    json.put("activation", activation.toString());
    json.put("epochs", epochs);
    json.put("learningRate", learningRate);
    json.put("hiddenLayerNodes", hiddenLayerNodes);
    json.put("seed", seed);
    json.put("network", network.toModel(name).serialize());

    return json.toString();
  }
}
