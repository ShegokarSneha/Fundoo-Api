package com.bridgelabz.Fundoo.services;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

public interface UserServiceInterface {

	public ResponseStatus registration(RegisterDto register);

	public ResponseStatus verifyUser(String token);

	public ResponseStatus login(LoginDto login);

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto);

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto);
	
	public ResponseStatus getAllUsers();

}
