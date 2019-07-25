package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

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

	/***************** Registration Controller ******************/

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> registerUser(@RequestBody RegisterDto register, HttpServletRequest request,
			HttpStatus httpStatus) {
		System.out.println("In Register User");
		ResponseStatus response = iService.Registration(register, request);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************************ Login Controller *******************/

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<ResponseStatus> loginUser(@RequestBody LoginDto login, HttpServletRequest request) {
		System.out.println("In Logging User");
		ResponseStatus response = iService.Login(login, request);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/*********************** Forget Password Controller ********************/

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> forgetPassword(@RequestBody ForgetPasswordDto forgetdto,
			HttpServletRequest request) {
		System.out.println("Forget Password");
		ResponseStatus response = iService.forgetPassword(forgetdto, request);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/********************** Reset Password Controller **************************/

	@RequestMapping(value = "/resetpassword/{token}", method = RequestMethod.POST)
	public ResponseEntity<ResponseStatus> resetPassword(@PathVariable("token") String token,
			@RequestBody ResetPasswordDto setpassword, HttpServletRequest request) {
		System.out.println("Reset Password");
		ResponseStatus response = iService.resetPassword(token, setpassword, request);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/***************************** Verify User ***************************/

	@RequestMapping(value = "/verify/{token}", method = RequestMethod.GET)
	public ResponseEntity<ResponseStatus> verifyUser(@PathVariable("token") String token, HttpServletRequest request) {
		System.out.println(token);
		ResponseStatus response = iService.verifyUser(token, request);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
