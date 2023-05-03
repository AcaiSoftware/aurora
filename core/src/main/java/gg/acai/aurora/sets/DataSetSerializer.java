package gg.acai.aurora.sets;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author Clouke
 * @since 02.05.2023 15:15
 * © Aurora - All Rights Reserved
 */
public class DataSetSerializer implements JsonSerializer<DataSet>, JsonDeserializer<DataSet> {

  @Override
  public JsonElement serialize(DataSet dataSet, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    JsonArray inputs = new JsonArray();
    JsonArray targets = new JsonArray();

    for (double[] row : dataSet.inputs()) {
      JsonArray inputRow = new JsonArray();
      for (double inputVal : row)
        inputRow.add(new JsonPrimitive(inputVal));
      inputs.add(inputRow);
    }

    for (double[] row : dataSet.targets()) {
      JsonArray targetRow = new JsonArray();
      for (double targetVal : row)
        targetRow.add(new JsonPrimitive(targetVal));
      targets.add(targetRow);
    }

    jsonObject.add("inputs", inputs);
    jsonObject.add("targets", targets);

    return jsonObject;
  }

  @Override
  public DataSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    JsonArray inputsArray = jsonObject.getAsJsonArray("inputs");
    JsonArray targetsArray = jsonObject.getAsJsonArray("targets");

    double[][] inputs = new double[inputsArray.size()][];
    double[][] targets = new double[targetsArray.size()][];

    for (int i = 0; i < inputsArray.size(); i++) {
      JsonArray inputRow = inputsArray.get(i).getAsJsonArray();
      inputs[i] = new double[inputRow.size()];
      for (int j = 0; j < inputRow.size(); j++)
        inputs[i][j] = inputRow.get(j).getAsDouble();
    }

    for (int i = 0; i < targetsArray.size(); i++) {
      JsonArray targetRow = targetsArray.get(i).getAsJsonArray();
      targets[i] = new double[targetRow.size()];
      for (int j = 0; j < targetRow.size(); j++)
        targets[i][j] = targetRow.get(j).getAsDouble();
    }

    DataSet dataSet = new DataSet();
    dataSet.add(inputs, targets);
    return dataSet;
  }

}
