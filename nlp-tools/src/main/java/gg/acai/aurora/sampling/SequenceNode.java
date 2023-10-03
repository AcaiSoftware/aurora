package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 29.08.2023 19:56
 * © Aurora - All Rights Reserved
 */
public class SequenceNode implements Node<String> {

  private final String content;
  private final double probability;

  public SequenceNode(String content, double probability) {
    this.content = content;
    this.probability = probability;
  }

  @Override
  public String content() {
    return content;
  }

  @Override
  public double probability() {
    return probability;
  }

  @Override
  public int compareTo(@Nonnull Node<String> other) {
    return Double.compare(
      probability,
      other.probability()
    );
  }

  @Override
  public String toString() {
    return "SequenceNode{" +
      "content='" + content + '\'' +
      ", probability=" + probability +
      '}';
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
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof SequenceNode)) return false;
    SequenceNode other = (SequenceNode) obj;
    return content.equals(other.content) && probability == other.probability;
  }
}