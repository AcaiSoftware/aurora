package gg.acai.aurora.ml.nn;

import gg.acai.acava.Requisites;
import gg.acai.acava.collect.pairs.Pairs;
import gg.acai.acava.commons.graph.Graph;
import gg.acai.acava.io.Callback;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.QRMath;
import gg.acai.aurora.CycleBuffer;
import gg.acai.aurora.TimeEstimator;
import gg.acai.aurora.ml.Trainable;
import gg.acai.aurora.ml.TrainingTimeEstimator;
import gg.acai.aurora.ml.nn.extension.ModelTrainEvent;
import gg.acai.aurora.ml.nn.extension.TrainingTickEvent;
import gg.acai.aurora.sets.DataSet;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;

/**
 * @author Clouke
 * @since 11.02.2023 16:18
 * © Acava - All Rights Reserved
 */
public class NeuralNetworkTrainer extends AbstractNeuralNetwork implements Trainable {

  private final int epochs;
  private final double learningRate;
  private final ExecutorService thread;
  private final ModelTrainEvent listener;
  private final TimeEstimator<Integer> estimator;
  private final boolean printTrainingProgress;
  private final boolean autoSave;
  private final boolean shouldPrintStats;
  private final CycleBuffer cycle;
  private final int maxCycleBuffer;
  private final Graph<Double> graph;
  private final DataSet set;
  private Callback<NeuralNetworkTrainer> callback;
  private double[] accuracyTest;

  private long start;
  private long tick;
  private double accurateEstimation;
  private int lastPercentage;
  private double lastAccuracy;
  private double accuracy = -1.0;
  private boolean completed;

  public NeuralNetworkTrainer(@Nonnull TrainingBuilder builder) {
    super(builder.inputLayerSize, builder.hiddenLayerSize, builder.outputLayerSize);
    super.setActivationFunction(builder.activationFunction);
    this.epochs = builder.epochs;
    this.learningRate = builder.learningRate;
    this.thread = builder.thread;
    this.listener = builder.listener;
    this.printTrainingProgress = builder.shouldPrintTrainingProgress;
    this.autoSave = builder.autoSave;
    this.accuracyTest = builder.accuracyTest;
    this.shouldPrintStats = builder.shouldPrintStats;
    this.maxCycleBuffer = builder.maxCycleBuffer;
    this.graph = builder.graph;
    this.cycle = new CycleBuffer(maxCycleBuffer);
    this.estimator = new TrainingTimeEstimator(epochs);
    this.set = builder.set;
  }

  public void train() {
    if (set == null || set.inputs() == null || set.targets() == null) throw new IllegalArgumentException("You must provide a dataset to train on!");
    train(set.inputs(), set.targets());
  }

  @Override
  public void train(double[][] inputs, double[][] targets) {
    if (inputs.length != targets.length) throw new IllegalArgumentException("Inputs and targets must have the same length!");
    if (accuracyTest == null) accuracyTest = new double[]{targets[0][0]};

    Runnable task = () -> {
      start = System.currentTimeMillis();
      for (int e = 0; e <= epochs; e++) {
        estimator.tick();
        tick = System.nanoTime();
        for (int i = 0; i < inputs.length; i++) {
          // Perform forward propagation
          double[] hidden = new double[weights_input_to_hidden[0].length];
          for (int j = 0; j < hidden.length; j++) {
            hidden[j] = biases_hidden[j];
            for (int k = 0; k < inputs[i].length; k++) {
              hidden[j] += inputs[i][k] * weights_input_to_hidden[k][j];
            }
            hidden[j] = activationFunction.apply(hidden[j]);
          }
          double[] output = new double[weights_hidden_to_output[0].length];
          for (int j = 0; j < output.length; j++) {
            output[j] = biases_output[j];
            for (int k = 0; k < hidden.length; k++) {
              output[j] += hidden[k] * weights_hidden_to_output[k][j];
            }
            output[j] = activationFunction.apply(output[j]);
          }
          // Compute error
          double[] error = new double[output.length];
          for (int j = 0; j < output.length; j++) {
            error[j] = targets[i][j] - output[j];
          }
          // Perform backpropagation
          double[] delta2 = new double[output.length];
          for (int j = 0; j < output.length; j++) {
            delta2[j] = error[j] * activationFunction.derivative(output[j]);
          }
          double[] delta1 = new double[hidden.length];
          for (int j = 0; j < hidden.length; j++) {
            double sum = 0;
            for (int k = 0; k < output.length; k++) {
              sum += delta2[k] * weights_hidden_to_output[j][k];
            }
            delta1[j] = sum * activationFunction.derivative(hidden[j]);
          }
          // Update weights and biases
          for (int j = 0; j < weights_input_to_hidden[0].length; j++) {
            for (int k = 0; k < weights_input_to_hidden.length; k++) {
              weights_input_to_hidden[k][j] += learningRate * delta1[j] * inputs[i][k];
            }
            biases_hidden[j] += learningRate * delta1[j];
          }
          for (int j = 0; j < weights_hidden_to_output[0].length; j++) {
            for (int k = 0; k < weights_hidden_to_output.length; k++) {
              weights_hidden_to_output[k][j] += learningRate * delta2[j] * hidden[k];
            }
            biases_output[j] += learningRate * delta2[j];
          }
        }
        boolean stagnation = proceedTick(e);
        if (stagnation) {
          printStagnationWarning();
          break;
        }
      }
      endTick();
    };

    if (thread != null) {
      thread.execute(task);
      return;
    }

    task.run();
  }

  public void whenComplete(Callback<NeuralNetworkTrainer> callback) {
    this.callback = callback;
  }

  public TrainingStats stats() {
    if (!completed) {
      throw new IllegalStateException("Training is not completed yet!");
    }

    double accuracy = -1.0;
    if (accuracyTest != null) {
      double[] output = predict(accuracyTest);
      accuracy = QRMath.round(Math.pow(output[0] - 1, 2) * 100.0);
    }
    return new TrainingStats(epochs, learningRate, accuracy, start, accurateEstimation, graph);
  }

  private boolean proceedTick(int currentEpoch) {
    if (listener == null && !printTrainingProgress) {
      return false; // No need to proceed if there is no listener and no need to print progress
    }

    double percent = (double) currentEpoch / epochs * 100.0;
    int pct = (int) percent;
    boolean toReturn = false;
    String progress = null;
    if (pct != lastPercentage) {
      StringBuilder buf = new StringBuilder();
      char incomplete = '░';
      char complete = '█';
      for (int i = 0; i <= 100; i++)
        buf.append(i <= percent ? complete : incomplete);

      progress = buf.toString();
      if (graph != null) {
        long diff = System.nanoTime() - tick;
        double micros = diff / 1000.0;
        graph.addNode(micros);
      }
    }

    double estimation = estimator.estimated(currentEpoch);
    if (pct == 5)
      accurateEstimation = estimation;

    if (listener != null) {
      TrainingTickEvent event = new TrainingTickEvent(currentEpoch, epochs, percent, progress, estimation);
      listener.onTrain(event);
    }

    if (printTrainingProgress && progress != null) {
      if (accuracyTest != null) {
        double[] output = predict(accuracyTest);
        accuracy = QRMath.round(Math.pow(output[0] - 1, 2) * 100.0);
      }

      if (maxCycleBuffer != -1) {
        if (accuracy != -1.0 && (accuracy == lastAccuracy) && accuracy < 80.0) {
          toReturn = cycle.reached();
        } else {
          cycle.reset();
        }
      }

      lastAccuracy = accuracy;
      String b = Aurora.ANSI_BOLD;
      String r = Aurora.RESET;
      Pairs<Double, TimeEstimator.Time> estim = estimator.estimateWith(currentEpoch);
      double roundedEstimation = QRMath.round(estim.left());
      System.out.print("\r" + progress + " ─ " + b + pct + "/100%" + r + " ─ Time Left: " + b + roundedEstimation + estim.right().plural() + r + (accuracy != -1.0 ? " ─ Accuracy: " + b + accuracy + "%" : ""));
    }
    lastPercentage = (int) percent;
    return toReturn;
  }

  private void endTick() {
    start = System.currentTimeMillis() - start;
    completed = true;
    if (autoSave) {
      NeuralNetworkModel model = (NeuralNetworkModel) save().setSaveOnClose(true);
      model.close();
    }
    if (callback != null)
      callback.onCallback(this);

    if (shouldPrintStats) {
      TrainingStats stats = stats();
      stats.print();
    }
  }

  public NeuralNetworkModel save() {
    return saveAs(null);
  }

  public NeuralNetworkModel saveAs(String name) {
    Requisites.checkArgument(completed, "The training process has not been completed yet!");
    return NeuralNetworkFactory.loader()
      .from(this)
      .name(name)
      .ignoreVersions()
      .saveOnClose()
      .build();
  }

  public boolean isCompleted() {
    return completed;
  }

  private void printStagnationWarning() {
    String b = Aurora.ANSI_BOLD;
    String r = Aurora.RESET;
    String bullet = Aurora.BULLET;

    System.out.println(b + "\nWARNING: The training process has been stopped due to training stagnation" + r + " (The model got stuck and is not improving).\n" +
      b + "Options:" + r + "\n" +
      bullet + "Try re-training the model\n" +
      bullet + "Change the learning rate (current: " + learningRate + ")\n" +
      bullet + "Change the model architecture" + "\n" +
      bullet + "Change the training data" + "\n" +
      bullet + "Change the training parameters (epochs, etc.)\n" +
      bullet + "Try using" + b + " HyperparameterTuning" + r + " to find the best parameters for your model." + "\n" +
      "Buffered for " + cycle.current() + "/" + maxCycleBuffer + " cycles. If you wish to disable this feature, " +
      "use TrainingBuilder#disableCycleBuffer() or set the maxCycleBuffer to -1\n"
    );
  }

}
