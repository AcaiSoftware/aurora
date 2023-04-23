package gg.acai.aurora.em;

import gg.acai.aurora.Clusterer;

import java.util.List;

/**
 * @author Clouke
 * @since 18.04.2023 04:07
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface ExpectationMaximization extends Clusterer {
  /**
   * Gets the cluster of the data points in this EM model.
   *
   * @return Returns cluster of the data points in this EM model.
   */
  List<Integer> cluster();
}
