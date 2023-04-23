package gg.acai.aurora.hierarchy;

import gg.acai.acava.Requisites;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Clouke
 * @since 09.04.2023 00:25
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterFamily implements Iterable<Map.Entry<Double, Double>> {

  /**
   * An index holder for the cluster families to keep track of their index
   */
  private static int INDEX_HOLDER = 0;

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

  /**
   * The index of this cluster family
   */
  private final int index;

  public HierarchyClusterFamily(String label, double centroid, Map<Double, Double> distances, int index) {
    Requisites.requireNonNull(label, "label name cannot be null");
    Requisites.checkArgument(centroid >= 0, "centroid cannot be negative");
    Requisites.requireNonNull(distances, "distances cannot be null");
    this.label = label;
    this.centroid = centroid;
    this.nodeDistances = distances;
    this.index = index;
  }

  public HierarchyClusterFamily(String label, double centroid) {
    this(label, centroid, new HashMap<>(), INDEX_HOLDER++);
  }

  public void addNode(double value) {
    nodeDistances.put(value, Math.abs(centroid - value));
  }

  public String label() {
    return label;
  }

  public int index() {
    return index;
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
  public Iterator<Map.Entry<Double, Double>> iterator() {
    return nodeDistances.entrySet().iterator();
  }

  @Override
  public String toString() {
    return "HierarchyClusterFamily{" +
      "name='" + label + '\'' +
      ", centroid=" + centroid +
      ", distances=" + nodeDistances +
      '}';
  }

  @Override @SuppressWarnings({"MethodDoesntCallSuperMethod"})
  public HierarchyClusterFamily clone() {
    return new HierarchyClusterFamily(label, centroid, new HashMap<>(nodeDistances), index);
  }

  public static Supplier<Integer> globalIndexes() {
    return () -> INDEX_HOLDER;
  }

  public static void resetIndexes() {
    INDEX_HOLDER = 0;
  }

  public static void setIndexes(int index) {
    INDEX_HOLDER = index;
  }

}
