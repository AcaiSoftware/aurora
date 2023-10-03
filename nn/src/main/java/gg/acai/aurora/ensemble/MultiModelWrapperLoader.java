package gg.acai.aurora.ensemble;

import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.model.ModelLoader;
import gg.acai.aurora.model.OverridableLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 11.09.2023 00:40
 * © Aurora - All Rights Reserved
 */
public class MultiModelWrapperLoader implements OverridableLoader<MultiModelWrapper> {
  @Override
  public MultiModelWrapper load(String json) {
    JSONObject obj = new JSONObject(json);
    String name = obj.getString("name");
    JSONArray networks = obj.getJSONArray("networks");
    List<NeuralNetworkModel> temp = new ArrayList<>();
    for (int i = 0; i < networks.length(); i++) {
      String network = networks.getString(i);
      try (ModelLoader loader = new ModelLoader(network)) {
        temp.add(loader.load(NeuralNetworkModel.class));
      }
    }

    List<NeuralNetwork> models = new ArrayList<>();
    temp.forEach(model -> {
      NeuralNetwork nn = new NeuralNetworkBuilder()
        .from(model)
        .build();
      models.add(nn);
    });

    boolean partitioning = obj.getBoolean("partitioning");
    double partitionRate = obj.getDouble("partitionRate");
    int outputSize = obj.getInt("outputSize");
    int trainCount = obj.getInt("trainCount");

    return new MultiModelWrapper(
      name,
      models,
      partitioning,
      partitionRate,
      outputSize,
      trainCount
    );
  }
}
