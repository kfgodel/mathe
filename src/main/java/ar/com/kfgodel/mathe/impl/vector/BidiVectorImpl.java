package ar.com.kfgodel.mathe.impl.vector;

import ar.com.kfgodel.mathe.api.BidiVector;
import ar.com.kfgodel.mathe.api.Mathe;
import ar.com.kfgodel.mathe.api.Scalar;
import ar.com.kfgodel.mathe.api.ScalarMutabilityType;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;

import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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
  public BidiVector invertX() {
    return vector(firstComponent().invert(), secondComponent());
  }

  @Override
  public BidiVector invertY() {
    return vector(firstComponent(), secondComponent().invert());
  }

  @Override
  public BidiVector scalarProduct(Scalar scalar) {
    return combiningEachComponentWith(scalar, Scalar::multiply);
  }

  @Override
  public BidiVector divide(Scalar divisor) {
    return combiningEachComponentWith(divisor, Scalar::divide);
  }

  @Override
  public BidiVector integered() {
    return applyingToEachComponent(Scalar::integered);
  }

  @Override
  public BidiVector invert() {
    return applyingToEachComponent(Scalar::invert);
  }

  @Override
  public BidiVector plus(BidiVector other) {
    return combineEachComponentPairFrom(other, Scalar::plus);
  }

  @Override
  public BidiVector componentProduct(BidiVector other) {
    return combineEachComponentPairFrom(other, Scalar::multiply);
  }

  @Override
  public BidiVector minus(BidiVector other) {
    return combineEachComponentPairFrom(other, Scalar::minus);
  }


  @Override
  public Nary<Scalar> components() {
    return NaryFromNative.of(firstComponent(), secondComponent());
  }

  @Override
  public BidiVector doubled() {
    return scalarProduct(Mathe.TWO_SCALAR);
  }

  @Override
  public BidiVector halved() {
    return divide(Mathe.TWO_SCALAR);
  }

  @Override
  public BidiVector center() {
    return halved();
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

  private BidiVector applyingToEachComponent(Function<Scalar, Scalar> operation) {
    return vector(NaryFromNative.create(components().map(operation)));
  }

  private BidiVector combiningEachComponentWith(Scalar other, BinaryOperator<Scalar> operation) {
    return vector(
      operation.apply(firstComponent(), other),
      operation.apply(secondComponent(), other)
    );
  }

  /**
   * Combines this vector with the one given, applying a scalar operation to each component pair
   * @param other The vector to combine with
   * @param operation The scalar operation to apply in each of the vector's component
   * @return The new vector with the result
   */
  private BidiVector combineEachComponentPairFrom(BidiVector other, BinaryOperator<Scalar> operation) {
    return vector(
      operation.apply(this.firstComponent(), other.firstComponent()),
      operation.apply(this.secondComponent(), other.secondComponent())
    );
  }

}
