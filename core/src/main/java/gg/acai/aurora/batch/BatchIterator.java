package gg.acai.aurora.batch;

import gg.acai.acava.io.Closeable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Clouke
 * @since 12.09.2023 21:39
 * © Aurora - All Rights Reserved
 */
public class BatchIterator<T> implements Iterator<Batch<T>>, Closeable {

  private final List<T> data;
  private final List<T> labels;
  private final int batchSize;
  private int currentIndex;

  public BatchIterator(List<T> data, List<T> labels, int batchSize) {
    this.data = data;
    this.labels = labels;
    this.batchSize = batchSize;
    this.currentIndex = 0;
  }

  @Override
  public boolean hasNext() {
    return currentIndex < data.size();
  }

  @Override
  public Batch<T> next() {
    int endIndex = Math.min(
      currentIndex + batchSize,
      data.size()
    );

    List<T> batchData = data.subList(currentIndex, endIndex);
    List<T> batchLabels = labels.subList(currentIndex, endIndex);
    currentIndex += batchSize;

    return new Batch<>(
      batchData,
      batchLabels,
      currentIndex
    );
  }

  public int size() {
    return data.size();
  }

  public void reset() {
    currentIndex = 0;
  }

  @Override
  public void close() {
    reset();
  }
}