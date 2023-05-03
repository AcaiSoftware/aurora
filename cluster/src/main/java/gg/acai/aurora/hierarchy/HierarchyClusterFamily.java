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

  /**
   * Constructs a new cluster family with the given label, centroid and distances.
   *
   * @param label The name of the cluster family
   * @param centroid The base point of the cluster family
   * @param distances The distances from the centroid to each node (x, distance)
   * @param index The index of this cluster family
   */
  public HierarchyClusterFamily(String label, double centroid, Map<Double, Double> distances, int index) {
    Requisites.requireNonNull(label, "label name cannot be null");
    Requisites.checkArgument(centroid >= 0, "centroid cannot be negative");
    Requisites.requireNonNull(distances, "distances cannot be null");
    this.label = label;
    this.centroid = centroid;
    this.nodeDistances = distances;
    this.index = index;
  }

  /**
   * Constructs a new cluster family with the given label and centroid.
   *
   * @param label The name of the cluster family
   * @param centroid The base point of the cluster family
   */
  public HierarchyClusterFamily(String label, double centroid) {
    this(label, centroid, new HashMap<>(), INDEX_HOLDER++);
  }

  /**
   * Adds a node to the cluster family with the given value.
   *
   * @param value The value of the node to add
   */
  public void addNode(double value) {
    nodeDistances.put(value, Math.abs(centroid - value));
  }

  /**
   * Gets the label of this cluster family.
   *
   * @return Returns the label of this cluster family
   */
  public String label() {
    return label;
  }

  /**
   * Gets the index of this cluster family.
   *
   * @return Returns the index of this cluster family
   */
  public int index() {
    return index;
  }

  /**
   * Gets the centroid of this cluster family.
   *
   * @return Returns the centroid of this cluster family
   */
  public double centroid() {
    return centroid;
  }

  /**
   * Computes the average of the nodes in this cluster family.
   *
   * @return Returns the average of the nodes in this cluster family
   */
  public double average() {
    double sum = 0;
    for (double node : nodeDistances.keySet()) {
      sum += node;
    }
    return sum / nodeDistances.size();
  }

  /**
   * Computes the average distance of the nodes in this cluster family.
   *
   * @return Returns the average distance of the nodes in this cluster family
   */
  public double averageDistance() {
    double sum = 0;
    for (double distance : nodeDistances.values()) {
      sum += distance;
    }
    return sum / nodeDistances.size();
  }

  /**
   * Computes the ratio of this cluster family to the given cluster family.
   *
   * @param other The cluster family to compute the ratio to
   * @return Returns the ratio of this cluster family to the given cluster family
   */
  public double ratioOf(HierarchyClusterFamily other) {
    return (double) size() / (double) other.size();
  }

  /**
   * Computes the distance ratio of this cluster family to the given cluster family.
   *
   * @param other The cluster family to compute the distance ratio to
   * @return Returns the distance ratio of this cluster family to the given cluster family
   */
  public double distanceRatioOf(HierarchyClusterFamily other) {
    return averageDistance() / other.averageDistance();
  }

  /**
   * Gets a map of the nodes and their distances from the centroid.
   * <p>
   * Represents the format: <b>(node, distance)
   *
   * @return Returns a map of the nodes and their distances from the centroid
   */
  public Map<Double, Double> distances() {
    return nodeDistances;
  }

  /**
   * Gets the size of this cluster family.
   *
   * @return Returns the size of this cluster family
   */
  public int size() {
    return nodeDistances.size();
  }

  /**
   * @return Returns an iterator over the nodes and their distances from the centroid
   */
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

  /**
   * Gets a supplier for the global indexes.
   *
   * @return Returns a supplier for the global indexes
   */
  public static Supplier<Integer> globalIndexes() {
    return () -> INDEX_HOLDER;
  }

  /**
   * Resets the global indexes.
   */
  public static void resetIndexes() {
    INDEX_HOLDER = 0;
  }

  /**
   * Sets the global indexes to the given index.
   *
   * @param index The index to set the global indexes to
   */
  public static void setIndexes(int index) {
    INDEX_HOLDER = index;
  }

}
