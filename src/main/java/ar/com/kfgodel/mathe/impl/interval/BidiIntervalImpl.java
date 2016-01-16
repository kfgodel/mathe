package ar.com.kfgodel.mathe.impl.interval;

import ar.com.kfgodel.mathe.api.BidiInterval;
import ar.com.kfgodel.mathe.api.BidiVector;
import ar.com.kfgodel.mathe.api.Interval;

import java.util.Objects;

/**
 * Implements a bi interval
 * Created by ikari on 16/01/2016.
 */
public class BidiIntervalImpl implements BidiInterval {

  private Interval firstInterval;
  private Interval secondInterval;

  public static BidiIntervalImpl create(Interval firstInterval, Interval secondInterval) {
    BidiIntervalImpl bidiInterval = new BidiIntervalImpl();
    bidiInterval.firstInterval = firstInterval;
    bidiInterval.secondInterval = secondInterval;
    return bidiInterval;
  }

  @Override
  public boolean contains(BidiVector vector) {
    return this.firstInterval.contains(vector.firstComponent()) && this.secondInterval.contains(vector.secondComponent());
  }

  @Override
  public Interval firstInterval() {
    return firstInterval;
  }

  @Override
  public Interval secondInterval() {
    return secondInterval;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj instanceof BidiInterval){
      BidiInterval that = (BidiInterval) obj;
      return this.firstInterval.equals(that.firstInterval()) && this.secondInterval.equals(that.secondInterval());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.firstInterval, this.secondInterval);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.firstInterval);
    builder.append("; ");
    builder.append(this.secondInterval);
    return builder.toString();
  }
}
