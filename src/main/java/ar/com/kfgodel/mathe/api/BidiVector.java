package ar.com.kfgodel.mathe.api;

import java.util.function.Supplier;

/**
 * This type represents a bi dimensional vector, composed of 2 scalars
 * Created by tenpines on 03/01/16.
 */
public interface BidiVector extends Supplier<BidiVector>, Value {
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
}
