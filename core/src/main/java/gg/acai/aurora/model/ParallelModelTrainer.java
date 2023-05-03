package gg.acai.aurora.model;

import gg.acai.acava.collect.maps.BiMap;

import java.util.List;
import java.util.function.Consumer;

/**
 * A parallel model trainer which provides a parallel training interface for models.
 *
 * @author Clouke
 * @since 17.04.2023 00:59
 * © Aurora - All Rights Reserved
 */
public interface ParallelModelTrainer {

  /**
   * Begins the parallel training.
   *
   * @param completion A completion handler for when the training is complete.
   */
  void begin(Consumer<ParallelModelTrainer> completion);

  /**
   * Adds a trainable to the parallel model trainer.
   *
   * @param trainer The trainable to add.
   * @param inputs The inputs to train on.
   * @param outputs The outputs to train on.
   * @return Returns this parallel model trainer for chaining.
   */
  ParallelModelTrainer add(Trainable trainer, double[][] inputs, double[][] outputs);

  /**
   * Gets a list of threads that are currently working.
   *
   * @return Returns a list of threads that are currently working.
   */
  List<Thread> workers();

  /**
   * Gets a BiMap of the trainables and their inputs and outputs.
   *
   * @return Returns a BiMap of the trainables and their inputs and outputs.
   */
  BiMap<Trainable, double[][], double[][]> asMap();

}
