package ar.com.kfgodel.mathe.api;

/**
 * This type represents an object that can be used as numeric value
 * Created by tenpines on 04/01/16.
 */
public interface Value {
  /**
   * Indicates if this value can change over time, or it's constant
   * @return The type of mutability this value has
   */
  ScalarMutabilityType mutability();
}
