package gg.acai.aurora.sets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
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

  public TestSet add(double[] input, double[] output) {
    data.put(input, output);
    return this;
  }

  public TestSet add(double[] input, double output) {
    data.put(input, new double[]{output});
    return this;
  }

  public TestSet add(double[] input, boolean output) {
    double[] out = {output ? 1.0 : 0.0};
    data.put(input, out);
    return this;
  }

  public Map<double[], double[]> asMap() {
    return data;
  }

  @Override
  public Iterator<Map.Entry<double[], double[]>> iterator() {
    return data.entrySet().iterator();
  }

}
