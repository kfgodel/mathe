package ar.com.kfgodel.mathe.impl.scalar;

import ar.com.kfgodel.mathe.api.Scalar;

/**
 * Base class used to define common methods for all scalar values, that
 * cannot be defined as default interface methods
 * Created by tenpines on 03/01/16.
 */
public abstract class ScalarSupport implements Scalar {

  @Override
  public boolean equals(Object obj) {
    if(obj == this){
      return true;
    }
    if(obj instanceof Scalar){
      return this.isEqualTo((Scalar) obj);
    }
    if(obj instanceof Number){
      return asDouble() == ((Number) obj).doubleValue();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Double.hashCode(asDouble());
  }

  @Override
  public String toString() {
    return String.valueOf(this.asDouble());
  }
}
