package gg.acai.aurora.density;

import gg.acai.aurora.Clusterer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Clouke
 * @since 24.04.2023 15:29
 * © Aurora - All Rights Reserved
 */
public class DensityClusterer implements Clusterer {

  private final List<Node> nodes;

  public DensityClusterer(List<Node> nodes) {
    this.nodes = nodes;
  }

  public DensityClusterer() {
    this.nodes = new ArrayList<>();
  }

  public DensityClusterer addNode(Node node) {
    this.nodes.add(node);
    return this;
  }

  public DensityClusterer addNode(double x, double y) {
    this.nodes.add(new Node(nodes.size(), x, y));
    return this;
  }

  public List<List<Node>> cluster(double radius) {
    if (nodes.isEmpty()) {
      throw new IllegalStateException("No nodes to cluster");
    }
    return cluster(nodes, radius);
  }

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
