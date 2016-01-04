package ar.com.kfgodel.mathe.impl;

import ar.com.kfgodel.mathe.api.Scalar;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Scalar based on a supplier function that may change its value over time
 * Created by tenpines on 03/01/16.
 */
public class SuppliedScalar implements Scalar {

  private DoubleSupplier supplier;

  @Override
  public double asDouble() {
    return supplier.getAsDouble();
  }

  public static SuppliedScalar create(DoubleSupplier supplier) {
    SuppliedScalar scalar = new SuppliedScalar();
    scalar.supplier = supplier;
    return scalar;
  }
}