package gg.acai.aurora;

/**
 * @author Clouke
 * @since 10.09.2023 03:04
 * © Aurora - All Rights Reserved
 */
public interface Environment {

  void reset();

  void step(int action);

  double[] takeAction(double[] action);

  double[] state();

  boolean episodeOver();

  int actionSpace();

  int stateSpace();

  int maxSteps();

  int currentStep();

  double reward();

}
