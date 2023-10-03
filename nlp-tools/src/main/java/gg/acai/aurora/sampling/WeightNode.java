package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 29.08.2023 20:00
 * © Aurora - All Rights Reserved
 */
public class WeightNode implements Node<Double> {

  private final Double content;
  private final double probability;

  public WeightNode(Double content, double probability) {
    this.content = content;
    this.probability = probability;
  }

  @Override
  public Double content() {
    return content;
  }

  @Override
  public double probability() {
    return probability;
  }

  @Override
  public int compareTo(@Nonnull Node<Double> other) {
    return Double.compare(
      probability,
      other.probability()
    );
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof WeightNode)) return false;
    WeightNode other = (WeightNode) obj;
    return content.equals(other.content) && probability == other.probability;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = content.hashCode();
    temp = Double.doubleToLongBits(probability);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "WeightNode{" +
      "content=" + content +
      ", probability=" + probability +
      '}';
  }
}
