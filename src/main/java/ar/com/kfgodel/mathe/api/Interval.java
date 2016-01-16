package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.interval.EndpointType;
import ar.com.kfgodel.mathe.impl.interval.IntervalImpl;

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
  Endpoint lowestEndpoint();

  /**
   * The higher scalar of teh two that define this interval.<br>
   *   Any if they are equal
   * @return The maximum scalar
   */
  Endpoint highestEndpoint();

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


  /**
   * Creates an interval with the given endpoints
   * @param lowestEndpoint The lowest value endpoint
   * @param highestEndpoint The upperbound endpoint
   * @return The created interval
   */
  static Interval from(Endpoint lowestEndpoint, Endpoint highestEndpoint) {
    return IntervalImpl.create(lowestEndpoint, highestEndpoint);
  }

  /**
   * Creates an interval with the give parameters. Ordering the first and second scalar
   */
  static Interval intervalOrdering(Scalar first, Scalar second, EndpointType lowestType, EndpointType highestType) {
    Scalar lowest;
    Scalar highest;
    if(first.isGreaterThan(second)){
      highest = first;
      lowest = second;
    }else{
      lowest = first;
      highest = second;
    }
    Endpoint lowestEndpoint = Endpoint.from(lowest, lowestType);
    Endpoint highestEndpoint = Endpoint.from(highest, highestType);
    return from(lowestEndpoint, highestEndpoint);
  }

}
