package gg.acai.aurora.degree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Clouke
 * @since 07.03.2023 03:50
 * © Aurora - All Rights Reserved
 */
public class DegreeGroup<N> implements Iterable<N>, Predicate<N> {

  private final N[] content;
  private final List<N> nodes;

  public DegreeGroup(N[] content) {
    this.content = content;
    this.nodes = new ArrayList<>();
  }

  public N get(int index) {
    return content[index];
  }

  public void addNode(N n) {
    nodes.add(n);
  }

  public N getHighestDegreeNode() {
    // checks for the most added one in nodes list
    N highest = null;
    int highestDegree = 0;
    for (N n : nodes) {
      int degree = 0;
      for (N other : nodes) {
        if (n.equals(other)) {
          degree++;
        }
      }
      if (degree > highestDegree) {
        highest = n;
        highestDegree = degree;
      }
    }

    return highest;
  }

  public N getRandomNode() {
    return nodes.get((int) (Math.random() * nodes.size()));
  }

  public int degreeOf(N n) {
    int degree = 0;
    for (N other : nodes) {
      if (n.equals(other)) {
        degree++;
      }
    }
    return degree;
  }

  public double similarity(N n) {
    if (!(n instanceof String)) {
      return -1.0;
    }

    String string = (String) n;
    double distance = 0;
    for (N other : content) {
      if (!(other instanceof String)) {
        continue;
      }

      String otherString = (String) other;
      distance += LevenshteinDistance.compute(string, otherString);
    }

    distance /= content.length;

    return 1 - (distance / 100.0);
  }

  public int size() {
    return content.length;
  }

  public N[] getContent() {
    return content;
  }

  public boolean contains(N n) {
    for (N other : content) {
      if (other.equals(n)) {
        return true;
      }
    }
    return false;
  }

  public List<N> getNodes() {
    return nodes;
  }

  public boolean hasNode(N n) {
    return nodes.contains(n);
  }

  public N getNode(int index) {
    return nodes.get(index);
  }

  public int nodeSize() {
    return nodes.size();
  }

  @Override
  public Iterator<N> iterator() {
    return new Iterator<N>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < content.length;
      }

      @Override
      public N next() {
        return content[index++];
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Group{");
    N highest = getHighestDegreeNode();
    if (highest != null) {
      builder.append("degreeNode=").append(highest).append(", ");
    }
    for (N n : content) {
      builder.append(n).append(", ");
    }
    builder.delete(builder.length() - 2, builder.length());
    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean test(N n) {
    return contains(n);
  }

}
