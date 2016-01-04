package ar.com.kfgodel.mathe.impl.vector;

import ar.com.kfgodel.mathe.api.BidiVector;
import ar.com.kfgodel.mathe.api.Scalar;

import java.util.Objects;

/**
 * This type represents a bi dimensional vector based on two pre-defined scalars
 * Created by tenpines on 03/01/16.
 */
public class BidiVectorImpl implements BidiVector {

  private Scalar first;
  private Scalar second;

  public static BidiVectorImpl create(Scalar first, Scalar second) {
    BidiVectorImpl vector = new BidiVectorImpl();
    vector.first = first;
    vector.second = second;
    return vector;
  }

  @Override
  public Scalar firstComponent() {
    return first;
  }

  @Override
  public Scalar secondComponent() {
    return second;
  }

  @Override
  public BidiVector get() {
    return this;
  }

  @Override
  public String toString() {
    return "[" + first + ", " + second + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj instanceof BidiVector){
      BidiVector that = (BidiVector) obj;
      return this.first.equals(that.firstComponent()) && this.second.equals(that.secondComponent());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }
}