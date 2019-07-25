package com.bridgelabz.Fundoo.services;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

public interface ServiceInterface {

	public ResponseStatus Registration(RegisterDto register, HttpServletRequest request);

	public ResponseStatus verifyUser(String token, HttpServletRequest request);

	public ResponseStatus Login(LoginDto login, HttpServletRequest request);

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto, HttpServletRequest request);

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto, HttpServletRequest request);

}
