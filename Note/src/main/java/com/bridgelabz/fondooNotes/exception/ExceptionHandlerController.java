package com.bridgelabz.fondooNotes.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fondooNotes.response.ResponseCode;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@Autowired
	private ResponseCode responseCode;
	private ResponseStatus response;

	@ExceptionHandler(value = BlankException.class)
	public ResponseEntity<ResponseStatus> blankExceptionHandler(BlankException e) {
		response = responseCode.getResponse(404, e.getMessage(), null);
		System.out.println(e.getMessage());
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = AlreadyExistsException.class)
	public final ResponseEntity<ResponseStatus> alreadyExistsExceptionHandler(AlreadyExistsException e, Object obj){
		response = responseCode.getResponse(400, e.getMessage(), null);
		System.out.println(e.getMessage());
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public final ResponseEntity<ResponseStatus> notFoundExceptionHandler(NotFoundException e){
		response = responseCode.getResponse(400, e.getMessage(), null);
		System.out.println(e.getMessage());
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
