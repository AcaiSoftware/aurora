package gg.acai.aurora;

import gg.acai.aurora.publics.image.Image;

/**
 * A display of data, such as a graph.
 *
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
