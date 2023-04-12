package gg.acai.aurora.ml.cluster.hierarchy;

import gg.acai.acava.Requisites;
import gg.acai.aurora.Evaluator;
import gg.acai.aurora.ml.cluster.Clusterer;

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
  private final int learn_req;
  private final double low_shield;

  public HierarchyClusterClassifier(Set<HierarchyClusterFamily> families, int learn_req, double low_shield) {
    this.tree = families;
    this.learn_req = learn_req;
    this.low_shield = low_shield;
  }

  public HierarchyClusterClassifier(int learn_req) {
    this(new HashSet<>(), learn_req, -1.0);
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
      if (learn_req > 0.0 && distances.size() >= learn_req) {
        double closestNodeDistance = Double.MAX_VALUE;
        for (double node : distances.keySet()) {
          boolean aboveCentroid = false;
          double nodeOffset = Math.abs(node - value);
          for (HierarchyClusterFamily other : tree) {
            if (other == family)
              continue;
            if (Math.abs(node - other.centroid()) < nodeOffset) {
              aboveCentroid = true; // cannot go above other centroids
              //double offsetToCentroid = Math.abs((other.centroid() - value) - nodeOffset); TODO: possibly add a learning connection to other centroids using this
              break;
            }
          }
          if (aboveCentroid || (low_shield > 0.0D && nodeOffset > low_shield))
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
    double[] data = tree.stream()
      .flatMapToDouble(family ->
        family.distances()
          .keySet()
          .stream()
          .mapToDouble(Double::doubleValue))
      .toArray();

    int[] labels = tree.stream()
      .flatMapToInt(family ->
        family.distances()
          .keySet()
          .stream()
          .mapToInt(node -> family.index()))
      .toArray();

    return new HierarchyClusterEvaluator(this, data, labels);
  }

  @Nonnull
  public String draw() {
    StringBuilder builder = new StringBuilder();
    String nodeSymbol = "*";
    String centroidSymbol = "o";
    tree.forEach(family -> {
      builder.append(family.label())
        .append("(").append(family.index()).append(")")
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
