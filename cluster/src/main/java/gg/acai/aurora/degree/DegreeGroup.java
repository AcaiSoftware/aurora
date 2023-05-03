package gg.acai.aurora.degree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * A degree group holding nodes within same similarity.
 *
 * @author Clouke
 * @since 07.03.2023 03:50
 * © Aurora - All Rights Reserved
 */
public class DegreeGroup<N> implements Iterable<N>, Predicate<N> {

  private final N[] content;
  private final List<N> nodes;

  /**
   * Constructs a new degree group with the given content.
   *
   * @param content The content of the group
   */
  public DegreeGroup(N[] content) {
    this.content = content;
    this.nodes = new ArrayList<>();
  }

  /**
   * Gets the content of the group.
   *
   * @param index The index of the content
   * @return Returns the content at the given index
   */
  public N get(int index) {
    return content[index];
  }

  /**
   * Adds a node to the group.
   *
   * @param n The node to add
   */
  public void addNode(N n) {
    nodes.add(n);
  }

  /**
   * Gets the highest degree node in the group.
   * <p>This is the node which has been added the most.
   *
   * @return Returns the highest degree node or null if none
   */
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

  /**
   * Gets a random node in this group.
   *
   * @return Returns a random node in this group or null if empty
   */
  public N getRandomNode() {
    if (nodes.isEmpty()) {
      return null;
    }
    return nodes.get((int) (Math.random() * nodes.size()));
  }

  /**
   * Gets the degree of the given node.
   *
   * @param n The node to get the degree of
   * @return Returns the degree of the given node
   */
  public int degreeOf(N n) {
    int degree = 0;
    for (N other : nodes) {
      if (n.equals(other)) {
        degree++;
      }
    }
    return degree;
  }

  /**
   * Checks the similarity between the given node and this group.
   *
   * @param n The node to check the similarity of
   * @return Returns the similarity between the given node and this group
   */
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

  /**
   * Gets the size of this group.
   *
   * @return Returns the size of this group
   */
  public int size() {
    return content.length;
  }

  /**
   * Gets the content of this group.
   *
   * @return Returns the content of this group
   */
  public N[] getContent() {
    return content;
  }

  /**
   * Checks if this group contains the given node.
   *
   * @param n The node to check
   * @return Returns true if this group contains the given node
   */
  public boolean contains(N n) {
    for (N other : content) {
      if (other.equals(n)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the nodes in this group.
   *
   * @return Returns the nodes in this group
   */
  public List<N> getNodes() {
    return nodes;
  }

  /**
   * Checks if this group has the given node.
   *
   * @param n The node to check
   * @return Returns true if this group has the given node
   */
  public boolean hasNode(N n) {
    return nodes.contains(n);
  }

  /**
   * Gets the node at the given index.
   *
   * @param index The index of the node
   * @return Returns the node at the given index
   */
  public N getNode(int index) {
    return nodes.get(index);
  }

  /**
   * Gets the size of the nodes in this group.
   *
   * @return Returns the size of the nodes in this group
   */
  public int nodeSize() {
    return nodes.size();
  }

  /**
   * Gets an iterator for this group.
   *
   * @return Returns an iterator for this group
   */
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
