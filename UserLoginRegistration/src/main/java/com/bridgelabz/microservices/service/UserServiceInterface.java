package com.bridgelabz.microservices.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.microservices.dto.ForgetPasswordDto;
import com.bridgelabz.microservices.dto.LoginDto;
import com.bridgelabz.microservices.dto.RegisterDto;
import com.bridgelabz.microservices.dto.ResetPasswordDto;
import com.bridgelabz.microservices.response.ResponseStatus;

public interface UserServiceInterface {

	public ResponseStatus registration(RegisterDto register);

	public ResponseStatus verifyUser(String token);

	public ResponseStatus login(LoginDto login);

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto);

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto);
	
	public ResponseStatus getAllUsers();
	
	public ResponseStatus uploadProfilePic(MultipartFile imagefile, String token);

}
