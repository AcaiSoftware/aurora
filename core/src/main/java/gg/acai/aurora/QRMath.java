package gg.acai.aurora;

/**
 * Various mathematical functions that are used throughout Aurora.
 *
 * @author Clouke
 * @since 07.02.2023 23:06
 * © Aurora - All Rights Reserved
 */
public final class QRMath {

  /**
   * Private constructor to prevent instantiation.
   */
  private QRMath() {
    throw new UnsupportedOperationException("Cannot instantiate Math class.");
  }

  /**
   * Rounds a double value to a specified amount of decimal places.
   *
   * @param value  value to be rounded
   * @param places amount of decimal places
   * @return Returns the rounded double value
   */
  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }

  /**
   * Rounds a double value to 2 decimal places.
   *
   * @param value value to be rounded
   * @return Returns the rounded double value
   */
  public static double round(double value) {
    return round(value, 2);
  }

  /**
   * Calculates the sigmoid function of a double value.
   *
   * @param x double value
   * @return Returns the sigmoid function of the double value
   */
  public static double sigmoid(double x) {
    return 1.0 / (1.0 + Math.exp(-x));
  }

  /**
   * Calculates the sigmoid derivative of a double value.
   *
   * @param x double value
   * @return Returns the sigmoid derivative of the double value
   */
  public static double sigmoidDerivative(double x) {
    return x * (1 - x);
  }

  /**
   * Calculates the tanh derivative of a double value.
   *
   * @param x double value
   * @return Returns the tanh function of the double value
   */
  public static double tanhDerivative(double x) {
    return 1 - Math.pow(x, 2);
  }

  /**
   * Calculates the relu function of a double value.
   *
   * @param x double value
   * @return Returns the relu function of the double value
   */
  public static double relu(double x) {
    return Math.max(0, x);
  }

  /**
   * Calculates the relu derivative of a double value.
   *
   * @param x double value
   * @return Returns the relu derivative of the double value
   */
  public static double reluDerivative(double x) {
    return x > 0 ? 1 : 0;
  }

  /**
   * Calculates the leaky relu function of a double value.
   *
   * @param x double value
   * @return Returns the leaky relu function of the double value
   */
  public static double leakyRelu(double x) {
    return x > 0 ? x : 0.01 * x;
  }

  /**
   * Calculates the leaky relu derivative of a double value.
   *
   * @param x double value
   * @return Returns the leaky relu derivative of the double value
   */
  public static double leakyReluDerivative(double x) {
    return x > 0 ? 1 : 0.01;
  }

  /**
   * Calculates the euclidean distance between two double arrays.
   *
   * @param a - first double array
   * @param b - second double array
   * @return double value representing the distance between the two arrays
   */
  public static double distance(double[] a, double[] b) {
    double sum = 0;
    for (int i = 0; i < a.length; i++)
      sum += Math.pow(a[i] - b[i], 2);
    return Math.sqrt(sum);
  }

  /**
   * Calculates the euclidean distance between two double arrays, with weights applied to each element.
   *
   * @param a       - first double array
   * @param b       - second double array
   * @param weights - weights to be applied to each element when calculating the distance
   * @return double value representing the distance between the two arrays
   */
  public static double distance(double[] a, double[] b, double[] weights) {
    double sum = 0;
    for (int i = 0; i < a.length; i++)
      sum += Math.pow(a[i] - b[i], 2) * weights[i];
    return Math.sqrt(sum);
  }

  /**
   * Calculates the euclidean distance between two double arrays, with weights and biases applied to each element.
   *
   * @param a       - first double array
   * @param b       - second double array
   * @param weights - weights to be applied to each element when calculating the distance
   * @param biases  - biases to be applied to each element when calculating the distance
   * @return double value representing the distance between the two arrays
   */
  public static double distance(double[] a, double[] b, double[] weights, double[] biases) {
    double sum = 0;
    for (int i = 0; i < a.length; i++)
      sum += Math.pow(a[i] - b[i], 2) * weights[i] + biases[i];
    return Math.sqrt(sum);
  }

  /**
   * Finds the closest point in a 2D double array to a given 1D double array.
   *
   * @param a - given 1D double array
   * @param b - 2D double array to find the closest point in
   * @return int value representing the index of the closest point in the 2D array
   */
  public static int closestPoint(double[] a, double[][] b) {
    double min = Double.MAX_VALUE;
    int idx = -1;
    for (int i = 0; i < b.length; i++) {
      double dist = distance(a, b[i]);
      if (dist < min) {
        min = dist;
        idx = i;
      }
    }
    return idx;
  }

}
