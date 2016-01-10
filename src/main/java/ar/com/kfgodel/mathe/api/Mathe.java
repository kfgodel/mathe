package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.scalar.DoubleScalar;
import ar.com.kfgodel.mathe.impl.scalar.LazyScalar;
import ar.com.kfgodel.mathe.impl.scalar.SuppliedScalar;
import ar.com.kfgodel.mathe.impl.vector.BidiVectorImpl;

import java.util.function.DoubleSupplier;

/**
 * Access point class to Mathe concepts
 * Created by tenpines on 03/01/16.
 */
public interface Mathe {

  Scalar ZERO_SCALAR = scalar(0.0);
  Scalar UNITY_SCALAR = scalar(1.0);

  BidiVector ZERO_VECTOR = vector(ZERO_SCALAR, ZERO_SCALAR);
  BidiVector X_VECTOR = vector(UNITY_SCALAR, ZERO_SCALAR);
  BidiVector Y_VECTOR = vector(ZERO_SCALAR, UNITY_SCALAR);

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
   * Creates a scalar based on the given function result, that is executed only
   * the first time the value is accessed
   * @param supplier The function to get the scalar value
   * @return The created scalar
   */
  static Scalar lazyScalar(DoubleSupplier supplier) {
    return LazyScalar.create(supplier);
  }


  /**
   * Creates a bi dimensional vector based on the pair of scalars given.<br>
   *   Properties of this vector will depend on properties of the scalars
   * @param first The first coordinate (also known as x, or width)
   * @param second The second coordinate (also known as y, or height)
   * @return The created vector
   */
  static BidiVector vector(Scalar first, Scalar second) {
    return BidiVectorImpl.create(first, second);
  }

  /**
   * Facility method to reduce verbosity. Creates a vector out of a pair of primitives
   * @return The created vector out of two constant scalars
   */
  static BidiVector vector(double firstComponent, double secondComponent) {
    return vector(scalar(firstComponent), scalar(secondComponent));
  }

}
