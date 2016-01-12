package ar.com.kfgodel.mathe.api;

/**
 * This type represents the set of numbers between two scalars
 * Created by ikari on 11/01/2016.
 */
public interface Interval {
  /**
   * The lower scalar of the two that define this interval.<br>
   *   Any of them if they are equal
   * @return The minimum scalar
   */
  Scalar lowestEndpoint();

  /**
   * The higher scalar of teh two that define this interval.<br>
   *   Any if they are equal
   * @return The maximum scalar
   */
  Scalar highestEndpoint();

  /**
   * Indicates if this interval contains the given element
   * @param other The scalar to test
   * @return true if the endpoints of this interval include the scalar
   */
  boolean contains(Scalar other);

  /**
   * Facility method
   */
  boolean contains(double value);
}
