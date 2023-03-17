package gg.acai.aurora.ml.nn;

import gg.acai.acava.collect.pairs.ImmutablePair;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Clouke
 * @since 10.03.2023 05:41
 * © Aurora - All Rights Reserved
 */
public class MultiNeuralNetwork {

  private final Map<NeuralNetworkTrainer, ImmutablePair<double[][], double[][]>> trainers;
  private final List<Thread> workers;

  public MultiNeuralNetwork(Map<NeuralNetworkTrainer, ImmutablePair<double[][], double[][]>> trainers) {
    this.trainers = trainers;
    this.workers = new LinkedList<>();
  }

  public MultiNeuralNetwork() {
    this(new LinkedHashMap<>());
  }

  public MultiNeuralNetwork addTrainer(NeuralNetworkTrainer trainer, double[][] inputs, double[][] outputs) {
    trainers.put(trainer, new ImmutablePair<>(inputs, outputs));
    return this;
  }

  public void begin() {
    for (Map.Entry<NeuralNetworkTrainer, ImmutablePair<double[][], double[][]>> entry : trainers.entrySet()) {
      NeuralNetworkTrainer trainer = entry.getKey();
      ImmutablePair<double[][], double[][]> pair = entry.getValue();
      Thread thread = new Thread(() -> trainer.train(pair.left(), pair.right()));
      workers.add(thread);
      thread.start();
    }

    try {
      System.out.println("Waiting for workers to finish...");
      for (Thread worker : workers) {
        worker.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("All workers finished!");
  }

}

