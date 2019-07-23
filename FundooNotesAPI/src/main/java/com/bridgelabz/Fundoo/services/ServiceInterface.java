package com.bridgelabz.Fundoo.services;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.result.LoginRegistrationStatus;

public interface ServiceInterface {
	public LoginRegistrationStatus Registration(RegisterDto register, HttpServletRequest request);

	public LoginRegistrationStatus verifyUser(String token, HttpServletRequest request);

	public LoginRegistrationStatus Login(LoginDto login, HttpServletRequest request);

	public LoginRegistrationStatus forgetPassword(ForgetPasswordDto forgetdto, HttpServletRequest request);

	public LoginRegistrationStatus resetPassword(String token, ResetPasswordDto setpasswordDto, HttpServletRequest request);

}