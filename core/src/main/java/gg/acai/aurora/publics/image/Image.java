package gg.acai.aurora.publics.image;

/**
 * @author Clouke
 * @since 24.01.2023 13:41
 * © Acai - All Rights Reserved
 */
@FunctionalInterface
public interface Image {

  /**
   * Draws a visualisation of the data graph.
   *
   * @return Returns the visualisation
   */
  String draw();

  /**
   * Prints the visualisation of the data graph.
   */
  default void print() {
    String graph = draw();
    System.out.println(graph);
  }

  /**
   * Gets the description of this data graph.
   *
   * @return Returns the description
   */
  default String description() {
    return "Visualisation of a data graph";
  }

}
