package ar.com.kfgodel.mathe.api;

/**
 * This type represents an error on mathe usage
 * Created by ikari on 10/01/2016.
 */
public class MatheException extends RuntimeException {
  public MatheException(String message) {
    super(message);
  }

  public MatheException(String message, Throwable cause) {
    super(message, cause);
  }

  public MatheException(Throwable cause) {
    super(cause);
  }
}
