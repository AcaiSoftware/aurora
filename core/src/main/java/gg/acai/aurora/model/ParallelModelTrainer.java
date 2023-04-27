package gg.acai.aurora.model;

import gg.acai.acava.collect.maps.BiMap;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 17.04.2023 00:59
 * © Aurora - All Rights Reserved
 */
public interface ParallelModelTrainer {

  void begin(Consumer<ParallelModelTrainer> completion);

  ParallelModelTrainer add(Trainable trainer, double[][] inputs, double[][] outputs);

  List<Thread> workers();

  BiMap<Trainable, double[][], double[][]> asMap();

}
