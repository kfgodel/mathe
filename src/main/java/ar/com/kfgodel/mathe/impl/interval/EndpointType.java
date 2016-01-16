package ar.com.kfgodel.mathe.impl.interval;

import ar.com.kfgodel.mathe.api.Scalar;

import java.util.function.Predicate;

/**
 * This type represents the different options for the restrictions of an endpoint
 * Created by ikari on 14/01/2016.
 */
public enum EndpointType {
  LOWEST_INCLUSIVE{
    @Override
    public Predicate<Scalar> getOperatorFor(Scalar referenceScalar) {
      return referenceScalar::isLessOrEqualTo;
    }

    @Override
    public String represent(Scalar scalar) {
      return "[" + scalar;
    }
  },
  LOWEST_EXCLUSIVE{
    @Override
    public Predicate<Scalar> getOperatorFor(Scalar referenceScalar) {
      return referenceScalar::isLessThan;
    }

    @Override
    public String represent(Scalar scalar) {
      return "(" + scalar;
    }
  },
  HIGHEST_INCLUSIVE{
    @Override
    public Predicate<Scalar> getOperatorFor(Scalar referenceScalar) {
      return referenceScalar::isGreaterOrEqualTo;
    }

    @Override
    public String represent(Scalar scalar) {
      return scalar + "]";
    }
  },
  HIGHEST_EXCLUSIVE {
    @Override
    public Predicate<Scalar> getOperatorFor(Scalar referenceScalar) {
      return referenceScalar::isGreaterThan;
    }

    @Override
    public String represent(Scalar scalar) {
      return scalar + ")";
    }
  };

  /**
   * Gets the correct operator to use when comparin the reference scalar to others
   */
  public abstract Predicate<Scalar> getOperatorFor(Scalar referenceScalar);

  /**
   * Generates a string representation of this restriction around the given scalar
   */
  public abstract String represent(Scalar scalar);
}
