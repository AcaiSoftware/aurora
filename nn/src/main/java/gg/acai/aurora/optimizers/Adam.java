package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 08.09.2023 03:36
 * © Aurora - All Rights Reserved
 */
public class Adam implements Optimizer {

  private final double beta1;
  private final double beta2;
  private final double epsilon;
  private double[] m_t, v_t;
  private int t;

  public Adam(double beta1, double beta2, double epsilon) {
    this.beta1 = beta1;
    this.beta2 = beta2;
    this.epsilon = epsilon;
  }

  @Override
  public void update(int iteration, double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] delta_hidden, double[] delta_output, double[] biases_hidden, double[] biases_output, double[][] inputs, double[] hidden, double learningRate) {
    if (m_t == null) {
      m_t = new double[weights_input_to_hidden.length * weights_input_to_hidden[0].length];
      v_t = new double[weights_input_to_hidden.length * weights_input_to_hidden[0].length];
      t = 0;
    }

    t++;

    double alpha = learningRate * Math.sqrt(1 - Math.pow(beta2, t)) / (1 - Math.pow(beta1, t));
    for (int i = 0; i < weights_input_to_hidden.length; i++) {
      for (int j = 0; j < weights_input_to_hidden[0].length; j++) {
        m_t[i * weights_input_to_hidden[0].length + j] = beta1 * m_t[i * weights_input_to_hidden[0].length + j] + (1 - beta1) * delta_hidden[j] * inputs[iteration][i];
        v_t[i * weights_input_to_hidden[0].length + j] = beta2 * v_t[i * weights_input_to_hidden[0].length + j] + (1 - beta2) * Math.pow(delta_hidden[j] * inputs[iteration][i], 2);
        double m_hat = m_t[i * weights_input_to_hidden[0].length + j] / (1 - Math.pow(beta1, t));
        double v_hat = v_t[i * weights_input_to_hidden[0].length + j] / (1 - Math.pow(beta2, t));
        weights_input_to_hidden[i][j] += alpha * m_hat / (Math.sqrt(v_hat) + epsilon);
      }
    }

    for (int i = 0; i < weights_hidden_to_output.length; i++) {
      for (int j = 0; j < weights_hidden_to_output[0].length; j++) {
        m_t[i * weights_hidden_to_output[0].length + j] = beta1 * m_t[i * weights_hidden_to_output[0].length + j] + (1 - beta1) * delta_output[j] * hidden[i];
        v_t[i * weights_hidden_to_output[0].length + j] = beta2 * v_t[i * weights_hidden_to_output[0].length + j] + (1 - beta2) * Math.pow(delta_output[j] * hidden[i], 2);
        double m_hat = m_t[i * weights_hidden_to_output[0].length + j] / (1 - Math.pow(beta1, t));
        double v_hat = v_t[i * weights_hidden_to_output[0].length + j] / (1 - Math.pow(beta2, t));
        weights_hidden_to_output[i][j] += alpha * m_hat / (Math.sqrt(v_hat) + epsilon);
      }
    }

    for (int i = 0; i < biases_hidden.length; i++) {
      m_t[i] = beta1 * m_t[i] + (1 - beta1) * delta_hidden[i];
      v_t[i] = beta2 * v_t[i] + (1 - beta2) * Math.pow(delta_hidden[i], 2);
      double m_hat = m_t[i] / (1 - Math.pow(beta1, t));
      double v_hat = v_t[i] / (1 - Math.pow(beta2, t));
      biases_hidden[i] += alpha * m_hat / (Math.sqrt(v_hat) + epsilon);
    }

    for (int i = 0; i < biases_output.length; i++) {
      m_t[i] = beta1 * m_t[i] + (1 - beta1) * delta_output[i];
      v_t[i] = beta2 * v_t[i] + (1 - beta2) * Math.pow(delta_output[i], 2);
      double m_hat = m_t[i] / (1 - Math.pow(beta1, t));
      double v_hat = v_t[i] / (1 - Math.pow(beta2, t));
      biases_output[i] += alpha * m_hat / (Math.sqrt(v_hat) + epsilon);
    }
  }

  @Override
  public String toString() {
    return "Adam{" +
      "beta1=" + beta1 +
      ", beta2=" + beta2 +
      ", epsilon=" + epsilon +
      '}';
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(beta1);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(beta2);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(epsilon);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Adam)) return false;
    Adam adam = (Adam) obj;
    return adam.beta1 == beta1 && adam.beta2 == beta2 && adam.epsilon == epsilon;
  }
}
