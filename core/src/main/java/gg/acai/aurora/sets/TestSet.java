package gg.acai.aurora.sets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A test set for model evaluation.
 *
 * @author Clouke
 * @since 27.04.2023 21:52
 * © Aurora - All Rights Reserved
 */
public class TestSet implements Iterable<Map.Entry<double[], double[]>> {

  private final Map<double[], double[]> data;

  public TestSet(Map<double[], double[]> data) {
    this.data = data;
  }

  public TestSet() {
    this.data = new HashMap<>();
  }

  /**
   * Adds a data point to this test set.
   *
   * @param input The input of the data point
   * @param output The output of the data point
   * @return Returns this test set for chaining
   */
  public TestSet add(double[] input, double[] output) {
    data.put(input, output);
    return this;
  }

  /**
   * Adds a data point to this test set.
   *
   * @param input The input of the data point
   * @param output The output of the data point
   * @return Returns this test set for chaining
   */
  public TestSet add(double[] input, double output) {
    data.put(input, new double[]{output});
    return this;
  }

  /**
   * Adds a data point to this test set.
   *
   * @param input The input of the data point
   * @param output The output of the data point (true = 1.0, false = 0.0)
   * @return Returns this test set for chaining
   */
  public TestSet add(double[] input, boolean output) {
    double[] out = {output ? 1.0 : 0.0};
    data.put(input, out);
    return this;
  }

  /**
   * Gets this test set as a map.
   *
   * @return Returns this test set as a map
   */
  public Map<double[], double[]> asMap() {
    return data;
  }

  @Override
  public Iterator<Map.Entry<double[], double[]>> iterator() {
    return data.entrySet().iterator();
  }

}
