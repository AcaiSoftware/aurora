package gg.acai.aurora.cluster.hierarchy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Clouke
 * @since 09.04.2023 17:50
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterBuilder {

  private final Set<HierarchyClusterFamily> tree = new HashSet<>();
  private int minDistance;
  private double threshold;

  public HierarchyClusterBuilder tree(Set<HierarchyClusterFamily> families) {
    this.tree.addAll(families);
    return this;
  }

  public HierarchyClusterBuilder addFamilies(HierarchyClusterFamily... families) {
    tree.addAll(Arrays.asList(families));
    return this;
  }

  public HierarchyClusterBuilder minDistance(int minDistance) {
    this.minDistance = minDistance;
    return this;
  }

  public HierarchyClusterBuilder threshold(double threshold) {
    this.threshold = threshold;
    return this;
  }

  public HierarchyClusterClassifier build() {
    return new HierarchyClusterClassifier(tree, minDistance, threshold);
  }

}
