package gg.acai.aurora.lvq;

import gg.acai.acava.commons.Attributes;

/**
 * @author Clouke
 * @since 29.04.2023 10:56
 * © Aurora - All Rights Reserved
 */
public interface LVQ {

  int classify(double[] input);

  Attributes attributes();

}
