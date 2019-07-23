package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.accesstoken.AccessToken;
import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.mailsender.ConfirmationMailSender;
import com.bridgelabz.Fundoo.model.User;
import com.bridgelabz.Fundoo.repository.LoginRegistrationRepository;
import com.bridgelabz.Fundoo.result.LoginRegistrationStatus;

@Service
public class ServiceImplementation implements ServiceInterface {

	@Autowired
	private LoginRegistrationRepository repository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AccessToken accessToken;
	@Autowired
	private ConfirmationMailSender confirmationMailSender;

	LoginRegistrationStatus status = new LoginRegistrationStatus();

	// ========================= Registering User ============================//

	public LoginRegistrationStatus Registration(RegisterDto register, HttpServletRequest request) {
		boolean alreadyUser = repository.findByEmail(register.getEmail()).isPresent();
		if (alreadyUser) {

			// Already Registered

			status.setEmail(register.getEmail());
			status.setStatus("User Already Registered...");
			System.out.println("\nUser Already Registered");

		} else {

			// Successful Registration

			User user = modelMapper.map(register, User.class);

			// Encoding Password

			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			// Generating access Token

			repository.save(user);
			user.setToken(accessToken.generateAccessToken(user.getUserId()));
			user.setDate(LocalDateTime.now());
			repository.save(user);

			// Registration Status

			status.setEmail(user.getEmail());
			status.setStatus("Registered Successfully...");
			System.out.println("\nUser Registered Successfully...");
			registrationActivationMail(user, request);

		}
		return status;
	}

	// ======================= Sending Activation Mail ===========================//

	private void registrationActivationMail(User user, HttpServletRequest request) {
		String token = user.getToken();
		StringBuffer requestUrl = request.getRequestURL();
		// System.out.println(requestUrl);
		String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/verify/" + token;
		// System.out.println(url);
		String subject = "Verification Mail";
		String text = "Hello " + user.getFirstname() + "\n" + "You have registered Successfully."
				+ " To activate your account please click on the activation link : " + url;
		confirmationMailSender.sendEmail(user.getEmail(), subject, text);

	}

	// ======================== Verify User ======================//

	public LoginRegistrationStatus verifyUser(String token, HttpServletRequest request) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = repository.findByUserId(userId);
		if (!alreadyuser.isEmpty()) {
			User verifieduser = alreadyuser.get();
			verifieduser.setVerfied(true);
			repository.save(verifieduser);
			status.setEmail(verifieduser.getEmail());
			status.setStatus("User Verified Successfully.");
			System.out.println("User Verfied");
		} else {
			status.setEmail("Not Present");
			status.setStatus("User Not Registerted");
			System.out.println("User Not Registerted");
		}
		return status;
	}

	// ===================== Logging User =====================//

	public LoginRegistrationStatus Login(LoginDto login, HttpServletRequest request) {
		String password = login.getPassword();
		Optional<User> alreadyuser = repository.findByEmail(login.getEmail());

		if (!alreadyuser.isEmpty()) {
			if (alreadyuser.get().isVerfied() == true) {
				if (bCryptPasswordEncoder.matches(password, alreadyuser.get().getPassword())) {

					// LOGIN SUCCESSFULLY
					status.setEmail(login.getEmail());
					status.setStatus("User Login Successfully...");
					System.out.println("\nUser Login Successfully...");
				} else {

					// INAVLID PASSWORD

					status.setEmail(login.getEmail());
					status.setStatus("Invalid Password");
					System.out.println("\nInvalid Password");
				}
			} else {
				// Email Not verified
				status.setEmail(login.getEmail());
				status.setStatus("Email Not verified");
				System.out.println("User Not Verified...");
			}
		} else {

			// EMAIL NOT EXIST

			status.setEmail(login.getEmail());
			status.setStatus("The email provided does not exist!");
			System.out.println("The email provided does not exist!");
		}
		return status;
	}

	// ====================== Forgot Password ======================//

	public LoginRegistrationStatus forgetPassword(ForgetPasswordDto forgetdto, HttpServletRequest request) {
		Optional<User> alreadyuser = repository.findByEmail(forgetdto.getEmail());
		if (!alreadyuser.isEmpty()) {
			String token = alreadyuser.get().getToken();
			StringBuffer requestUrl = request.getRequestURL();
			String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/resetpassword/" + token;
			String subject = "Reset Password";
			String text = "Hello " + alreadyuser.get().getFirstname() + "\n" + "You  requested to reset your Password."
					+ " To reset your password please click on the reset password link : " + url;
			confirmationMailSender.sendEmail(alreadyuser.get().getEmail(), subject, text);
			status.setEmail(forgetdto.getEmail());
			status.setStatus("Request to reset password received. Check your inbox for the reset link.");
			System.out.println("Request to reset password received. Check your inbox for the reset link.");
		} else {
			status.setEmail(forgetdto.getEmail());
			status.setStatus("We didn't find an account with entered E-mail Address.");
			System.out.println("We didn't find an account with entered E-mail Address.");
		}
		return status;
	}

	// ======================== Reset Password ===========================//

	public LoginRegistrationStatus resetPassword(String token, ResetPasswordDto setpasswordDto,
			HttpServletRequest request) {
		String id = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = repository.findByUserId(id);

		User resetuser = alreadyuser.get();
		if (!alreadyuser.isEmpty()) {
			System.out.println(setpasswordDto.getPassword());
			resetuser.setPassword(bCryptPasswordEncoder.encode(setpasswordDto.getPassword()));
			repository.save(resetuser);
			status.setEmail(resetuser.getEmail());
			status.setStatus("You have successfully reset your password. You may now login.");
			System.out.println("Password Reset Successfully...!");
		} else {
			status.setEmail(resetuser.getEmail());
			status.setStatus("The Token is invalid or broken....!");
			System.out.println("Invalid Token");
		}
		return status;
	}

}
