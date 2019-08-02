package com.bridgelabz.Fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;
import com.bridgelabz.Fundoo.services.UserServiceInterface;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserServiceInterface iUserService;

	/***************** Registration Controller ******************/

	@PostMapping(value = "/register")
	public ResponseEntity<ResponseStatus> registerUser(@RequestBody RegisterDto register){
		System.out.println("In Register User");
		ResponseStatus response = iUserService.registration(register);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************************ Login Controller *******************/

	@GetMapping(value = "/login")
	public ResponseEntity<ResponseStatus> loginUser(@RequestBody LoginDto login) {
		System.out.println("In Logging User");
		ResponseStatus response = iUserService.login(login);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/*********************** Forget Password Controller ********************/

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<ResponseStatus> forgetPassword(@RequestBody ForgetPasswordDto forgetdto) {
		System.out.println("Forget Password");
		ResponseStatus response = iUserService.forgetPassword(forgetdto);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/********************** Reset Password Controller **************************/

	@PostMapping(value = "/resetpassword/{token}")
	public ResponseEntity<ResponseStatus> resetPassword(@PathVariable("token") String token,
			@RequestBody ResetPasswordDto setpassword) {
		System.out.println("Reset Password");
		ResponseStatus response = iUserService.resetPassword(token, setpassword);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/***************************** Verify User ***************************/

	@GetMapping(value = "/verify/{token}")
	public ResponseEntity<ResponseStatus> verifyUser(@PathVariable("token") String token) {
		System.out.println("Verify User");
		ResponseStatus response = iUserService.verifyUser(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/***************************** Get All Users ***************************/
	
	@GetMapping(value = "/getall")
	public ResponseEntity<ResponseStatus> getAllUsers(){
		System.out.println("Get all Users");
		ResponseStatus response = iUserService.getAllUsers();
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
}
