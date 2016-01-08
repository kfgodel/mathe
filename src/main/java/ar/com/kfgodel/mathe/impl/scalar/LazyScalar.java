package ar.com.kfgodel.mathe.impl.scalar;

import ar.com.kfgodel.mathe.api.ScalarMutabilityType;

import java.util.function.DoubleSupplier;

/**
 * This type represents a scalar value that is defined from a function, but
 * its value doesn't change over time
 * Created by tenpines on 04/01/16.
 */
public class LazyScalar extends ScalarSupport {

  private DoubleSupplier supplier;
  private double value;

  @Override
  public double asDouble() {
    if(supplier != null){
      value = supplier.getAsDouble();
      supplier = null;
    }
    return value;
  }

  public static LazyScalar create(DoubleSupplier supplier) {
    LazyScalar scalar = new LazyScalar();
    scalar.supplier = supplier;
    return scalar;
  }

  @Override
  public ScalarMutabilityType mutability() {
    return ScalarMutabilityType.IMMUTABLE;
  }

}
