package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 29.08.2023 19:55
 * © Aurora - All Rights Reserved
 */
public interface Node<C> extends Comparable<Node<C>> {

  /**
   * Gets the content of this node.
   *
   * @return Returns the content of this node.
   */
  C content();

  /**
   * Gets the probability within the content of this node.
   *
   * @return Returns the probability within the content of this node.
   */
  double probability();

  @Override
  int compareTo(@Nonnull Node<C> other);

}

