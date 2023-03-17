package gg.acai.aurora;

import gg.acai.aurora.image.Image;

/**
 * @author Clouke
 * @since 24.01.2023 14:45
 * © Acai - All Rights Reserved
 */
@FunctionalInterface
public interface Display {

  /**
   * @return Returns a new graph of the data
   */
  Image toImage();

}
