package gg.acai.aurora;

/**
 * A stats accumulator for the purpose of training a neural network
 *
 * @author Clouke
 * @since 03.03.2023 15:01
 * © Aurora - All Rights Reserved
 */
public class TrainingStats {

  private static final String ANSI_BOLD = "\033[1m";
  private static final String RESET = "\033[0m";

  private final int epochs;
  private final double learningRate;
  private final double accuracy;
  private final long time;

  public TrainingStats(int epochs, double learningRate, double accuracy, long time) {
    this.epochs = epochs;
    this.learningRate = learningRate;
    this.accuracy = accuracy;
    this.time = time;
  }

  /**
   * Gets the number of epochs the neural network was trained for
   *
   * @return The number of epochs
   */
  public int epochs() {
    return epochs;
  }

  /**
   * Gets the learning rate the neural network was trained with
   *
   * @return The learning rate
   */
  public double learningRate() {
    return learningRate;
  }

  /**
   * Gets the accuracy of the neural network
   *
   * @return The accuracy
   */
  public double accuracy() {
    return accuracy;
  }

  /**
   * Gets the time taken to train the neural network
   *
   * @return The time taken
   */
  public double time() {
    return time;
  }

  @Override
  public String toString() {
    String acc = accuracy == -1 ? "Missing Accuracy Test" : accuracy + "%";
    double seconds = time / 1000.0D;
    return "\n" + ANSI_BOLD + "Training Stats:" + "\n" + RESET +
      "• Accuracy: " + acc + "\n" +
      "• Time: " + seconds + "s" + "\n";
  }

  /**
   * Prints the training stats to the console
   */
  public void print() {
    System.out.println(this);
  }

}
