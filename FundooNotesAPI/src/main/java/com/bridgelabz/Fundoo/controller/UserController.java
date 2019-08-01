package com.bridgelabz.Fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;
import com.bridgelabz.Fundoo.services.ServiceInterface;

@RestController

@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private ServiceInterface iService;

	/***************** Registration Controller 
	 * @throws Exception ******************/

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> registerUser(@RequestBody RegisterDto register){
		System.out.println("In Register User");
		ResponseStatus response = iService.Registration(register);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************************ Login Controller *******************/

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<ResponseStatus> loginUser(@RequestBody LoginDto login) {
		System.out.println("In Logging User");
		ResponseStatus response = iService.Login(login);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/*********************** Forget Password Controller ********************/

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> forgetPassword(@RequestBody ForgetPasswordDto forgetdto) {
		System.out.println("Forget Password");
		ResponseStatus response = iService.forgetPassword(forgetdto);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/********************** Reset Password Controller **************************/

	@RequestMapping(value = "/resetpassword/{token}", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> resetPassword(@PathVariable("token") String token,
			@RequestBody ResetPasswordDto setpassword) {
		System.out.println("Reset Password");
		ResponseStatus response = iService.resetPassword(token, setpassword);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/***************************** Verify User ***************************/

	@RequestMapping(value = "/verify/{token}", method = RequestMethod.GET)
	public ResponseEntity<ResponseStatus> verifyUser(@PathVariable("token") String token) {
		System.out.println("Verify User");
		ResponseStatus response = iService.verifyUser(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public ResponseEntity<ResponseStatus> getAllUsers(){
		System.out.println("Get all Users");
		ResponseStatus response = iService.getAllUsers();
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
}
