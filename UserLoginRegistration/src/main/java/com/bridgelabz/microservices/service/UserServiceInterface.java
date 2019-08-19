package com.bridgelabz.microservices.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.microservices.dto.ForgetPasswordDto;
import com.bridgelabz.microservices.dto.LoginDto;
import com.bridgelabz.microservices.dto.RegisterDto;
import com.bridgelabz.microservices.dto.ResetPasswordDto;
import com.bridgelabz.microservices.model.User;
import com.bridgelabz.microservices.response.ResponseStatus;

public interface UserServiceInterface {

	public User registration(RegisterDto register);

	public ResponseStatus verifyUser(String token);

	public ResponseStatus login(LoginDto login);

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto);

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto);
	
	public List<User> getAllUsers();
	
	public ResponseStatus uploadProfilePic(MultipartFile imagefile, String token);

	public ResponseStatus getProfilePic(String token);
	
	public User getUser(String token);

}
