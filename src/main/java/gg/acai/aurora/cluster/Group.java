package gg.acai.aurora.cluster;

import gg.acai.aurora.ml.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Clouke
 * @since 07.03.2023 03:50
 * © Aurora - All Rights Reserved
 */
public class Group<T> implements Iterable<T>, Predicate<T> {

  private final T[] content;
  private final List<T> nodes;

  public Group(T[] content) {
    this.content = content;
    this.nodes = new ArrayList<>();
  }

  public T get(int index) {
    return content[index];
  }

  public void addNode(T t) {
    nodes.add(t);
  }

  public T getHighestDegreeNode() {
    // checks for the most added one in nodes list
    T highest = null;
    int highestDegree = 0;
    for (T t : nodes) {
      int degree = 0;
      for (T other : nodes) {
        if (t.equals(other)) {
          degree++;
        }
      }
      if (degree > highestDegree) {
        highest = t;
        highestDegree = degree;
      }
    }

    return highest;
  }

  public T getRandomNode() {
    return nodes.get((int) (Math.random() * nodes.size()));
  }

  public int degree(T t) {
    int degree = 0;
    for (T other : nodes) {
      if (t.equals(other)) {
        degree++;
      }
    }
    return degree;
  }

  public double similarity(T t) {
    if (!(t instanceof String)) {
      return 0;
    }

    String string = (String) t;
    double distance = 0;
    for (T other : content) {
      if (!(other instanceof String)) {
        continue;
      }

      String otherString = (String) other;
      distance += LevenshteinDistance.compute(string, otherString);
    }

    distance /= content.length;

    return 1 - (distance / 100);
  }

  public int size() {
    return content.length;
  }

  public T[] getContent() {
    return content;
  }

  public boolean contains(T t) {
    for (T other : content) {
      if (other.equals(t)) {
        return true;
      }
    }
    return false;
  }

  public List<T> getNodes() {
    return nodes;
  }

  public boolean hasNode(T t) {
    return nodes.contains(t);
  }

  public T getNode(int index) {
    return nodes.get(index);
  }

  public int nodeSize() {
    return nodes.size();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < content.length;
      }

      @Override
      public T next() {
        return content[index++];
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Group{");
    for (T t : content) {
      builder.append(t.toString()).append(", ");
    }
    T highestDegreeNode = getHighestDegreeNode();
    if (highestDegreeNode != null) {
      builder.append("selNode: ").append(highestDegreeNode).append(", ");
    }
    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean test(T t) {
    return contains(t);
  }
}
