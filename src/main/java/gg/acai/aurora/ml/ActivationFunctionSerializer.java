package gg.acai.aurora.ml;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author Bendik Brissach
 * @since 11.03.2023 22:37
 * © Aurora - All Rights Reserved
 */
public final class ActivationFunctionSerializer implements JsonSerializer<ActivationFunction>, JsonDeserializer<ActivationFunction> {

  @Override
  public ActivationFunction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
    throws JsonParseException {
      return ActivationFunction.of(jsonElement.getAsString());
  }

  @Override
  public JsonElement serialize(ActivationFunction activationFunction, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(activationFunction.toString());
  }
}
