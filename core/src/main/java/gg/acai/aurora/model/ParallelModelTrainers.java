package gg.acai.aurora.model;

import gg.acai.acava.collect.maps.BiHashMap;
import gg.acai.acava.collect.maps.BiMap;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 17.04.2023 01:26
 * © Aurora - All Rights Reserved
 */
public class ParallelModelTrainers implements ParallelModelTrainer {

  private final BiMap<Trainable, double[][], double[][]> trainers;
  private final Lock lock;
  private final List<Thread> workers;
  private boolean running;

  public ParallelModelTrainers(BiMap<Trainable, double[][], double[][]> trainers, List<Thread> workers) {
    this.trainers = trainers;
    this.workers = workers;
    this.lock = new ReentrantLock();
  }

  public ParallelModelTrainers() {
    this(new BiHashMap<>(), new LinkedList<>());
  }

  @Override
  public void begin(Consumer<ParallelModelTrainer> completion) throws RuntimeException {
    try {
      lock.lock();
      if (running) {
        throw new IllegalStateException("Already running!");
      }
      running = true;
      trainers.forEach(
        (trainer, inputs, outputs) -> {
          Thread thread = new Thread(() -> {
            try {
              trainer.train(inputs, outputs);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });
          workers.add(thread);
          thread.start();
        });

      workers.forEach(thread -> {
        try {
          thread.join();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });

      running = false;
    } finally {
      completion.accept(this);
      lock.unlock();
    }
  }

  @Override
  public ParallelModelTrainer add(Trainable trainer, double[][] inputs, double[][] outputs) {
    synchronized (trainers) {
      trainers.put(trainer, inputs, outputs);
      return this;
    }
  }

  @Override
  public List<Thread> workers() {
    synchronized (workers) {
      return workers;
    }
  }

  @Override
  public BiMap<Trainable, double[][], double[][]> asMap() {
    synchronized (trainers) {
      return trainers;
    }
  }

}
