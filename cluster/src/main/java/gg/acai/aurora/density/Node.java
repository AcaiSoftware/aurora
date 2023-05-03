package gg.acai.aurora.density;

/**
 * A single node holding an id, x coordinate, and y coordinate.
 *
 * @author Clouke
 * @since 03.05.2023 15:58
 * © Aurora - All Rights Reserved
 */
public class Node {

  private final int id;
  private final double x;
  private final double y;

  public Node(int id, double x, double y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the id of this node.
   *
   * @return Returns the id of this node
   */
  public int id() {
    return id;
  }

  /**
   * Gets the x coordinate of this node.
   *
   * @return Returns the x coordinate of this node
   */
  public double x() {
    return x;
  }

  /**
   * Gets the y coordinate of this node.
   *
   * @return Returns the y coordinate of this node
   */
  public double y() {
    return y;
  }

  @Override
  public String toString() {
    return "Node{" +
      "id=" + id +
      ", x=" + x +
      ", y=" + y +
      '}';
  }
}