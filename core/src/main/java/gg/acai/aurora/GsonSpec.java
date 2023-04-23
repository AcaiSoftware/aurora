package gg.acai.aurora;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.ActivationFunctionSerializer;

/**
 * @author Clouke
 * @since 02.03.2023 12:40
 * © Aurora - All Rights Reserved
 */
public final class GsonSpec {

  private static final Gson STANDARD = new GsonBuilder()
    .registerTypeAdapter(ActivationFunction.class, new ActivationFunctionSerializer())
    .create();

  private static final Gson PRETTY = new GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(ActivationFunction.class, new ActivationFunctionSerializer())
    .create();

  public static Gson standard() {
    return STANDARD;
  }

  public static Gson pretty() {
    return PRETTY;
  }

  public static Gson get(boolean pretty) {
    return pretty ? pretty() : standard();
  }
}
