package gg.acai.aurora.lvq;

import gg.acai.acava.commons.Attributes;

/**
 * @author Clouke
 * @since 29.04.2023 10:56
 * © Aurora - All Rights Reserved
 */
public interface LVQ {

  /**
   * Classifies the input and returns the index of the winning neuron.
   *
   * @param input the input to classify
   * @return Returns the index of the winning neuron
   */
  int classify(double[] input);

  /**
   * Gets the attributes of this LVQ.
   *
   * @return Returns the attributes of this LVQ.
   */
  Attributes attributes();

}
