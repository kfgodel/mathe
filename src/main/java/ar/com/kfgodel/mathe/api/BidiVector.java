package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.nary.api.Nary;

import java.util.function.Supplier;

/**
 * This type represents a bi dimensional vector, composed of 2 scalars
 * Created by tenpines on 03/01/16.
 */
public interface BidiVector extends Supplier<BidiVector>, Value {

  BidiVector ZERO = Mathe.vector(Scalar.ZERO, Scalar.ZERO);
  BidiVector X = Mathe.vector(Scalar.ONE, Scalar.ZERO);
  BidiVector Y = Mathe.vector(Scalar.ZERO, Scalar.ONE);

  /**
   * @return The first component of this vector
   */
  Scalar firstComponent();

  /**
   * Alias for the first component of this vector
   */
  default Scalar x(){
    return firstComponent();
  }

  /**
   * Alias for the first component of this vector
   */
  default Scalar width(){
    return firstComponent();
  }

  /**
   * @return The second component of this vector
   */
  Scalar secondComponent();

  /**
   * Alias for the second component
   */
  default Scalar y(){
    return secondComponent();
  }

  /**
   * Alias for the second component
   */
  default Scalar height(){
    return secondComponent();
  }

  /**
   * Sums each component and returns a new scalar with the result.<br>
   *   If components of both vectors are immutable the result is immutable
   * @param other The vector to sum to this instance
   * @return The resulting vector
   */
  BidiVector plus(BidiVector other);

  /**
   * Generates a vector whose first component is the multiplication of first component
   * of this instance and the one given, and the second component us the multipluication of second components
   * @param other The vector to multiply with
   * @return The created vector
   */
  BidiVector componentProduct(BidiVector other);

  /**
   * Generate a vector with the result of subtracting each each component
   * @param other The vector to subtract
   * @return The difference vector
   */
  BidiVector minus(BidiVector other);

  /**
   * Inverts the sign of the x component
   */
  BidiVector invertX();

  /**
   * Inverts the sign of the y component
   */
  BidiVector invertY();

  /**
   * Generates a new vector with each component multiplied by the given scalar
   * @param scalar The multiplication factor
   * @return The result vector
   */
  BidiVector scalarProduct(Scalar scalar);

  /**
   * Generates a vector with the integer value of each component
   * @return The integered vector
   */
  BidiVector integered();

  /**
   * Inverts both components sign
   */
  BidiVector invert();

  /**
   * Rotates this vector counter-clockwise the amount of given degrees
   * @param degrees The amount of degrees to rotate
   * @return The resulting vector
   */
  BidiVector rotate(double degrees);

  /**
   * Generates a new vector with the result of dividing each component by the given divisor
   */
  BidiVector divide(Scalar divisor);

  /**
   * @return The scalar components of this instance, one per dimension.
   * For vectors it's a nary with two elements, one for each component
   */
  Nary<Scalar> components();

  /**
   * Multiplies each component by 2, doubling this vector module
   * @return The resulting vector
   */
  BidiVector doubled();

  /**
   * Divides each component by 2, reducing this module size by 2
   * @return The resulting vector
   */
  BidiVector halved();

  /**
   * @return The vector that represents the middle point between this vector and the origin
   */
  BidiVector center();
}
