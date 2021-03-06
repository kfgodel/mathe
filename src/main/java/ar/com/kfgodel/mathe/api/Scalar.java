package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.scalar.DoubleScalar;
import ar.com.kfgodel.mathe.impl.scalar.LazyScalar;
import ar.com.kfgodel.mathe.impl.scalar.SuppliedScalar;
import ar.com.kfgodel.nary.api.Nary;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * This type represents a one dimension vector (or a real number)
 * Created by tenpines on 03/01/16.
 */
@FunctionalInterface
public interface Scalar extends DoubleSupplier, Supplier<Scalar>, Value {
  
  Scalar ZERO = scalar(0.0);
  Scalar ONE = scalar(1.0);
  Scalar TWO = scalar(2.0);

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
   * @return The value of this scalar casted to int
   */
  default int asInt(){
    return (int) asDouble();
  }

  /**
   * @return The value as float (casted to float)
   */
  default float asFloat(){
    return (float) asDouble();
  }

  /**
   * Indicates the value mutability of this scalar
   * @return An enum indicating the mutability of this instance
   */
  default ScalarMutabilityType mutability(){
    return ScalarMutabilityType.MUTABLE;
  }

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

  /**
   * Generates a new scalar with the inverted sign (negative value)
   * @return The equivalent of multiplying this scalar by -1
   */
  default Scalar invert(){
    return this.mutability().combinedWith(ScalarMutabilityType.IMMUTABLE)
      .generate(()-> -asDouble());
  }

  /**
   * Generates a new scalar with the value converted to an integer
   */
  default Scalar integered(){
    return this.mutability().combinedWith(ScalarMutabilityType.IMMUTABLE)
      .generate(()-> (int)asDouble());
  }

  /**
   * Compares this scalar to the given and returns true if this is less
   */
  default boolean isLessThan(Scalar other){
   return asDouble() < other.asDouble();
  }
  /**
   * Compares this scalar to the given and returns true if this is less or equal
   */
  default boolean isLessOrEqualTo(Scalar other){
    return asDouble() <= other.asDouble();
  }
  /**
   * Compares this scalar to the given and returns true if this is greater or equal
   */
  default boolean isGreaterOrEqualTo(Scalar other){
   return asDouble() >= other.asDouble();
  }

  /**
   * Compares this scalar to the given and returns true if this is greater or equal
   */
  default boolean isGreaterThan(Scalar other){
    return asDouble() > other.asDouble();
  }

  /**
   * Compares this scalar to the given and returns true if this is greater or equal
   */
  default boolean isEqualTo(Scalar other){
    return asDouble() == other.asDouble();
  }

  /**
   * Generates a new scalar with the result of the division
   */
  default Scalar divide(Scalar other){
    return this.combiningMutabilityWith(other, (thisValue, otherValue) -> thisValue / otherValue);
  }

  /**
   * Divides this scalar by two, returning the result
   */
  default Scalar halved(){
    return divide(TWO);
  }

  /**
   * Multiplies this scalar by two, getting the result
   */
  default Scalar doubled(){
    return multiply(TWO);
  }

  /**
   * Facility method that accepts a primitive value
   */
  default Scalar multiply(double otherValue){
    return multiply(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default Scalar plus(double otherValue){
    return plus(scalar(otherValue));
  }


  /**
   * Facility method that accepts a primitive value
   */
  default Scalar divide(double otherValue){
    return divide(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default boolean isLessThan(double otherValue){
    return isLessThan(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default boolean isLessOrEqualTo(double otherValue){
    return isLessOrEqualTo(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default boolean isEqualTo(double otherValue){
    return isEqualTo(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default boolean isGreaterOrEqualTo(double otherValue){
    return isGreaterOrEqualTo(scalar(otherValue));
  }

  /**
   * Facility method that accepts a primitive value
   */
  default boolean isGreaterThan(double otherValue){
    return isGreaterThan(scalar(otherValue));
  }

  /**
   * @return The scalar components of this instance, one per dimension.
   * For scalars it's the nary of itself
   */
  default Nary<Scalar> components(){
    return Nary.of(this);
  }

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

}
