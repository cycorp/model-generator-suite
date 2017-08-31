/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyc.model.exception;

/**
 *
 * @author vijay
 */
public class ModelRuntimeException extends RuntimeException {

  /**
   * Creates a new instance of
   * <code>ModelRuntimeException</code> without detail message.
   */
  public ModelRuntimeException() {
  }

  /**
   * Constructs an instance of
   * <code>ModelRuntimeException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public ModelRuntimeException(String msg) {
    super(msg);
  }
  
  /**
   * Constructs a new exception with the specified detail message and cause.
   */
  public ModelRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Constructs a new exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
   */
  public ModelRuntimeException(Throwable cause) {
    super(cause);
  }
}
