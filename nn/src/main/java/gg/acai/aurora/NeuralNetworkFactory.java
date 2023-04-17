package gg.acai.aurora;

import gg.acai.aurora.model.ModelLoader;

/**
 * @author Clouke
 * @since 02.03.2023 13:09
 * © Aurora - All Rights Reserved
 */
public final class NeuralNetworkFactory {

  public static TrainingBuilder training() {
    return new TrainingBuilder();
  }

  public static ModelLoader loader() {
    return new ModelLoader(() -> NeuralNetworkModel.class);
  }

}