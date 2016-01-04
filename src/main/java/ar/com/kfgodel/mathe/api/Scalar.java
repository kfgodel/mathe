package ar.com.kfgodel.mathe.api;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * This type represents a one dimension vector (or a real number)
 * Created by tenpines on 03/01/16.
 */
@FunctionalInterface
public interface Scalar extends DoubleSupplier, Supplier<Scalar>, Value {
  /**
   * Returns the primitive representation of this scalar current value
   * @return The current double value
   */
  double asDouble();

  @Override
  default double getAsDouble(){
    return asDouble();
  }

  @Override
  default Scalar get(){
    return this;
  }

  /**
   * Indicates the value mutability of this scalar
   * @return An enum indicating the mutability of this instance
   */
  default ScalarMutabilityType mutability(){
    return ScalarMutabilityType.MUTABLE;
  };
}
