package gg.acai.aurora.ml.nlp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Clouke
 * @since 15.03.2023 23:22
 * © Aurora - All Rights Reserved
 */
public class WordVectorizer {

  private final Set<String> vocabulary;

  public WordVectorizer() {
    this.vocabulary = new HashSet<>();
  }

  public void fit(List<String> sentences) {
    for (String sentence : sentences) {
      String[] words = sentence.split("\\s+");
      vocabulary.addAll(Arrays.asList(words));
    }
  }

  public double[] transform(String sentence) {
    String[] words = sentence.split("\\s+");
    double[] vector = new double[vocabulary.size()];
    int index = 0;
    for (String word : vocabulary) {
      vector[index++] = Arrays.asList(words).contains(word) ? 1.0 : 0.0;
    }
    return vector;
  }

  public double distance(double[] a, double[] b) {
    double distance = 0;
    for (int i = 0; i < a.length; i++) {
      distance += Math.abs(a[i] - b[i]);
    }
    return distance;
  }

  public double cosineSimilarity(double[] a, double[] b) {
    double dotProduct = 0.0;
    double normA = 0.0;
    double normB = 0.0;
    for (int i = 0; i < a.length; i++) {
      dotProduct += a[i] * b[i];
      normA += Math.pow(a[i], 2);
      normB += Math.pow(b[i], 2);
    }
    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
  }

  public int size() {
    return vocabulary.size();
  }

  public String inverseTransform(double[] vector) {
    StringBuilder builder = new StringBuilder();
    int index = 0;
    for (String word : vocabulary) {
      if (vector[index++] == 1.0) {
        builder.append(word).append(" ");
      }
    }
    return builder.toString().trim();
  }

  public Set<String> getVocabulary() {
    return vocabulary;
  }

  @Override
  public String toString() {
    return "WordVectorizer{" +
            "vocabulary=" + vocabulary +
            '}';
  }
}
