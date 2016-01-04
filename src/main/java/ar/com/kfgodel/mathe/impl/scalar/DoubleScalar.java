package ar.com.kfgodel.mathe.impl.scalar;

import ar.com.kfgodel.mathe.api.ScalarMutabilityType;

/**
 * Constant value scalar based on a primitive double
 * Created by tenpines on 03/01/16.
 */
public class DoubleScalar extends ScalarSupport {

  private double value;

  @Override
  public double asDouble() {
    return value;
  }

  public static DoubleScalar create(double value) {
    DoubleScalar scalar = new DoubleScalar();
    scalar.value = value;
    return scalar;
  }

  @Override
  public ScalarMutabilityType mutability() {
    return ScalarMutabilityType.IMMUTABLE;
  }
}
