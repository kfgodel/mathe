package ar.com.kfgodel.mathe.api;

import java.text.Bidi;
import java.util.function.Supplier;

/**
 * This type represents a bi dimensional vector, composed of 2 scalars
 * Created by tenpines on 03/01/16.
 */
public interface BidiVector extends Supplier<BidiVector> {
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
}
