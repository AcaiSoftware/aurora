package gg.acai.aurora.hierarchy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Clouke
 * @since 09.04.2023 17:50
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterBuilder {

  private Set<HierarchyClusterFamily> tree;
  private int reqLearnSamples = 5;
  private double shield = -1.0;

  public HierarchyClusterBuilder tree(Set<HierarchyClusterFamily> tree) {
    this.tree = tree;
    return this;
  }

  public HierarchyClusterBuilder addFamilies(HierarchyClusterFamily... families) {
    Optional.ofNullable(tree)
      .orElseGet(() -> tree = new HashSet<>())
      .addAll(Arrays.asList(families));
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
