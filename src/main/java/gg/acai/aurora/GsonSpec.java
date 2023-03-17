package gg.acai.aurora;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gg.acai.aurora.ml.ActivationFunction;
import gg.acai.aurora.ml.ActivationFunctionSerializer;
import gg.acai.aurora.ml.lang.ChatbotSerializer;
import gg.acai.aurora.ml.lang.ChatbotV2;

/**
 * @author Clouke
 * @since 02.03.2023 12:40
 * © Aurora - All Rights Reserved
 */
public final class GsonSpec {

  private static final TypeToken<ChatbotV2> CHATBOT_TYPE = new TypeToken<ChatbotV2>() {};

  private static final Gson STANDARD = new GsonBuilder()
    .registerTypeAdapter(ActivationFunction.class, new ActivationFunctionSerializer())
    .registerTypeAdapter(CHATBOT_TYPE.getType(), new ChatbotSerializer())
    .create();

  private static final Gson PRETTY = new GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(ActivationFunction.class, new ActivationFunctionSerializer())
    .registerTypeAdapter(CHATBOT_TYPE.getType(), new ChatbotSerializer())
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

  public static TypeToken<ChatbotV2> chatbotType() {
    return CHATBOT_TYPE;
  }

}
