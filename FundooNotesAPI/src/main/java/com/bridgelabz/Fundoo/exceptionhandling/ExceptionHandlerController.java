package com.bridgelabz.Fundoo.exceptionhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.Fundoo.result.ResponseCode;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@Autowired
	private ResponseCode responseCode;
	private ResponseStatus response;

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ResponseStatus> exceptionHandling() {
		response = responseCode.getResponse(404, "User Not Found", null);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public final ResponseEntity<ResponseStatus> exceptionHandler(){
		response = responseCode.getResponse(400, "User Already Exist...", null);
		System.out.println("\nUser Already Registered");
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<ResponseStatus> allExceptionHandler(Exception ex) {
		response = responseCode.getResponse(500, ex.getMessage(), null);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseStatus> assertionException(final IllegalArgumentException ex) {
		response = responseCode.getResponse(404, ex.getMessage(), null);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.NOT_FOUND);
	}

}
