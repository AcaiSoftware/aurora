package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;

/**
 * Immutable extension of {@link Node}.
 *
 * @author Clouke
 * @since 29.08.2023 20:09
 * © Aurora - All Rights Reserved
 */
public class ImmutableNode<C> implements Node<C> {

  protected final C content;
  protected final double probability;

  public ImmutableNode(C content, double probability) {
    this.content = content;
    this.probability = probability;
  }

  @Override
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
