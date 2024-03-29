package com.bridgelabz.microservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.microservices.dto.ForgetPasswordDto;
import com.bridgelabz.microservices.dto.LoginDto;
import com.bridgelabz.microservices.dto.RegisterDto;
import com.bridgelabz.microservices.dto.ResetPasswordDto;
import com.bridgelabz.microservices.model.User;
import com.bridgelabz.microservices.response.ResponseCode;
import com.bridgelabz.microservices.response.ResponseStatus;
import com.bridgelabz.microservices.service.UserServiceInterface;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserServiceInterface iUserService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ResponseCode responseCode;
	
	private ResponseStatus response;

	/***************** Registration Controller ******************/

	@PostMapping(value = "/register")
	public ResponseEntity<ResponseStatus> registerUser(@RequestBody RegisterDto register) {
		System.out.println("In Register User");
		User user = iUserService.registration(register);
		response = responseCode.getResponse(201, "User Registered Successfully...", user);
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
	public ResponseEntity<ResponseStatus> getAllUsers() {
		System.out.println("Get all Users");
		List<User> userlist = iUserService.getAllUsers();
		response = responseCode.getResponse(200, "User List", userlist);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Set Profile Picture ***************************/

	@PostMapping(value = "/uploadpic")
	public ResponseEntity<ResponseStatus> uploadProfilePic(@RequestParam(value = "imagefile") MultipartFile imagefile,
			@RequestHeader String token) {
		System.out.println("Upload Profile Picture");
		ResponseStatus response = iUserService.uploadProfilePic(imagefile, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/*********************** Get Profile Picture **********************/
	
	@GetMapping(value = "/getpic")
	public ResponseEntity<ResponseStatus> getProfilePic(@RequestHeader String token){
		System.out.println("Get Profile Picture");
		ResponseStatus response = iUserService.getProfilePic(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/******************* Get User Notes List ********************/
	
	@GetMapping(value = "/getnotes")
	public ResponseEntity<ResponseStatus> getNotes(@RequestHeader String token){
		System.out.println("In Get All Note of User By Rest Template");
		ResponseStatus response = restTemplate.getForObject("http://localhost:8081/note/usernotes/"+token, ResponseStatus.class);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/***************************** Get User ***************************/

	@GetMapping(value = "/getuser/{token}")
	public ResponseEntity<ResponseStatus> getUser(@PathVariable("token") String token) {
		System.out.println("Get User");
		ResponseStatus response = iUserService.getUser(token);
		response = responseCode.getResponse(200, "User get Successfully", response);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
