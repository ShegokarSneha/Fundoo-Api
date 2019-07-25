package com.bridgelabz.Fundoo.result;

import org.springframework.stereotype.Component;

@Component
public class ResponseCode {
	
	
	public ResponseStatus getResponse(int statusCode, String message, Object data) {
		ResponseStatus response = new ResponseStatus();
		response.setStatusCode(statusCode);
		response.setMessage(message);
		response.setData(data);
		return response;
	}

/*	public ResponseStatus getResponse(int statusCode, String token, String message) {
		ResponseStatus response = new ResponseStatus();
		response.setStatusCode(statusCode);
		response.setToken(token);
		response.setMessage(message);
		return response;
	}*/

}
