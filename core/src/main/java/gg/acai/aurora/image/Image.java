package gg.acai.aurora.image;

import gg.acai.aurora.Aurora;

/**
 * @author Clouke
 * @since 24.01.2023 13:41
 * © Acai - All Rights Reserved
 */
@FunctionalInterface
public interface Image {

  String draw();

  default void print() {
    String graph = draw();
    Aurora.log(graph);
  }

  default String description() {
    return "Visualisation of a data graph";
  }

}
