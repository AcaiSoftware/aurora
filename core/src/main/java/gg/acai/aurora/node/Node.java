package gg.acai.aurora.node;

import java.util.HashMap;
import java.util.Map;

/**
 * An attributed tree node that holds a value and a map of its children.
 *
 * @author Clouke
 * @since 02.05.2023 15:31
 * © Aurora - All Rights Reserved
 */
public class Node<T> {

  /**
   * The attribute of this node.
   */
  private final String attribute;
  /**
   * The children of this node.
   */
  private final Map<T, Node<T>> children;
  /**
   * The result of this node.
   */
  private T result;

  /**
   * Constructs a new node with the given attribute.
   *
   * @param attribute the attribute of this node
   */
  public Node(String attribute) {
    this.attribute = attribute;
    this.children = new HashMap<>();
  }

  /**
   * Adds a result to this node.
   *
   * @param result the result to add
   */
  public void addResult(T result) {
    this.result = result;
  }

  /**
   * Adds a child to this node.
   *
   * @param value the value of the child
   * @param node the child node
   */
  public void addChild(T value, Node<T> node) {
    children.put(value, node);
  }

  /**
   * Gets the attribute of this node.
   *
   * @return the attribute of this node
   */
  public String attribute() {
    return attribute;
  }

  /**
   * Gets the children of this node.
   *
   * @return the children of this node
   */
  public Map<T, Node<T>> children() {
    return children;
  }

  /**
   * Gets the result of this node.
   *
   * @return the result of this node
   */
  public T result() {
    return result;
  }

  /**
   * Checks if this node has a result.
   *
   * @return true if this node has a result, false otherwise
   */
  public boolean hasResult() {
    return result != null;
  }

  /**
   * Checks if this node is a leaf.
   *
   * @return true if this node is a leaf, false otherwise
   */
  public boolean leaf() {
    return children.isEmpty();
  }

}
