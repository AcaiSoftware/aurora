package gg.acai.aurora.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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

  public Vector<String> defaults() {
    Vector<String> options = new Vector<>();
    options.add("result");
    options.add("unknown");
    return options;
  }

  public T predict(Map<String, T> data) {
    Node<T> node = root;
    while (node.attribute().hashCode() != RESULT) {
      T value = data.get(node.attribute());
      node = node.children().get(value);
    }
    return node.result();
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

    T majorityResult = majority(data, targetAttribute);
    if (attributes.isEmpty() || sameValues(data, targetAttribute)) {
      Node<T> leaf = new Node<>("result");
      leaf.addResult(majorityResult);
      return leaf;
    }

    String bestAttribute = bestAttribute(data, attributes, targetAttribute);

    Node<T> node = new Node<>(bestAttribute);
    for (T value : distinctValues(data, bestAttribute)) {
      List<Map<String, T>> subData = subData(data, bestAttribute, value);
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
    T result = node.result();
    if (result != null) {
      return result;
    }

    T attributeValue = instance.get(node.attribute());
    if (node.children().containsKey(attributeValue)) {
      return classify(instance, node.children().get(attributeValue));
    }

    return null;
  }

  private T majority(List<Map<String, T>> data, String targetAttribute) {
    Map<T, Integer> countMap = new HashMap<>();
    for (Map<String, T> instance : data) {
      T result = instance.get(targetAttribute);
      countMap.put(result, countMap.getOrDefault(result, 0) + 1);
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

  private boolean sameValues(List<Map<String, T>> data, String attribute) {
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
  private String bestAttribute(List<Map<String, T>> data, List<String> attributes, String targetAttribute) {
    double maxGain = -1;
    String bestAttribute = null;

    for (String attribute : attributes) {
      double gain = infoGain(data, attribute, targetAttribute);
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
  private double infoGain(List<Map<String, T>> data, String attribute, String targetAttribute) {
    double entropy = entropy(data, targetAttribute);
    double remainder = remainder(data, attribute, targetAttribute);
    return entropy - remainder;
  }

  /**
   * Returns the entropy of the data.
   */
  private double entropy(List<Map<String, T>> data, String targetAttribute) {
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

  private double remainder(List<Map<String, T>> data, String attribute, String targetAttribute) {
    double remainder = 0;
    for (T value : distinctValues(data, attribute)) {
      List<Map<String, T>> subData = subData(data, attribute, value);
      remainder += ((double) subData.size() / data.size()) * entropy(subData, targetAttribute);
    }
    return remainder;
  }

  /**
   * Returns a set of distinct values for a given attribute in the data.
   */
  private Set<T> distinctValues(List<Map<String, T>> data, String attribute) {
    Set<T> distinctValues = new HashSet<>();

    for (Map<String, T> instance : data) {
      distinctValues.add(instance.get(attribute));
    }

    return distinctValues;
  }

  /**
   * Returns a subset of the data where the given attribute has the given value.
   */
  private List<Map<String, T>> subData(List<Map<String, T>> data, String attribute, T value) {
    List<Map<String, T>> subData = new ArrayList<>();

    for (Map<String, T> instance : data) {
      if (instance.get(attribute).equals(value)) {
        subData.add(instance);
      }
    }

    return subData;
  }


}




