package gg.acai.aurora.cluster.hierarchy;

import gg.acai.acava.Requisites;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 09.04.2023 00:25
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterFamily {

  /**
   * The name of the cluster family
   */
  private final String label;

  /**
   * The base point of the cluster family
   */
  private final double centroid;

  /**
   * The distances from the centroid to each node (x, distance)
   */
  private final Map<Double, Double> nodeDistances;

  public HierarchyClusterFamily(String label, double centroid, Map<Double, Double> distances) {
    Requisites.requireNonNull(label, "label name cannot be null");
    Requisites.checkArgument(centroid >= 0, "centroid cannot be negative");
    Requisites.requireNonNull(distances, "distances cannot be null");
    this.label = label;
    this.centroid = centroid;
    this.nodeDistances = distances;
  }

  public HierarchyClusterFamily(String label, double centroid) {
    this(label, centroid, new HashMap<>());
  }

  public void addNode(double value) {
    nodeDistances.put(value, Math.abs(centroid - value));
  }

  public String label() {
    return label;
  }

  public double centroid() {
    return centroid;
  }

  public double average() {
    double sum = 0;
    for (double node : nodeDistances.keySet()) {
      sum += node;
    }
    return sum / nodeDistances.size();
  }

  public double averageDistance() {
    double sum = 0;
    for (double distance : nodeDistances.values()) {
      sum += distance;
    }
    return sum / nodeDistances.size();
  }

  public double ratioOf(HierarchyClusterFamily other) {
    return (double) size() / (double) other.size();
  }

  public double distanceRatioOf(HierarchyClusterFamily other) {
    return averageDistance() / other.averageDistance();
  }

  public Map<Double, Double> distances() {
    return nodeDistances;
  }

  public int size() {
    return nodeDistances.size();
  }

  @Override
  public String toString() {
    return "HierarchyClusterFamily{" +
      "name='" + label + '\'' +
      ", centroid=" + centroid +
      ", distances=" + nodeDistances +
      '}';
  }
}