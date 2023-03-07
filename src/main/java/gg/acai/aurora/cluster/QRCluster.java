package gg.acai.aurora.cluster;

import gg.acai.aurora.Serializer;

import java.util.List;

/**
 * @author Clouke
 * @since 07.03.2023 03:51
 * © Aurora - All Rights Reserved
 */
public interface QRCluster<T> extends Iterable<T>, Serializer {

  Group<T> cluster(T t);

  Group<T> findClosestCluster(T t);

  List<Group<T>> clusters();

  int size();

  void clean();

}
