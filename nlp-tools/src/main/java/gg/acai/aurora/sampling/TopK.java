package gg.acai.aurora.sampling;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * This class implements a data structure for maintaining the top-k elements with the highest priorities.
 * Elements are stored in a priority queue, and the lowest-priority element among the top-k is removed when
 * a new element is added that has a higher priority.
 *
 * @param <C> The type of elements stored in the TopK data structure.
 *
 * @author Clouke
 * @since 29.08.2023 20:09
 * © Aurora - All Rights Reserved
 */
public class TopK<C> {

  private final PriorityQueue<Node<C>> heap;
  private final int k;
  private Random r;

  /**
   * Constructs a new TopK data structure with the given capacity.
   *
   * @param k The max number of elements to maintain in the top-k list.
   */
  public TopK(int k) {
    this.k = k;
    this.heap = new PriorityQueue<>(k);
  }

  /**
   * Adds a new element to the top-k list.
   *
   * @param node The wrapped element to be added to the top-k list.
   */
  public void add(@Nonnull Node<C> node) {
    if (heap.size() < k) {
      heap.offer(node);
    } else if (heap.peek() != null && node.compareTo(heap.peek()) > 0) {
      heap.poll();
      heap.offer(node);
    }
  }

  /**
   * Samples the next element from the top-k list using a provided random number generator.
   *
   * @param random The random number generator to be used for sampling.
   * @return The sampled element from the top-k list.
   */
  public Node<C> sampleNext(@Nonnull Random random) {
    Objects.requireNonNull(random);
    double r = random.nextDouble();
    double sum = 0;
    for (Node<C> node : heap) {
      sum += node.probability();
      if (r < sum) return node;
    }
    return heap.peek();
  }

  /**
   * Samples the next element from the top-k list using the internally stored random number generator.
   *
   * @return The sampled element from the top-k list.
   */
  public Node<C> sampleNext() {
    r = (r == null ? new Random() : r);
    return sampleNext(r);
  }

  /**
   * Gets the priority queue containing the top-k elements.
   *
   * @return Returns the priority queue containing the top-k elements.
   */
  public PriorityQueue<Node<C>> top() {
    return heap;
  }

  /**
   * Gets the size of the top-k list.
   *
   * @return Returns the size of the top-k list.
   */
  public int size() {
    return heap.size();
  }

  /**
   * Sets the random number generator to be used for sampling.
   *
   * @param r The random number generator to be used for sampling.
   */
  public void setRandom(Random r) {
    this.r = r;
  }

}