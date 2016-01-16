package ar.com.kfgodel.mathe.api;

import ar.com.kfgodel.mathe.impl.interval.EndpointType;
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

  /**
   * Creates a scalar value with the given constant value
   * @param value The value for the created scalar
   * @return The immutable scalar
   */
  static Scalar scalar(double value) {
    return DoubleScalar.create(value);
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
   * Creates a scalar from a nary of numbers, takeing the first one and ignoring the rest.
   * Fails if the nary is empty
   * @param values The nary of numbers
   * @return The created scalar
   */
  static Scalar scalar(Nary<? extends Number> values) throws IllegalArgumentException{
    return values.findFirst()
      .map(Mathe::scalar)
      .orElseThrow(()-> new IllegalArgumentException("Insufficient numbers in the nary to create scalar. Expected 1, got: 0"));
  }

  /**
   * Facility method to create scalar out of objects
   */
  static Scalar scalar(Number value){
    return scalar(value.doubleValue());
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
    return intervalInclusiveInclusive(first, second);
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

  /**
   * Creates an closed interval (inclusive on both endpoints) from the given scalars
   */
  static Interval intervalInclusiveInclusive(Scalar first, Scalar second) {
    return Interval.intervalOrdering(first, second, EndpointType.LOWEST_INCLUSIVE, EndpointType.HIGHEST_INCLUSIVE);
  }

  /**
   * Facility method accepting primitives
   */
  static Interval intervalInclusiveInclusive(double first, double second) {
    return intervalInclusiveInclusive(scalar(first), scalar(second));
  }


    /**
     * Creates an closed-open interval (inclusive on the lowest endpoint, exclusive on the highest) from the given scalars
     */
  static Interval intervalInclusiveExclusive(Scalar first, Scalar second) {
    return Interval.intervalOrdering(first, second, EndpointType.LOWEST_INCLUSIVE, EndpointType.HIGHEST_EXCLUSIVE);
  }

  /**
   * Facility method accepting primitives
   */
  static Interval intervalInclusiveExclusive(double first, double second) {
    return intervalInclusiveExclusive(scalar(first), scalar(second));
  }

  /**
   * Creates an open-closed interval (exclusive on the lowest endpoint, inclusive on the highest) from the given scalars
   */
  static Interval intervalExclusiveInclusive(Scalar first, Scalar second) {
    return Interval.intervalOrdering(first, second, EndpointType.LOWEST_EXCLUSIVE, EndpointType.HIGHEST_INCLUSIVE);
  }

  /**
   * Facility method accepting primitives
   */
  static Interval intervalExclusiveInclusive(double first, double second) {
    return intervalExclusiveInclusive(scalar(first), scalar(second));
  }

  /**
   * Creates an open interval (exclusive on the lowest endpoint, exclusive on the highest) from the given scalars
   */
  static Interval intervalExclusiveExclusive(Scalar first, Scalar second) {
    return Interval.intervalOrdering(first, second, EndpointType.LOWEST_EXCLUSIVE, EndpointType.HIGHEST_EXCLUSIVE);
  }

  /**
   * Facility method accepting primitives
   */
  static Interval intervalExclusiveExclusive(double first, double second) {
    return intervalExclusiveExclusive(scalar(first), scalar(second));
  }
}
