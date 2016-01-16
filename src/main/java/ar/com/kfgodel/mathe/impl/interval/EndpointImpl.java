package ar.com.kfgodel.mathe.impl.interval;

import ar.com.kfgodel.mathe.api.Endpoint;
import ar.com.kfgodel.mathe.api.Scalar;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Implements the endpoint behavior
 * Created by ikari on 14/01/2016.
 */
public class EndpointImpl implements Endpoint {

  private Scalar referenceScalar;
  private Predicate<Scalar> inclusionOperator;
  private EndpointType type;

  public static EndpointImpl create(Scalar referenceScalar, EndpointType type) {
    EndpointImpl endpoint = new EndpointImpl();
    endpoint.referenceScalar = referenceScalar;
    endpoint.inclusionOperator = type.getOperatorFor(referenceScalar);
    endpoint.type = type;
    return endpoint;
  }

  @Override
  public Scalar referenceScalar() {
    return referenceScalar;
  }

  @Override
  public EndpointType type() {
    return type;
  }

  @Override
  public boolean includes(Scalar other) {
    return inclusionOperator.test(other);
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj instanceof Endpoint){
      Endpoint that = (Endpoint) obj;
      return this.type.equals(that.type()) && this.referenceScalar.equals(that.referenceScalar());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.referenceScalar, this.type);
  }

  @Override
  public String toString() {
    return this.type.represent(this.referenceScalar);
  }
}
