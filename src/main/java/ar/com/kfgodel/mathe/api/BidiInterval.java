package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.interval.BidiIntervalImpl;

/**
 * This type represents a bi dimensional interval
 * Created by ikari on 16/01/2016.
 */
public interface BidiInterval {
  /**
   * Creates a bi-dimensional interval from 2 simple intervals
   * @param firstComponentInterval The interval that restricts first component values
   * @param secondComponentInterval The interval that restricts second component values
   * @return The created bi-interval
   */
  static BidiInterval from(Interval firstComponentInterval, Interval secondComponentInterval) {
    return BidiIntervalImpl.create(firstComponentInterval, secondComponentInterval);
  }

  /**
   * Indicates if the given vector is inside the area delimited by this interval
   * @param vector The vector to test
   * @return True if the vector components fulfill this interval restrictions
   */
  boolean contains(BidiVector vector);

  /**
   * @return The interval that restricts the first component
   */
  Interval firstInterval();

  /**
   * @return The interval that restricts the second component
   */
  Interval secondInterval();

}
