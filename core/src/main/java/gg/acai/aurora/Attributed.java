package gg.acai.aurora;

import gg.acai.acava.commons.Attributes;

/**
 * @author Clouke
 * @since 10.06.2023 02:39
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Attributed {
  /**
   * Gets the attributes of this attributed marker.
   *
   * @return Returns the attributes of this attributed marker.
   */
  Attributes attributes();
}
