package gg.acai.aurora;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Clouke
 * @since 02.03.2023 12:40
 * © Aurora - All Rights Reserved
 */
public final class GsonSpec {

  private static final Gson STANDARD = new Gson();

  private static final Gson PRETTY = new GsonBuilder()
    .setPrettyPrinting()
    .create();

  public static Gson standard() {
    return STANDARD;
  }

  public static Gson pretty() {
    return PRETTY;
  }
}
