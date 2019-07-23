package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.LoginRegistrationStatus;
import com.bridgelabz.Fundoo.services.ServiceImplementation;

@RestController

@RequestMapping(value = "/user")
public class LoginRegistrationController {

	@Autowired
	private ServiceImplementation serviceImplementation;

	/***************** Registration Controller ******************/

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public LoginRegistrationStatus registerUser(@RequestBody RegisterDto register, HttpServletRequest request) {
		System.out.println("In Register User");
		LoginRegistrationStatus status = serviceImplementation.Registration(register, request);
		return status;
	}

	/************************ Login Controller *******************/

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public LoginRegistrationStatus loginUser(@RequestBody LoginDto login, HttpServletRequest request) {
		System.out.println("In Logging User");
		LoginRegistrationStatus loginstatus = serviceImplementation.Login(login, request);
		return loginstatus;
	}

	/*********************** Forget Password Controller *********************/

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public LoginRegistrationStatus forgetPassword(@RequestBody ForgetPasswordDto forgetdto,
			HttpServletRequest request) {
		System.out.println("Forget Password");
		LoginRegistrationStatus status = serviceImplementation.forgetPassword(forgetdto, request);
		return status;
	}

	/********************** Reset Password Controller ***************************/

	@RequestMapping(value = "/resetpassword/{token}", method = RequestMethod.POST)
	public LoginRegistrationStatus resetPassword(@PathVariable("token") String token,
			@RequestBody ResetPasswordDto setpassword, HttpServletRequest request) {
		System.out.println("Reset Password");
		LoginRegistrationStatus status = serviceImplementation.resetPassword(token, setpassword, request);
		return status;
	}

	/***************************** Verify User *******************************/

	@RequestMapping(value = "/verify/{token}", method = RequestMethod.GET)
	public LoginRegistrationStatus verifyUser(@PathVariable("token") String token, HttpServletRequest request) {
		System.out.println(token);
		LoginRegistrationStatus status = serviceImplementation.verifyUser(token, request);
		return status;
	}

}