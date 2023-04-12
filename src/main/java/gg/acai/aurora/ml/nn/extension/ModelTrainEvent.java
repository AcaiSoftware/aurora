package gg.acai.aurora.ml.nn.extension;

/**
 * @author Clouke
 * @since 08.02.2023 13:43
 * © Acava - All Rights Reserved
 */
@FunctionalInterface
public interface ModelTrainEvent {
  void onTrain(TrainingTickEvent event);
}
