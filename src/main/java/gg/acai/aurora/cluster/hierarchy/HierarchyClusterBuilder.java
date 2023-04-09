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
  private int reqLearnSamples = 5;
  private double shield = -1.0;

  public HierarchyClusterBuilder tree(Set<HierarchyClusterFamily> families) {
    this.tree.addAll(families);
    return this;
  }

  public HierarchyClusterBuilder addFamilies(HierarchyClusterFamily... families) {
    tree.addAll(Arrays.asList(families));
    return this;
  }

  public HierarchyClusterBuilder learnAfter(int samples) {
    this.reqLearnSamples = samples;
    return this;
  }

  public HierarchyClusterBuilder shield(double shield) {
    this.shield = shield;
    return this;
  }

  public HierarchyClusterClassifier build() {
    return new HierarchyClusterClassifier(tree, reqLearnSamples, shield);
  }

}
