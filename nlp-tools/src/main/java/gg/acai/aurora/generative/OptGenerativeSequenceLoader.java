package gg.acai.aurora.generative;

import com.google.gson.reflect.TypeToken;
import gg.acai.aurora.Encoder;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.generative.stream.EventStream;
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
 * @since 05.07.2023 22:53
 * © Aurora - All Rights Reserved
 */
public class OptGenerativeSequenceLoader implements OverridableLoader<OptGenerativeSequence> {

  private final Encoder encoder;
  private final EventStream<String> eventStream;
  private final Tokenizer tokenizer;
  private final Tokenizer outputTokenizer;

  public OptGenerativeSequenceLoader(Encoder encoder, EventStream<String> eventStream, Tokenizer tokenizer, Tokenizer outputTokenizer) {
    this.encoder = encoder;
    this.eventStream = eventStream;
    this.tokenizer = tokenizer;
    this.outputTokenizer = outputTokenizer;
  }

  @Override
  public OptGenerativeSequence load(String json) {
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
        System.out.println("No activation function specified, using softmax");
        return ActivationFunction.SOFTMAX;
      });

    String name = obj.getString("name");
    float temperature = (float) obj.getDouble("temperature");
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

    if (outputTokenizer != null) {
      Map<String, Integer> outputIndices = GsonSpec.standard()
        .fromJson(
          obj.getString("outputTokenizer"),
          typeToken.getType()
        );
      if (outputTokenizer instanceof AbstractTokenizerModel) ((AbstractTokenizerModel) outputTokenizer).add(outputIndices);
    }

    if (tokenizer instanceof AbstractTokenizerModel) ((AbstractTokenizerModel) tokenizer).add(globalIndices);

    return new OptGenerativeSequenceBuilder()
      .name(name)
      .encoder(encoder)
      .eventStream(eventStream)
      .tokenizer(tokenizer)
      .outputTokenizer(outputTokenizer)
      .from(model)
      .activation(activation)
      .temperature(temperature)
      .epochs(epochs)
      .learningRate(learningRate)
      .hiddenLayerNodes(hiddenLayerNodes)
      .seed(seed)
      .build();
  }
}
