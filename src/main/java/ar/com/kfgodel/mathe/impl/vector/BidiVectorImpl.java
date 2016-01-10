package ar.com.kfgodel.mathe.impl.vector;

import ar.com.kfgodel.mathe.api.BidiVector;
import ar.com.kfgodel.mathe.api.Scalar;
import ar.com.kfgodel.mathe.api.ScalarMutabilityType;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;

import java.util.Objects;
import java.util.function.BinaryOperator;

import static ar.com.kfgodel.mathe.api.Mathe.vector;

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
  public BidiVector plus(BidiVector other) {
    return combineEachComponentApplying(Scalar::plus, other);
  }

  @Override
  public BidiVector componentProduct(BidiVector other) {
    return combineEachComponentApplying(Scalar::multiply, other);
  }

  @Override
  public BidiVector minus(BidiVector other) {
    return combineEachComponentApplying(Scalar::minus, other);
  }

  @Override
  public BidiVector invertX() {
    return vector(firstComponent().invert(), secondComponent());
  }

  @Override
  public BidiVector invertY() {
    return vector(firstComponent(), secondComponent().invert());
  }

  @Override
  public BidiVector scalarProduct(Scalar scalar) {
    return applyingToEachComponent(Scalar::multiply, scalar);
  }

  @Override
  public BidiVector divide(Scalar divisor) {
    return applyingToEachComponent(Scalar::divide, divisor);
  }

  @Override
  public Nary<Scalar> components() {
    return NaryFromNative.of(firstComponent(), secondComponent());
  }


  @Override
  public BidiVector integered() {
    return vector(firstComponent().integered(), secondComponent().integered());
  }

  @Override
  public BidiVector invert() {
    return vector(firstComponent().invert(), secondComponent().invert());
  }

  @Override
  public BidiVector rotate(double degrees) {
    double angleInRadians = Math.toRadians(degrees);
    double cos = Math.cos(angleInRadians);
    double sin = Math.sin(angleInRadians);
    return vector(
      firstComponent().combiningMutabilityWith(secondComponent(), (x, y)-> x * cos - y * sin),
      firstComponent().combiningMutabilityWith(secondComponent(), (x, y)-> x * sin + y * cos)
    );
  }

  /**
   * Combines this vector with the one given, applying a scalar operation to each component pair
   * @param operation The scalar operation to apply in each of the vector's component
   * @param other The vector to combine with
   * @return The new vector with the result
   */
  private BidiVector combineEachComponentApplying(BinaryOperator<Scalar> operation, BidiVector other) {
    return vector(
      operation.apply(this.firstComponent(), other.firstComponent()),
      operation.apply(this.secondComponent(), other.secondComponent())
    );
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

  @Override
  public ScalarMutabilityType mutability() {
    return firstComponent().mutability().combinedWith(secondComponent().mutability());
  }

  private BidiVector applyingToEachComponent(BinaryOperator<Scalar> operation, Scalar other) {
    return vector(
      operation.apply(firstComponent(), other),
      operation.apply(secondComponent(), other)
    );
  }
}
