package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.CachedScalar;
import ar.com.kfgodel.mathe.impl.DoubleScalar;
import ar.com.kfgodel.mathe.impl.SuppliedScalar;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Access point class to Mathe concepts
 * Created by tenpines on 03/01/16.
 */
public interface Mathe {
  /**
   * Creates a scalar value with the given constant value
   * @param value The value for the created scalar
   * @return The immutable scalar
   */
  static Scalar scalar(double value) {
    return DoubleScalar.create(value);
  }

  /**
   * Creates a scalar based on the given function, that can change its value over time
   * @param doubleSupplier The function that defines the value of this scalar
   * @return The variable scalar
   */
  static Scalar scalar(DoubleSupplier doubleSupplier) {
    return SuppliedScalar.create(doubleSupplier);
  }

  /**
   * Creates a scalar value that caches the first result of the given
   * supplier function. <br>
   *   The function will be called lazily when the value is needed
   * @param supplier The function to be called once
   * @return The created scalar
   */
  static Scalar cachedScalar(DoubleSupplier supplier) {
    return CachedScalar.create(supplier);
  }
}