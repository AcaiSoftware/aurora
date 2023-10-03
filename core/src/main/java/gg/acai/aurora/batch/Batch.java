package gg.acai.aurora.batch;

import java.util.List;

/**
 * @author Clouke
 * @since 12.09.2023 21:38
 * © Aurora - All Rights Reserved
 */
public class Batch<T> {

  private final List<T> data;
  private final List<T> labels;
  private final int index;

  public Batch(List<T> data, List<T> labels, int index) {
    this.data = data;
    this.labels = labels;
    this.index = index;
  }

  public List<T> data() {
    return data;
  }

  public List<T> labels() {
    return labels;
  }

  public int index() {
    return index;
  }

  @Override
  public String toString() {
    return "Batch{" +
      "data=" + data +
      ", labels=" + labels +
      ", index=" + index +
      '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Batch) {
      Batch<?> batch = (Batch<?>) obj;
      return batch.data.equals(data) &&
        batch.labels.equals(labels) &&
        batch.index == index;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return data.hashCode() + labels.hashCode() + index;
  }
}
