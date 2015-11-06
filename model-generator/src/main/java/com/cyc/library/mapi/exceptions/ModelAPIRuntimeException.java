/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyc.library.mapi.exceptions;

/**
 *
 * @author vijay
 */
public class ModelAPIRuntimeException extends RuntimeException {

  /**
   * Creates a new instance of
   * <code>ModelAPIRuntimeException</code> without detail message.
   */
  public ModelAPIRuntimeException() {
  }

  /**
   * Constructs an instance of
   * <code>ModelAPIRuntimeException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public ModelAPIRuntimeException(String msg) {
    super(msg);
  }
  
  /**
   * Constructs a new exception with the specified detail message and cause.
   */
  public ModelAPIRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Constructs a new exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
   */
  public ModelAPIRuntimeException(Throwable cause) {
    super(cause);
  }
}
