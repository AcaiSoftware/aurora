package gg.acai.aurora.ml;

/**
 * @author Clouke
 * @since 01.04.2023 15:38
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Predictable {
  double[] predict(double[] input);
}
