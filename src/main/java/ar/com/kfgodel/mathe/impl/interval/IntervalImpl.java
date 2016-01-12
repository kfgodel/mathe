package ar.com.kfgodel.mathe.impl.interval;

import ar.com.kfgodel.mathe.api.Interval;
import ar.com.kfgodel.mathe.api.Mathe;
import ar.com.kfgodel.mathe.api.Scalar;

import java.util.Objects;

/**
 * Implementation of an interval using scalars
 * Created by ikari on 11/01/2016.
 */
public class IntervalImpl implements Interval {

  private Scalar lowest;
  private Scalar highest;

  public static IntervalImpl create(Scalar lowest, Scalar highest) {
    IntervalImpl interval = new IntervalImpl();
    interval.lowest = lowest;
    interval.highest = highest;
    return interval;
  }

  @Override
  public Scalar lowestEndpoint() {
    return lowest;
  }

  @Override
  public Scalar highestEndpoint() {
    return highest;
  }

  @Override
  public boolean contains(Scalar other) {
    return lowest.isLessOrEqualTo(other) && highest.isGreaterOrEqualTo(other);
  }

  @Override
  public boolean contains(double value) {
    return contains(Mathe.scalar(value));
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj instanceof Interval){
      Interval that = (Interval) obj;
      return this.lowest.equals(that.lowestEndpoint()) && this.highest.equals(that.highestEndpoint());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.lowest, this.highest);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    builder.append(this.lowest);
    builder.append("; ");
    builder.append(this.highest);
    builder.append("]");
    return builder.toString();
  }
}
