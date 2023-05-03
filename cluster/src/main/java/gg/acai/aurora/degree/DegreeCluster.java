package gg.acai.aurora.degree;

import gg.acai.aurora.Serializer;

import java.util.List;

/**
 * A degree clusterer which holds a high degree of similarity between the objects in the cluster.
 *
 * @author Clouke
 * @since 07.03.2023 03:51
 * © Aurora - All Rights Reserved
 */
public interface DegreeCluster<T> extends Iterable<T>, Serializer {

  /**
   * Clusters the given object into its respective cluster group,
   * <p> if no group exists, a new one is created.
   *
   * @param t The type to cluster
   * @return Returns the clustered group
   */
  DegreeGroup<T> cluster(T t);

  /**
   * Finds the closest cluster group to the given object.
   *
   * @param t The type to find the closest cluster group to
   * @return Returns the closest cluster group
   */
  DegreeGroup<T> findClosestCluster(T t);

  /**
   * Gets a list of all the cluster groups.
   *
   * @return Returns a list of all the cluster groups
   */
  List<DegreeGroup<T>> clusters();

  /**
   * Gets the size of the cluster.
   *
   * @return Returns the size of the cluster
   */
  int size();

  /**
   * Cleans the cluster by removing all empty groups.
   */
  void clean();

}
