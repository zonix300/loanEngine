package com.example.loanEngine.exception;

/**
 * BorrowerNotFoundException
 */
public class BorrowerNotFoundException extends RuntimeException {

  public BorrowerNotFoundException() {
    super();
  }

  public BorrowerNotFoundException(String message) {
    super(message);
  }
}
