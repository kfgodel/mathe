package ar.com.kfgodel.mathe.api;

import java.util.function.DoubleBinaryOperator;
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

  /**
   * Sums the value of this scalar to the one given.<br>
   *   The result mutability depends on both scalar mutability
   * @param other The scalar to add
   * @return The resulting scalar
   */
  default Scalar plus(Scalar other){
    return this.combiningMutabilityWith(other, (thisValue, otherValue) -> thisValue + otherValue );
  }

  /**
   * Generates a scalar with the result of the multiplication of this two scalars
   * @param other The scalar to multiply this with
   * @return The resulting scalar
   */
  default Scalar multiply(Scalar other){
    return this.combiningMutabilityWith(other, (thisValue, otherValue) -> thisValue * otherValue );
  }

  /**
   * Generates a scalar that combines values with the given scalar and applying the given operation.<br>
   *   The new scalar mutability will depend on original scalar mutability
   * @param other The scalar to combine
   * @param operation The operation that defines the combination
   * @return The resulting scalar
   */
  default Scalar combiningMutabilityWith(Scalar other, DoubleBinaryOperator operation){
    return this.mutability().combinedWith(other.mutability())
      .generate(()-> operation.applyAsDouble(this.asDouble(), other.asDouble()));
  }

  /**
   * Generates a new scalar with the subtraction of the given scalar to this
   * @param other The scalar to subtract
   * @return The difference scalar
   */
  default Scalar minus(Scalar other){
    return this.combiningMutabilityWith(other, (thisValue, otherValue) -> thisValue - otherValue );
  }
}
