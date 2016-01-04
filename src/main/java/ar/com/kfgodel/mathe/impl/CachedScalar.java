package ar.com.kfgodel.mathe.impl;

import ar.com.kfgodel.mathe.api.Scalar;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Scalar based on a lazily defined constant value
 * Created by tenpines on 03/01/16.
 */
public class CachedScalar implements Scalar {

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

  public static CachedScalar create(DoubleSupplier supplier) {
    CachedScalar scalar = new CachedScalar();
    scalar.supplier = supplier;
    return scalar;
  }
}
