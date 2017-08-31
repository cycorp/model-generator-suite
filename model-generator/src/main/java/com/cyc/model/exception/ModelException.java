package com.cyc.model.exception;

/**
 *
 * @author nwinant
 */
public class ModelException extends Exception {
  
  /**
   * Constructs a new exception with null as its detail message.
   */
  public ModelException() {
    super();
  }
  
  /**
   * Constructs a new exception with the specified detail message.
   */
  public ModelException(String message) {
    super(message);
  }
  
  /**
   * Constructs a new exception with the specified detail message and cause.
   */
  public ModelException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Constructs a new exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
   */
  public ModelException(Throwable cause) {
    super(cause);
  }
}
