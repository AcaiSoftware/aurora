package gg.acai.aurora.classifier;

import com.google.gson.reflect.TypeToken;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.Encoder;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.ModelLoader;
import gg.acai.aurora.model.OverridableLoader;
import gg.acai.aurora.token.AbstractTokenizerModel;
import gg.acai.aurora.token.Tokenizer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 25.08.2023 21:55
 * © Aurora - All Rights Reserved
 */
public class SequenceClassifierLoader implements OverridableLoader<SequenceClassifier> {

  private final Encoder encoder;
  private final Tokenizer tokenizer;

  public SequenceClassifierLoader(Tokenizer tokenizer, Encoder encoder) {
    this.tokenizer = tokenizer;
    this.encoder = encoder;
  }

  @Override
  public SequenceClassifier load(String json) {
    JSONObject obj = new JSONObject(json);
    JSONObject networkJson = new JSONObject(obj.getString("network"));
    NeuralNetworkModel model;
    try (ModelLoader loader = new ModelLoader(networkJson.toString())) {
      model = loader.load(NeuralNetworkModel.class);
    } catch (Exception e) {
      throw new RuntimeException("Could not load pre-trained neural network", e);
    }

    ActivationFunction activation = ActivationFunction
      .optionally(obj.getString("activation"))
      .orElseGet(() -> {
        Aurora.logger().warning("No activation function specified, using softmax");
        return ActivationFunction.SOFTMAX;
      });

    String name = obj.getString("name");
    int epochs = obj.getInt("epochs");
    double learningRate = obj.getDouble("learningRate");
    int hiddenLayerNodes = obj.getInt("hiddenLayerNodes");
    long seed = obj.getLong("seed");
    TypeToken<HashMap<String, Integer>> typeToken = new TypeToken<HashMap<String, Integer>>() {};
    Map<String, Integer> globalIndices = GsonSpec.standard()
      .fromJson(
        obj.getString("tokenizer"),
        typeToken.getType()
      );

    if (tokenizer instanceof AbstractTokenizerModel)
      ((AbstractTokenizerModel) tokenizer).add(globalIndices);

    return new SequenceClassifierBuilder()
      .name(name)
      .encoder(encoder)
      .tokenizer(tokenizer)
      .from(model)
      .activation(activation)
      .epochs(epochs)
      .learningRate(learningRate)
      .hiddenLayerNodes(hiddenLayerNodes)
      .seed(seed)
      .build();
  }
}
