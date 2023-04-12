package gg.acai.aurora.image;

import com.mitchtalmadge.asciigraph.ASCIIGraph;
import gg.acai.aurora.ml.regression.linear.LRResult;

/**
 * @author Clouke
 * @since 24.01.2023 13:52
 * © Acai - All Rights Reserved
 */
public class LRImage implements Image {

  private final LRResult regression;
  private int size = 21;

  public LRImage(LRResult regression, int size) {
    this.regression = regression;
    this.size = size;
  }

  public LRImage(LRResult regression) {
    this.regression = regression;
  }

  @Override
  public String draw() {
    double slope = regression.slope();
    double intercept = regression.intercept();

    double[] ys = new double[size];

    int index = 0;
    for (int x = -10; x <= 10; x++) {
      double y = slope * x + intercept;
      ys[index] = y;
      index++;
    }

    return ASCIIGraph.fromSeries(ys).plot();
  }

  @Override
  public String description() {
    return "Draws a linear regression graph of the data.";
  }
}
