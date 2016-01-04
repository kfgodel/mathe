package ar.com.kfgodel.mathe.api;

/**
 * This type defines the mutability options that a scalar has
 * Created by tenpines on 04/01/16.
 */
public enum ScalarMutabilityType {
  /**
   * The scalar value is guaranteed to be unchanged over the time of the application
   */
  IMMUTABLE{
    @Override
    public ScalarMutabilityType combinedWith(ScalarMutabilityType otherType) {
      // Only stays immutable if other is immutable too
      if(otherType.equals(IMMUTABLE)){
        return IMMUTABLE;
      }
      return MUTABLE;
    }
  },
  /**
   * The scalar value may change from each time the scalar is evaluated
   */
  MUTABLE;

  /**
   * Combines this type with the given, returning the expected mutability type for
   * an operation that uses both of them.<br>
   *   An operation involving any mutable value, ends up with a mutable result
   * @param otherType The type to combine with
   * @return The resulting mutability (mutable type absorbs immutable)
   */
  public ScalarMutabilityType combinedWith(ScalarMutabilityType otherType) {
    return MUTABLE;
  }
}
