

package com.loyalty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource not found exception.
 *
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = -7606892528613202960L;

/**
   * Instantiates a new Resource not found exception.
   *
   * @param message the message
   */
  public ConflictException(String message) {
    super(message);
  }
}
