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
import com.bridgelabz.Fundoo.result.ResponseCode;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@Service("ServiceInterface")
public class ServiceImplementation implements ServiceInterface {

	@Autowired
	private LoginRegistrationRepository registrationRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AccessToken accessToken;
	@Autowired
	private ConfirmationMailSender mailSender;
	@Autowired
	private ResponseCode responseCode;
	private ResponseStatus response;

	// ========================= Registering User ============================//

	public ResponseStatus Registration(RegisterDto register, HttpServletRequest request) {
		boolean alreadyUser = registrationRepository.findByEmail(register.getEmail()).isPresent();
		if (alreadyUser) {

			// Already Registered
			response = responseCode.getResponse(200, "User Already Exist...", register);
			System.out.println("\nUser Already Registered");

		} else {

			// Successful Registration

			User user = modelMapper.map(register, User.class);

			// Encoding Password

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// Generating access Token

			registrationRepository.save(user);
			user.setToken(accessToken.generateAccessToken(user.getUserId()));
			user.setDate(LocalDateTime.now());
			registrationRepository.save(user);

			// Registration Status

			response = responseCode.getResponse(201, "User Registered Successfully...", register);
			System.out.println("\nUser Registered Successfully...");
			registrationActivationMail(user, request);
		}
		return response;

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
		mailSender.sendEmail(user.getEmail(), subject, text);

	}

	// ======================== Verify User ======================//

	public ResponseStatus verifyUser(String token, HttpServletRequest request) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = registrationRepository.findByUserId(userId);
		if (alreadyuser.isEmpty()) {
			response = responseCode.getResponse(404, "Invalid Token...", token);
		} else {
			// if (!alreadyuser.isEmpty()) {
			User verifieduser = alreadyuser.get();
			if (verifieduser.isVerfied() == false) {

				verifieduser.setVerfied(true);
				registrationRepository.save(verifieduser);
				response = responseCode.getResponse(200, "User verified Successfully...", verifieduser);
				System.out.println("User Verfied");
			} else {
				response = responseCode.getResponse(200, "User verified already...", verifieduser);
				System.out.println("User Verfied already");
			}
		}
		return response;
	}

	// ===================== Logging User =====================//

	public ResponseStatus Login(LoginDto login, HttpServletRequest request) {
		String password = login.getPassword();
		Optional<User> alreadyuser = registrationRepository.findByEmail(login.getEmail());
		if (alreadyuser.isEmpty()) {
			response = responseCode.getResponse(404, "The email provided does not exist!", login);
			System.out.println("The email provided does not exist!");
		} else {

			if (alreadyuser.get().isVerfied() == true) {
				if (passwordEncoder.matches(password, alreadyuser.get().getPassword())) {

					// LOGIN SUCCESSFULLY

					response = responseCode.getResponse(200, "User Login Successfully...", login);
					System.out.println("\nUser Login Successfully...");
				} else {

					// INAVLID PASSWORD

					response = responseCode.getResponse(204, "Invalid Password", login);
					System.out.println("\nInvalid Password");
				}
			} else {
				// Email Not verified
				response = responseCode.getResponse(204, "Email Not verified", alreadyuser.get());
				System.out.println("User Not Verified...");
			}
		} /*
			 * else {
			 * 
			 * // EMAIL NOT EXIST
			 * 
			 * response = responseCode.getResponse(404,
			 * "The email provided does not exist!", login);
			 * System.out.println("The email provided does not exist!"); }
			 */
		return response;
	}

	// ====================== Forgot Password ======================//

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto, HttpServletRequest request) {
		Optional<User> alreadyuser = registrationRepository.findByEmail(forgetdto.getEmail());
		if (alreadyuser.isEmpty()) {
			response = responseCode.getResponse(204, "We didn't find an account with entered E-mail Address.",
					forgetdto);
			System.out.println("We didn't find an account with entered E-mail Address.");
		} else {
			// if (!alreadyuser.isEmpty()) {
			String token = alreadyuser.get().getToken();
			StringBuffer requestUrl = request.getRequestURL();
			String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/resetpassword/" + token;
			String subject = "Reset Password";
			String text = "Hello " + alreadyuser.get().getFirstname() + "\n" + "You  requested to reset your Password."
					+ " To reset your password please click on the reset password link : " + url;
			mailSender.sendEmail(alreadyuser.get().getEmail(), subject, text);

			response = responseCode.getResponse(200,
					"Request to reset password received." + "\nCheck your inbox for the reset link.", forgetdto);
			System.out.println("Request to reset password received." + "\nCheck your inbox for the reset link.");
		} /*
			 * else {
			 * 
			 * response = responseCode.getResponse(204,
			 * "We didn't find an account with entered E-mail Address.", forgetdto);
			 * System.out.println("We didn't find an account with entered E-mail Address.");
			 * }
			 */
		return response;
	}

	// ======================== Reset Password ===========================//

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto, HttpServletRequest request) {
		String id = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = registrationRepository.findByUserId(id);

		if (alreadyuser.isEmpty()) {
			response = responseCode.getResponse(404, "Invalid Token...", token);
		} else {
			User resetuser = alreadyuser.get();
			// if (!alreadyuser.isEmpty()) {
			System.out.println(setpasswordDto.getPassword());
			resetuser.setPassword(passwordEncoder.encode(setpasswordDto.getPassword()));
			registrationRepository.save(resetuser);

			response = responseCode.getResponse(200, "You have successfully reset your password. You may now login.",
					setpasswordDto);
			System.out.println("Password Reset Successfully...!");
		}
		return response;
	}

}
