package gg.acai.aurora.density;

/**
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

  public int id() {
    return id;
  }

  public double x() {
    return x;
  }

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