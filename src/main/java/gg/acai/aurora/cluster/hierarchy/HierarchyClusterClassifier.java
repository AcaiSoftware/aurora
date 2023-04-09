package gg.acai.aurora.cluster.hierarchy;

import gg.acai.acava.Requisites;
import gg.acai.aurora.Evaluator;
import gg.acai.aurora.cluster.Clusterer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A recursive hierarchical cluster classifier, classifying nodes
 * into families based on their distance from the centroid or closest node.
 *
 * @author Clouke
 * @since 09.04.2023 00:25
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterClassifier implements Clusterer, Iterable<HierarchyClusterFamily> {

  private final Set<HierarchyClusterFamily> tree;
  private final int minDistance;
  private final double threshold;

  public HierarchyClusterClassifier(Set<HierarchyClusterFamily> families, int minDistance, double threshold) {
    this.tree = families;
    this.minDistance = minDistance;
    this.threshold = threshold;
  }

  public HierarchyClusterClassifier(int minDistance) {
    this(new HashSet<>(), minDistance, -1.0);
  }

  public HierarchyClusterClassifier() {
    this(5);
  }

  public HierarchyClusterFamily createFamily(String name, double centroid) {
    HierarchyClusterFamily family = new HierarchyClusterFamily(name, centroid);
    tree.add(family);
    return family;
  }

  @Nonnull
  public HierarchyClusterFamily cluster(double value) {
    Requisites.checkArgument(!tree.isEmpty(), "Cannot add node to empty cluster");
    HierarchyClusterFamily closest = null;
    double closestCentroid = Double.MAX_VALUE;
    for (HierarchyClusterFamily family : tree) {
      double centroidOffset = Math.abs(family.centroid() - value);
      if (centroidOffset < closestCentroid) {
        closestCentroid = centroidOffset;
        closest = family;
      }
      /*
       * Learning phase:
       * - Check for closer nodes in family (if the size meets the requirement)
       * - If found, mark current family as the closest
       * This works as a "splitter" for the cluster groups,
       * allowing it to correctly group nodes that are closer to other nodes,
       * mapping the distance to the closest node from the centroid.
       * (e.g. 0.8 and 1.2, which are closer to 1 than 0 or 2)
       *
       * Example of a cluster group split:
       * Cluster A:        Cluster B:
       *  ●       ●      /  ●      ●
       *      ●    ●    /     ●   ●
       *   ●   ●       /  ●  ●   ●
       *      ●   ●   /  ●   ●
       *   ●     ●   /      ●
       *          Splitter
       */
      Map<Double, Double> distances = family.distances();
      if (distances.size() >= minDistance) {
        double closestNodeDistance = Double.MAX_VALUE;
        for (double node : distances.keySet()) {
          double nodeOffset = Math.abs(node - value);
          if (threshold > 0 && nodeOffset > threshold)
            continue;
          if (nodeOffset < closestNodeDistance) {
            closestNodeDistance = nodeOffset;
            closest = family;
          }
        }
      }
    }

    assert closest != null; {
      closest.addNode(value);
    }

    return closest;
  }

  @Nonnull
  public Set<HierarchyClusterFamily> families() {
    return Collections.unmodifiableSet(tree);
  }

  @Override
  public Iterator<HierarchyClusterFamily> iterator() {
    return tree.iterator();
  }

  @Nonnull
  public Evaluator evaluator() {
    int[] labels = new int[tree.size()];
    double[] data = new double[tree.size()];
    int i = 0;
    for (HierarchyClusterFamily family : tree) {
      labels[i] = i;
      data[i] = family.centroid();
      i++;
    }
    return new HierarchyClusterEvaluator(this, data, labels);
  }

  @Nonnull
  public String draw() {
    StringBuilder builder = new StringBuilder();
    String nodeSymbol = "*";
    String centroidSymbol = "o";
    tree.forEach(family -> {
      builder.append(family.label())
        .append(": ")
        .append("\n");

      Map<Double, Double> distances = family.distances();
      double centroid = family.centroid();
      for (double node : distances.keySet()) {
        int y = (int) Math.round(Math.abs(node - centroid) * 10.0);
        for (int i = 0; i < y; i++) {
          builder.append(" ");
        }
        builder.append(nodeSymbol).append("\n");
      }
      int y = (int) Math.round(family.centroid() * 10.0);
      for (int i = 0; i < y; i++) {
        builder.append(" ");
      }
      builder.append(centroidSymbol).append("\n");
    });

    return builder.toString();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    tree.forEach(family -> builder
      .append(family.label())
      .append(": (")
      .append(family.centroid())
      .append(") - ")
      .append(family.distances())
      .append("\n"));
    return builder.toString();
  }
}
