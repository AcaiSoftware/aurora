package gg.acai.aurora;

import com.google.common.base.Preconditions;
import gg.acai.acava.commons.Attributes;
import gg.acai.acava.commons.AttributesMapper;
import gg.acai.aurora.earlystop.EarlyStoppers;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.publics.io.ComplexProgressTicker;
import gg.acai.aurora.model.Evaluation;
import gg.acai.aurora.model.ModelMetrics;
import gg.acai.aurora.model.IterableTimeEstimator;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.sets.DataSet;
import gg.acai.aurora.publics.Ticker;
import gg.acai.aurora.sets.TestSet;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <p> Example Usage:
 * <p><b>Building a Neural Network</b>
 * <pre>{@code
 *  NeuralNetworkTrainer trainer = new NeuralNetworkBuilder()
 *    .name("my_model")
 *    .learningRate(0.1)
 *    .epochs(1_000_000)
 *    .optimizer(new StochasticGradientDescent())
 *    .earlyStops(new Stagnation(20)) // in case stagnation happens, we will stop training to prevent unnecessary training
 *    .printing(Bar.CLASSIC)
 *    .activationFunction(ActivationFunction.SIGMOID)
 *    .epochActions(new EpochAutoSave(10_000, "C:\\Users\\my_user\\models")) // every 10K epochs
 *    .layers(mapper -> mapper
 *      .inputLayers(3) // add your input layer size here
 *      .hiddenLayers(3) // add your hidden layer size here
 *      .outputLayers(1)) // add your output layer size here
 *    .build();
 *
 *  trainer.train(inputs, outputs); // train the neural network
 *}</pre>
 * @author Clouke
 * @since 11.02.2023 16:18
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkTrainer extends AbstractNeuralNetwork implements NeuralNetwork {

  protected static final Ticker<Long> TICKER = Ticker.systemMillis();

  protected final int epochs;
  protected final double learningRate;
  protected final TimeEstimator<Integer> estimator;
  protected final boolean autoSave;
  protected final boolean shouldPrintStats;
  protected final DataSet set;
  protected final EarlyStoppers earlyStoppers;
  protected final Attributes attributes = new AttributesMapper();
  protected final Optimizer optimizer;
  protected final ComplexProgressTicker progressTicker;
  protected final TestSet evaluationSet;
  protected final Set<EpochAction<NeuralNetwork>> epochActions;
  protected final AccuracySupplier accuracySupplier;
  protected Consumer<NeuralNetwork> completion;

  protected long time;
  protected double accuracy = -1.0;
  protected double loss = -1.0;
  protected boolean completed;
  protected boolean paused;

  public NeuralNetworkTrainer(@Nonnull NeuralNetworkBuilder builder) {
    super(builder.seed, builder.inputLayerSize, builder.hiddenLayerSize, builder.outputLayerSize);
    super.name = builder.name;
    NeuralNetworkModel model = builder.model;
    if (model != null) {
      weights_input_to_hidden = model.weights_input_to_hidden;
      weights_hidden_to_output = model.weights_hidden_to_output;
      biases_hidden = model.biases_hidden;
      biases_output = model.biases_output;
      name = model.name;
    }
    setActivationFunction(builder.activationFunction);
    this.epochs = builder.epochs;
    this.learningRate = builder.learningRate;
    this.autoSave = builder.autoSave;
    this.shouldPrintStats = builder.shouldPrintStats;
    this.set = builder.set;
    this.earlyStoppers = builder.earlyStoppers;
    this.optimizer = builder.optimizer.apply(learningRate);
    this.evaluationSet = builder.evaluationSet;
    this.epochActions = builder.epochActions;
    this.progressTicker = new ComplexProgressTicker(builder.progressBar, 1);
    this.accuracySupplier = builder.accuracySupplier;
    this.estimator = new IterableTimeEstimator(epochs);
  }

  @Override
  public void train() {
    if (set == null || set.inputs() == null || set.targets() == null) throw new IllegalStateException("No data set provided!");
    train(set.inputs(), set.targets());
  }

  @Override
  public void train(double[][] inputs, double[][] targets) {
    if (inputs.length != targets.length)
      throw new IllegalArgumentException("Inputs and targets must have the same length!");

    AccuracySupplier supplier = Optional
      .ofNullable(accuracySupplier)
      .orElse(() -> new double[]{targets[0][0]});

    time = TICKER.read();
    for (int epoch = 0; epoch <= epochs; epoch++) {
      if (paused) {
        try {
          synchronized (this) {
            while (paused) wait();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      estimator.tick();
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
        double loss = 0.0;
        for (double d : error) {
          loss += Math.pow(d, 2);
        }
        loss /= error.length;
        this.loss = loss;

        // Perform backpropagation
        double[] delta_output = new double[output.length];
        for (int j = 0; j < output.length; j++) {
          delta_output[j] = error[j] * activationFunction.derivative(output[j]);
        }
        double[] delta_hidden = new double[hidden.length];
        for (int j = 0; j < hidden.length; j++) {
          double sum = 0;
          for (int k = 0; k < output.length; k++) {
            sum += delta_output[k] * weights_hidden_to_output[j][k];
          }
          delta_hidden[j] = sum * activationFunction.derivative(hidden[j]);
        }

        // Update weights and biases
        optimizer.update(i, weights_input_to_hidden, weights_hidden_to_output, delta_hidden, delta_output, biases_hidden, biases_output, inputs, hidden);
      }

      int pct = (int) ((double) epoch / epochs * 100.0);
      double[] output = predict(supplier.test());
      accuracy = Math.pow(output[0] - 1, 2) * 100.0;

      estimator.setIteration(epoch);
      attributes.set("epoch", epoch)
        .set("accuracy", accuracy)
        .set("stage", pct)
        .set("time left", estimator)
        .set("loss", loss);

      if (!epochActions.isEmpty()) {
        for (EpochAction<NeuralNetwork> action : epochActions) {
          action.onEpochIteration(epoch, this);
        }
      }

      progressTicker.tick(pct, attributes);
      if (earlyStoppers.tick(attributes)) {
        earlyStoppers.printTerminationMessage();
        break;
      }
    }

    time = TICKER.call() - time;
    completed = true;
    if (autoSave) {
      NeuralNetworkModel model = toModel(name);
      model.save();
    }

    if (evaluationSet != null) {
      Evaluation evaluation = evaluate(evaluationSet);
      evaluation.printSummary();
    }

    if (shouldPrintStats) {
      TrainingStats stats = stats();
      stats.print();
    }

    if (completion != null)
      completion.accept(this);
  }

  @Override
  public Evaluation evaluate(TestSet set) {
    Evaluation evaluation = new ModelMetrics(this, set);
    evaluation.evaluate();
    return evaluation;
  }

  @Override
  public Optimizer optimizer() {
    return optimizer;
  }

  @Override
  public Attributes attributes() {
    return attributes.copy();
  }

  @Override
  public Set<EpochAction<NeuralNetwork>> iterationActions() {
    return epochActions;
  }

  public TrainingStats stats() {
    Preconditions.checkArgument(completed, "The training process has not been completed yet!");
    return new TrainingStats(epochs, learningRate, accuracy, time);
  }

  @Override
  public double accuracy() {
    return accuracy;
  }

  @Override
  public void close() {
    synchronized (this) {
      progressTicker.close();
      completed = false;
      accuracy = -1.0;
      loss = -1.0;
    }
  }

  @Override
  public NeuralNetworkModel toModel(String name) {
    WrappedNeuralNetwork wrapper = wrap();
    NeuralNetworkModel model = new NeuralNetworkModel(wrapper);
    model.setActivationFunction(activationFunction);
    model.name(name).saveOnClose(true);
    return model;
  }

  @Override
  public void onCompletion(Consumer<NeuralNetwork> function) {
    this.completion = function;
  }

  @Override
  public NeuralNetworkModel toModel() {
    return toModel(name);
  }

  @Override
  public synchronized void pause() {
    paused = true;
  }

  @Override
  public synchronized void resume() {
    paused = false;
    notifyAll();
  }

  @Override
  public boolean paused() {
    return paused;
  }

  public long countParameters() {
    long count = 0;

    for (double[] weights : weights_input_to_hidden) count += weights.length;
    for (double[] weights : weights_hidden_to_output) count += weights.length;
    for (double ignored : biases_hidden) count++;
    for (double ignored : biases_output) count++;

    return count;
  }

}
