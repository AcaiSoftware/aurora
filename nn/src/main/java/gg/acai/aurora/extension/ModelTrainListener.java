package gg.acai.aurora.extension;

/**
 * @author Clouke
 * @since 08.02.2023 13:43
 * © Acava - All Rights Reserved
 */
@FunctionalInterface
public interface ModelTrainListener {
  void onTrain(TrainingTickEvent event);
}
