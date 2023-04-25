package gg.acai.aurora;

/**
 * @author Clouke
 * @since 02.03.2023 13:09
 * © Aurora - All Rights Reserved
 */
public final class NeuralNetworkFactory {

  public static TrainingBuilder training() {
    return new TrainingBuilder();
  }

}