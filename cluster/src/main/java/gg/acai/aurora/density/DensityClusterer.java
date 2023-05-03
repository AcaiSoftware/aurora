package gg.acai.aurora.density;

import gg.acai.aurora.Clusterer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A density-based clustering algorithm which clusters nodes based on their density.
 *
 * @author Clouke
 * @since 24.04.2023 15:29
 * © Aurora - All Rights Reserved
 */
public class DensityClusterer implements Clusterer {

  private final List<Node> nodes;

  /**
   * Constructs a new DensityClusterer with the given nodes.
   *
   * @param nodes The nodes to cluster
   */
  public DensityClusterer(List<Node> nodes) {
    this.nodes = nodes;
  }

  /**
   * Constructs a new DensityClusterer with no nodes.
   */
  public DensityClusterer() {
    this.nodes = new ArrayList<>();
  }

  /**
   * Adds a node to the clusterer.
   *
   * @param node The node to add
   * @return The clusterer
   */
  public DensityClusterer addNode(Node node) {
    this.nodes.add(node);
    return this;
  }

  /**
   * Adds a node to the clusterer.
   *
   * @param x The x coordinate of the node
   * @param y The y coordinate of the node
   * @return The clusterer
   */
  public DensityClusterer addNode(double x, double y) {
    this.nodes.add(new Node(nodes.size(), x, y));
    return this;
  }

  /**
   * Clusters the nodes with the given radius.
   *
   * @param radius The radius to cluster with
   * @return The clusters
   * @throws IllegalStateException If there are no nodes to cluster
   */
  public List<List<Node>> cluster(double radius) throws IllegalStateException {
    if (nodes.isEmpty()) {
      throw new IllegalStateException("No nodes to cluster");
    }
    return cluster(nodes, radius);
  }

  /**
   * Clusters the given nodes with the given radius.
   *
   * @param nodes The nodes to cluster
   * @param radius The radius to cluster with
   * @return Returns the clusters of the nodes with the given radius
   */
  public List<List<Node>> cluster(List<Node> nodes, double radius) {
    List<List<Node>> clusters = new ArrayList<>();
    boolean[] assigned = new boolean[nodes.size()];

    for (int i = 0; i < nodes.size(); i++) {
      if (assigned[i]) {
        continue;
      }

      Node node = nodes.get(i);
      List<Node> cluster = new ArrayList<>();
      cluster.add(node);
      assigned[i] = true;

      for (int j = i + 1; j < nodes.size(); j++) {
        if (assigned[j]) {
          continue;
        }

        Node otherNode = nodes.get(j);
        double distance = Math.sqrt(Math.pow(node.x() - otherNode.x(), 2) + Math.pow(node.y() - otherNode.y(), 2));
        if (distance <= radius) {
          cluster.add(otherNode);
          assigned[j] = true;
        }
      }

      clusters.add(cluster);
    }

    return clusters;
  }


  /**
   * Predicts the clusters of the nodes with the given radius.
   *
   * @param input The input to predict
   * @return Returns the clusters of the nodes
   */
  @Override
  public double[] predict(double[] input) {
    double radius = input[0];
    List<List<Node>> clusters = cluster(radius);
    double[] output = new double[nodes.size()];
    Arrays.fill(output, -1);
    for (int i = 0; i < clusters.size(); i++) {
      List<Node> cluster = clusters.get(i);
      for (Node node : cluster) {
        output[node.id()] = i;
      }
    }
    return output;
  }
}
