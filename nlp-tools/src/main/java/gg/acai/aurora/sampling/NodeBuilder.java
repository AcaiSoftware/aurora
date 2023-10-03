package gg.acai.aurora.sampling;

/**
 * Builder class for {@link Node}s.
 *
 * @author Clouke
 * @since 29.08.2023 20:19
 * © Aurora - All Rights Reserved
 */
public class NodeBuilder<C> {

  private C content;
  private double probability;
  private boolean mutable;

  /**
   * Applies the given content to the node.
   *
   * @param content The content to apply.
   * @return Returns this builder for chaining.
   */
  public NodeBuilder<C> content(C content) {
    this.content = content;
    return this;
  }

  /**
   * Applies the given probability to the node.
   *
   * @param probability The probability to apply.
   * @return Returns this builder for chaining.
   */
  public NodeBuilder<C> probability(double probability) {
    this.probability = probability;
    return this;
  }

  /**
   * Sets the node to be mutable.
   * <p> The node will be immutable by default.
   *
   * @return Returns this builder for chaining.
   */
  public NodeBuilder<C> mutable() {
    this.mutable = true;
    return this;
  }

  /**
   * Builds the node with the given parameters.
   *
   * @return Returns the built node instance.
   */
  public Node<C> build() {
    return mutable
      ? new MutableNode<>(content, probability)
      : new ImmutableNode<>(content, probability);
  }

}
