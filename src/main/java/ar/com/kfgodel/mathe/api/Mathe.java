package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.interval.IntervalImpl;
import ar.com.kfgodel.mathe.impl.scalar.DoubleScalar;
import ar.com.kfgodel.mathe.impl.scalar.LazyScalar;
import ar.com.kfgodel.mathe.impl.scalar.SuppliedScalar;
import ar.com.kfgodel.mathe.impl.vector.BidiVectorImpl;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

/**
 * Access point class to Mathe concepts
 * Created by tenpines on 03/01/16.
 */
public interface Mathe {

  Scalar ZERO_SCALAR = scalar(0.0);
  Scalar ONE_SCALAR = scalar(1.0);
  Scalar TWO_SCALAR = scalar(2.0);

  BidiVector ZERO_VECTOR = vector(ZERO_SCALAR, ZERO_SCALAR);
  BidiVector X_VECTOR = vector(ONE_SCALAR, ZERO_SCALAR);
  BidiVector Y_VECTOR = vector(ZERO_SCALAR, ONE_SCALAR);

  /**
   * Creates a scalar value with the given constant value
   * @param value The value for the created scalar
   * @return The immutable scalar
   */
  static Scalar scalar(double value) {
    return DoubleScalar.create(value);
  }

  static Scalar scalar(Nary<? extends Number> values){
    double value = values.findFirst()
      .orElseThrow(()-> new IllegalArgumentException("Insufficient numbers in the nary to create scalar. Expected 1, got: 0"))
      .doubleValue();
    return scalar(value);
  }

  /**
   * Facility method to create scalar out of objects
   */
  static Scalar scalar(Number value){
    return scalar(value.doubleValue());
  }

  /**
   * Creates a scalar based on the given function, that can change its value over time
   * @param doubleSupplier The function that defines the value of this scalar
   * @return The variable scalar
   */
  static Scalar scalar(DoubleSupplier doubleSupplier) {
    return SuppliedScalar.create(doubleSupplier);
  }

  /**
   * Creates a scalar based on the given function result, that is executed only
   * the first time the value is accessed
   * @param supplier The function to get the scalar value
   * @return The created scalar
   */
  static Scalar lazyScalar(DoubleSupplier supplier) {
    return LazyScalar.create(supplier);
  }


  /**
   * Creates a bi dimensional vector based on the pair of scalars given.<br>
   *   Properties of this vector will depend on properties of the scalars
   * @param first The first coordinate (also known as x, or width)
   * @param second The second coordinate (also known as y, or height)
   * @return The created vector
   */
  static BidiVector vector(Scalar first, Scalar second) {
    return BidiVectorImpl.create(first, second);
  }

  /**
   * Expansion creation method that takes an unknown set of elements and expands to a vector.<br>
   *   Only the first two elements of the nary are consumed
   * @param components The nary of components
   * @return The created vector
   */
  static BidiVector vector(Nary<Scalar> components) {
    List<Scalar> componentList = components.limit(2).collect(Collectors.toList());
    int componentCount = componentList.size();
    if(componentCount != 2){
      throw new IllegalArgumentException("Insufficient scalars in the nary to create a vector. Expected 2, got: " + componentCount);
    }
    Scalar firstComponent = componentList.get(0);
    Scalar secondComponent = componentList.get(1);
    return vector(firstComponent, secondComponent);
  }

  /**
   * Facility method to create the vector out of a stream of numbers
   */
  static BidiVector vectorFrom(Nary<? extends  Number> components) {
    Nary<Scalar> scalarNary = NaryFromNative.create(components.map(Mathe::scalar));
    return vector(scalarNary);
  }


    /**
     * Facility method to reduce verbosity. Creates a vector out of a pair of primitives
     * @return The created vector out of two constant scalars
     */
  static BidiVector vector(double firstComponent, double secondComponent) {
    return vector(scalar(firstComponent), scalar(secondComponent));
  }
  /**
   * Facility method to reduce verbosity. Creates a vector out of a mix of primitives and scalar
   * @return The created vector out of two constant scalars
   */
  static BidiVector vector(Scalar firstComponent, double secondComponent) {
    return vector(firstComponent, scalar(secondComponent));
  }
  /**
   * Facility method to reduce verbosity. Creates a vector out of a mix of primitives and scalar
   * @return The created vector out of two constant scalars
   */
  static BidiVector vector(double firstComponent, Scalar secondComponent) {
    return vector(scalar(firstComponent), secondComponent);
  }

  /**
   * Creates an intervale with the given values.<br>
   * @return The interval defined by the pair of scalars
   */
  static Interval interval(Scalar first, Scalar second){
    Scalar lowest;
    Scalar highest;
    if(first.isGreaterThan(second)){
      highest = first;
      lowest = second;
    }else{
      lowest = first;
      highest = second;
    }
    return IntervalImpl.create(lowest, highest);
  }
  /**
   * Facility method accepting primitives
   */
  static Interval interval(double first, Scalar second){
    return interval(scalar(first),second);
  }
  /**
   * Facility method accepting primitives
   */
  static Interval interval(double first, double second){
    return interval(scalar(first),scalar(second));
  }
  /**
   * Facility method accepting primitives
   */
  static Interval interval(Scalar first, double second){
    return interval(first, scalar(second));
  }


}
