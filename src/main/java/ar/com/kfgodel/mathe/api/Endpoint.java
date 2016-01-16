package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.interval.EndpointImpl;
import ar.com.kfgodel.mathe.impl.interval.EndpointType;

import static ar.com.kfgodel.mathe.api.Mathe.scalar;

/**
 * This type represents the end of an interval, defined by a number an a restriction (inclusion or exclusion)
 *
 * Created by ikari on 14/01/2016.
 */
public interface Endpoint {
  /**
   * Facility method accepting primitive
   */
  static Endpoint lowestIncluding(double lowestValue) {
    return lowestIncluding(scalar(lowestValue));
  }

  /**
   * Creates an endpoint that represents the lowest acceptable value, including it
   * @param lowestScalar The value to accept
   * @return The new endpoint
   */
  static Endpoint lowestIncluding(Scalar lowestScalar) {
    return EndpointImpl.create(lowestScalar, EndpointType.LOWEST_INCLUSIVE);
  }

  /**
   * Creates an endpoint that represents the lowest acceptable bound, excluding its value
   * @param lowestScalar The value to reject as limit
   * @return The new endpoint
   */
  static Endpoint lowestExcluding(Scalar lowestScalar) {
    return EndpointImpl.create(lowestScalar, EndpointType.LOWEST_EXCLUSIVE);
  }

  /**
   * Facility method that accepts primitives
   */
  static Endpoint lowestExcluding(double lowestValue) {
    return lowestExcluding(scalar(lowestValue));
  }

  /**
   * Creates an endpoint that represents the highest acceptable bound, excluding its value
   * @param highestScalar The value to reject as limit
   * @return The new endpoint
   */
  static Endpoint highestExcluding(Scalar highestScalar) {
    return EndpointImpl.create(highestScalar, EndpointType.HIGHEST_EXCLUSIVE);
  }

  /**
   * Facility method that accepts primitives
   */
  static Endpoint highestExcluding(double highestValue) {
    return highestExcluding(scalar(highestValue));
  }

  /**
   * Creates an endpoint that represents the highest acceptable value, including it
   * @param highestScalar The value to accept as maximum
   * @return The new endpoint
   */
  static Endpoint highestIncluding(Scalar highestScalar) {
    return EndpointImpl.create(highestScalar, EndpointType.HIGHEST_INCLUSIVE);
  }

  /**
   * Facility method that accepts primitives
   */
  static Endpoint highestIncluding(double highestValue) {
    return highestIncluding(scalar(highestValue));
  }



  /**
   * @return The scalar that defines the value to which this endpoint is constrained to
   */
  Scalar referenceScalar();

  /**
   * @return The type that defines the restriction of this endpoint
   */
  EndpointType type();

  /**
   * Indicates if this endpoint as defined includes the given scalar, or considers it outside the restriction
   * @param other The scalar to compare to
   * @return True if the given scalar can be considered inside this endpoint
   */
  boolean includes(Scalar other);

  /**
   * Creates an endpoint with a pre-defined type
   * @param reference The number to use as reference
   * @param restrictionType The restriction to apply around the reference to define inclusion
   * @return The created endpoint
   */
  static Endpoint from(Scalar reference, EndpointType restrictionType) {
    return EndpointImpl.create(reference, restrictionType);
  }
}
