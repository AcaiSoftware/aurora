package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mutable extension of {@link Node}.
 *
 * @author Clouke
 * @since 29.08.2023 20:10
 * © Aurora - All Rights Reserved
 */
public class MutableNode<C> implements Node<C> {

  protected C content;
  protected double probability;

  public MutableNode(C content, double probability) {
    this.content = content;
    this.probability = probability;
  }

  public MutableNode(double probability) {
    this(null, probability);
  }

  public MutableNode(C content) {
    this(content, 0.0);
  }

  /**
   * Sets the content of this node.
   *
   * @param content The new content.
   */
  public void setContent(C content) {
    this.content = content;
  }

  /**
   * Sets the probability of this node.
   *
   * @param probability The new probability.
   */
  public void setProbability(double probability) {
    this.probability = probability;
  }

  @Override @Nullable
  public C content() {
    return content;
  }

  @Override
  public double probability() {
    return probability;
  }

  @Override
  public int compareTo(@Nonnull Node<C> other) {
    return Double.compare(
      probability,
      other.probability()
    );
  }
}
