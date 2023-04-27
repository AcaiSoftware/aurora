package gg.acai.aurora;

import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.Predictable;

/**
 * @author Clouke
 * @since 09.04.2023 20:05
 * © Aurora - All Rights Reserved
 */
public interface Clusterer extends Predictable, MLContextProvider {

  @Override
  default MLContext context() {
    return MLContext.CLUSTERING;
  }

}
