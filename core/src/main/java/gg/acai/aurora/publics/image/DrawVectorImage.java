package gg.acai.aurora.publics.image;

import gg.acai.aurora.Vec2D;

/**
 * @author Clouke
 * @since 24.01.2023 13:28
 * © Acai - All Rights Reserved
 */
public class DrawVectorImage implements Image {

  private final Vec2D points;

  public DrawVectorImage(Vec2D points) {
    this.points = points;
  }

  @Override
  public String draw() {
    double[] x = points.x();
    double[] y = points.y();

    // Find the min and max values of x and y
    double minX = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double minY = Double.MAX_VALUE;
    double maxY = Double.MIN_VALUE;

    for (int i = 0; i < x.length; i++) {
      minX = Math.min(minX, x[i]);
      maxX = Math.max(maxX, x[i]);
      minY = Math.min(minY, y[i]);
      maxY = Math.max(maxY, y[i]);
    }

    // Scale the values to fit within the console window
    double xScale = 60.0 / (maxX - minX);
    double yScale = 20.0 / (maxY - minY);

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < yScale * (maxY - minY); i++) {
      for (int j = 0; j < xScale * (maxX - minX); j++) {
        boolean pointFound = false;
        for (int k = 0; k < x.length; k++) {
          if (Math.round(x[k] * xScale) == j && Math.round(y[k] * yScale) == i) {
            builder.append("*");
            pointFound = true;
            break;
          }
        }
        if (!pointFound) builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  @Override
  public String description() {
    return "Visualizes a 2D vector's points";
  }
}
