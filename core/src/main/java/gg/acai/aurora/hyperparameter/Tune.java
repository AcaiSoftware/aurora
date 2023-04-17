package gg.acai.aurora.hyperparameter;

/**
 * @author Clouke
 * @since 16.04.2023 21:07
 * © Aurora - All Rights Reserved
 */
public interface Tune {

  double learningRate();

  int epochs();

  int layers();

}
