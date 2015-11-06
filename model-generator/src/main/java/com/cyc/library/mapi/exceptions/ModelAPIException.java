package com.cyc.library.mapi.exceptions;

/**
 *
 * @author nwinant
 */
public class ModelAPIException extends Exception {
  
  /**
   * Constructs a new exception with null as its detail message.
   */
  public ModelAPIException() {
    super();
  }
  
  /**
   * Constructs a new exception with the specified detail message.
   */
  public ModelAPIException(String message) {
    super(message);
  }
  
  /**
   * Constructs a new exception with the specified detail message and cause.
   */
  public ModelAPIException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Constructs a new exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
   */
  public ModelAPIException(Throwable cause) {
    super(cause);
  }
}
