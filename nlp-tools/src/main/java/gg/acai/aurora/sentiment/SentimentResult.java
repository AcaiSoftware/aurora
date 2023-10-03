package gg.acai.aurora.sentiment;

import java.util.Map;
import java.util.Objects;

/**
 * @author Clouke
 * @since 05.07.2023 20:12
 * © Aurora - All Rights Reserved
 */
public class SentimentResult {

  private final Map<Sentiment, Double> probabilities;
  private final double confidence;

  public SentimentResult(Map<Sentiment, Double> probabilities, double confidence) {
    this.probabilities = probabilities;
    this.confidence = confidence;
  }

  public Map<Sentiment, Double> probabilities() {
    return probabilities;
  }

  public double confidence() {
    return confidence;
  }

  public Sentiment sentiment() {
    return probabilities.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue())
      .map(Map.Entry::getKey)
      .orElse(Sentiment.NEUTRAL);
  }

  @Override
  public String toString() {
    return "SentimentPrediction{" +
      "probabilities=" + probabilities +
      ", confidence=" + confidence +
      '}';
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = probabilities != null ? probabilities.hashCode() : 0;
    temp = Double.doubleToLongBits(confidence);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SentimentResult)) return false;
    SentimentResult that = (SentimentResult) o;
    if (Double.compare(that.confidence, confidence) != 0) return false;
    return Objects.equals(probabilities, that.probabilities);
  }
}
