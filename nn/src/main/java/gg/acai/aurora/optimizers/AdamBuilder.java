package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 08.09.2023 03:47
 * © Aurora - All Rights Reserved
 */
public class AdamBuilder {

  private double beta1 = 0.9;
  private double beta2 = 0.999;
  private double epsilon = 1e-8;

  public AdamBuilder beta1(double beta1) {
    this.beta1 = beta1;
    return this;
  }

  public AdamBuilder beta2(double beta2) {
    this.beta2 = beta2;
    return this;
  }

  public AdamBuilder epsilon(double epsilon) {
    this.epsilon = epsilon;
    return this;
  }

  public AdamBuilder from(AdamBuilder other) {
    return new AdamBuilder()
      .beta1(other.beta1)
      .beta2(other.beta2)
      .epsilon(other.epsilon);
  }

  public Adam build() {
    return new Adam(
      beta1,
      beta2,
      epsilon
    );
  }

  @Override
  public String toString() {
    return "AdamBuilder{" +
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
    if (!(obj instanceof AdamBuilder)) {
      return false;
    }
    AdamBuilder other = (AdamBuilder) obj;
    return beta1 == other.beta1
      && beta2 == other.beta2
      && epsilon == other.epsilon;
  }
}
