package gg.acai.aurora.earlystop;

import gg.acai.aurora.Attributes;

/**
 * @author Clouke
 * @since 23.04.2023 03:47
 * © Aurora - All Rights Reserved
 */
public interface EarlyStop {

  void tick(Attributes attributes);

  boolean shouldStop();

}
