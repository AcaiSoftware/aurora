package gg.acai.aurora.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Clouke
 * @since 21.04.2023 08:51
 * © Aurora - All Rights Reserved
 */
public class DecisionTree<T> {

  private static final int RESULT = "result".hashCode();
  private Node<T> root;

  public DecisionTree() {
    root = null;
  }

  public DecisionTree(List<Map<String, T>> data, String targetAttribute) {
    train(data, targetAttribute);
  }

  public T predict(Map<String, T> data) {
    Node<T> node = root;
    while (node.attribute.hashCode() != RESULT) {
      T value = data.get(node.attribute);
      node = node.children.get(value);
    }
    return node.result;
  }

  private static class Node<T> {

    private final String attribute;
    private final Map<T, Node<T>> children;
    private T result;

    public Node(String attribute) {
      this.attribute = attribute;
      this.children = new HashMap<>();
    }

    public void addResult(T result) {
      this.result = result;
    }

    public void addChild(T value, Node<T> node) {
      children.put(value, node);
    }
  }

  public void train(List<Map<String, T>> data, String targetAttribute) {
    List<String> attributes = new ArrayList<>(data.get(0).keySet());
    attributes.remove(targetAttribute);
    root = buildTree(data, targetAttribute, attributes);
  }

  public void train(List<Map<String, T>> data) {
    train(data, "result");
  }

  private Node<T> buildTree(List<Map<String, T>> data, String targetAttribute, List<String> attributes) {
    if (data.isEmpty()) {
      return new Node<>("unknown");
    }

    T majorityResult = getMajorityResult(data, targetAttribute);
    if (attributes.isEmpty() || allDataHaveSameValue(data, targetAttribute)) {
      Node<T> leaf = new Node<>("result");
      leaf.addResult(majorityResult);
      return leaf;
    }

    String bestAttribute = getBestAttribute(data, attributes, targetAttribute);

    Node<T> node = new Node<>(bestAttribute);
    for (T value : getDistinctValues(data, bestAttribute)) {
      List<Map<String, T>> subData = getSubData(data, bestAttribute, value);
      List<String> subAttributes = new ArrayList<>(attributes);
      subAttributes.remove(bestAttribute);
      node.addChild(value, buildTree(subData, targetAttribute, subAttributes));
    }

    return node;
  }

  public T classify(Map<String, T> instance) {
    return classify(instance, root);
  }

  public T classify(Map<String, T> instance, Node<T> node) {
    if (node.result != null) {
      return node.result;
    }

    T attributeValue = instance.get(node.attribute);
    if (node.children.containsKey(attributeValue)) {
      return classify(instance, node.children.get(attributeValue));
    }

    return null;
  }

  private T getMajorityResult(List<Map<String, T>> data, String targetAttribute) {
    Map<T, Integer> countMap = new HashMap<>();
    for (Map<String, T> instance : data) {
      T result = instance.get(targetAttribute);
      if (countMap.containsKey(result)) {
        countMap.put(result, countMap.get(result) + 1);
      } else {
        countMap.put(result, 1);
      }
    }
    int maxCount = -1;
    T majorityResult = null;
    for (T result : countMap.keySet()) {
      int count = countMap.get(result);
      if (count > maxCount) {
        maxCount = count;
        majorityResult = result;
      }
    }
    return majorityResult;
  }

  private boolean allDataHaveSameValue(List<Map<String, T>> data, String attribute) {
    T value = null;
    for (Map<String, T> instance : data) {
      T attributeValue = instance.get(attribute);
      if (value == null) {
        value = attributeValue;
      } else if (!value.equals(attributeValue)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the attribute with the highest information gain.
   */
  private String getBestAttribute(List<Map<String, T>> data, List<String> attributes, String targetAttribute) {
    double maxGain = -1;
    String bestAttribute = null;

    for (String attribute : attributes) {
      double gain = getInformationGain(data, attribute, targetAttribute);
      if (gain > maxGain) {
        maxGain = gain;
        bestAttribute = attribute;
      }
    }

    return bestAttribute;
  }

  /**
   * Returns the information gain of a given attribute.
   */
  private double getInformationGain(List<Map<String, T>> data, String attribute, String targetAttribute) {
    double entropy = getEntropy(data, targetAttribute);
    double remainder = getRemainder(data, attribute, targetAttribute);
    return entropy - remainder;
  }

  /**
   * Returns the entropy of the data.
   */
  private double getEntropy(List<Map<String, T>> data, String targetAttribute) {
    Map<T, Integer> countMap = new HashMap<>();
    for (Map<String, T> instance : data) {
      T result = instance.get(targetAttribute);
      if (countMap.containsKey(result)) {
        countMap.put(result, countMap.get(result) + 1);
      } else {
        countMap.put(result, 1);
      }
    }

    double entropy = 0;
    for (T result : countMap.keySet()) {
      double probability = (double) countMap.get(result) / data.size();
      entropy -= probability * Math.log(probability) / Math.log(2);
    }

    return entropy;
  }

  private double getRemainder(List<Map<String, T>> data, String attribute, String targetAttribute) {
    double remainder = 0;
    for (T value : getDistinctValues(data, attribute)) {
      List<Map<String, T>> subData = getSubData(data, attribute, value);
      remainder += ((double) subData.size() / data.size()) * getEntropy(subData, targetAttribute);
    }
    return remainder;
  }

  /**
   * Returns a set of distinct values for a given attribute in the data.
   */
  private HashSet<T> getDistinctValues(List<Map<String, T>> data, String attribute) {
    HashSet<T> distinctValues = new HashSet<>();

    for (Map<String, T> instance : data) {
      distinctValues.add(instance.get(attribute));
    }

    return distinctValues;
  }

  /**
   * Returns a subset of the data where the given attribute has the given value.
   */
  private List<Map<String, T>> getSubData(List<Map<String, T>> data, String attribute, T value) {
    List<Map<String, T>> subData = new ArrayList<>();

    for (Map<String, T> instance : data) {
      if (instance.get(attribute).equals(value)) {
        subData.add(instance);
      }
    }

    return subData;
  }


}




