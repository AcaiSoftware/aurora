package gg.acai.aurora.cluster.degree;

import gg.acai.aurora.Serializer;

import java.util.List;

/**
 * @author Clouke
 * @since 07.03.2023 03:51
 * © Aurora - All Rights Reserved
 */
public interface DegreeCluster<T> extends Iterable<T>, Serializer {

  DegreeGroup<T> cluster(T t);

  DegreeGroup<T> findClosestCluster(T t);

  List<DegreeGroup<T>> clusters();

  int size();

  void clean();

}
