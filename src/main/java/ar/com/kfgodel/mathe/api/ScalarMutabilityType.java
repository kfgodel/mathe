package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.scalar.LazyScalar;

import java.util.function.DoubleSupplier;

/**
 * This type defines the mutability options that a scalar has
 * Created by tenpines on 04/01/16.
 */
public enum ScalarMutabilityType {
  /**
   * The scalar value is guaranteed to be unchanged over the time of the application
   */
  IMMUTABLE{
    @Override
    public ScalarMutabilityType combinedWith(ScalarMutabilityType otherType) {
      // Only stays immutable if other is immutable too
      if(otherType.equals(IMMUTABLE)){
        return IMMUTABLE;
      }
      return MUTABLE;
    }

    @Override
    public Scalar generate(DoubleSupplier supplier) {
      return LazyScalar.create(supplier);
    }
  },
  /**
   * The scalar value may change from each time the scalar is evaluated
   */
  MUTABLE;

  /**
   * Combines this type with the given, returning the expected mutability type for
   * an operation that uses both of them.<br>
   *   An operation involving any mutable value, ends up with a mutable result
   * @param otherType The type to combine with
   * @return The resulting mutability (mutable type absorbs immutable)
   */
  public ScalarMutabilityType combinedWith(ScalarMutabilityType otherType) {
    return MUTABLE;
  }

  /**
   * Generates a scalar based on the supplied function that respects this
   * type of mutability (mutable types, evaluates the function always, immutable types only one)
   * @param supplier The expression used to generate the scalar value
   * @return The created scalar
   */
  public Scalar generate(DoubleSupplier supplier) {
    return Mathe.scalar(supplier);
  }
}
