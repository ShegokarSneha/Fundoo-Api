package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.accesstoken.AccessToken;
import com.bridgelabz.Fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.Fundoo.dto.LoginDto;
import com.bridgelabz.Fundoo.dto.MailDto;
import com.bridgelabz.Fundoo.dto.RegisterDto;
import com.bridgelabz.Fundoo.dto.ResetPasswordDto;
import com.bridgelabz.Fundoo.exceptionhandling.NotFoundException;
import com.bridgelabz.Fundoo.model.User;
import com.bridgelabz.Fundoo.rabbitmq.QueueProducer;
import com.bridgelabz.Fundoo.repository.UserRepository;
import com.bridgelabz.Fundoo.result.ResponseCode;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@Service("ServiceInterface")
public class UserServiceImplementation implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AccessToken accesstoken;
	@Autowired
	private ResponseCode responseCode;
	@Autowired
	private QueueProducer queueProducer;

	private ResponseStatus response;

	private MailDto maildto = new MailDto();

	// ========================= Registering User ============================//

	public ResponseStatus registration(RegisterDto register) {
		boolean alreadyUser = userRepository.findByEmail(register.getEmail()).isPresent();
		if (alreadyUser) {

			// throw new UserAlreadyExistsException();
			response = responseCode.getResponse(200, "User Already Exist...", register);
			System.out.println("\nUser Already Registered");

		} else {

			// Successful Registration

			User user = modelMapper.map(register, User.class);

			// Encoding Password

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// Generating access Token

			userRepository.save(user);
			user.setToken(accesstoken.generateAccessToken(user.getUserid()));
			user.setDate(LocalDateTime.now());
			userRepository.save(user);

			// Registration Status

			String url = "http://localhost:8080/user/verify/" + user.getToken();

			String text = "Hello " + user.getFirstname() + "\n" + "You have registered Successfully."
					+ " To activate your account please click on the activation link : " + url;

			maildto.setEmail(user.getEmail());
			maildto.setSubject("Verification Mail");
			maildto.setBody(text);

			try {
				queueProducer.produce(maildto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response = responseCode.getResponse(201, "User Registered Successfully...", user);
			System.out.println("\nUser Registered Successfully...");
		}
		return response;

	}

	// ======================== Verify User ======================//

	public ResponseStatus verifyUser(String token) {
		System.out.println(token);
		String userid = accesstoken.verifyAccessToken(token);
		System.out.println("userid"+userid);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);
		alreadyuser.orElseThrow(()-> new NotFoundException("User Does Not Exist"));

//		if (alreadyuser.isEmpty()) {
//			System.out.println("User Not Found");
//			throw new UserNotFoundException();
//		} else {
			User verifieduser = alreadyuser.get();
			if (verifieduser.isVerfied() == false) {

				verifieduser.setVerfied(true);
				userRepository.save(verifieduser);
				response = responseCode.getResponse(200, "User verified Successfully...", verifieduser);
				System.out.println("User Verfied");
			} else {
				response = responseCode.getResponse(200, "User verified already...", verifieduser);
				System.out.println("User Verfied already");
			}
		
		return response;
	}

	// ===================== Logging User =====================//

	public ResponseStatus login(LoginDto login) {
		String password = login.getPassword();
		Optional<User> alreadyuser = userRepository.findByEmail(login.getEmail());
		alreadyuser.orElseThrow(()-> new NotFoundException("User Does Not Exist"));

//		if (alreadyuser.isEmpty()) {
//			System.out.println("User Not Found");
//			throw new UserNotFoundException();
//
//		} else {

			if (alreadyuser.get().isVerfied() == true) {
				if (passwordEncoder.matches(password, alreadyuser.get().getPassword())) {

					// LOGIN SUCCESSFULLY

					response = responseCode.getResponse(200, "User Login Successfully...", login);
					System.out.println("\nUser Login Successfully...");
				} else {

					// INAVLID PASSWORD

					response = responseCode.getResponse(401, "Invalid Password", login);
					System.out.println("\nInvalid Password");
				}
			} else {
				// Email Not verified
				response = responseCode.getResponse(204, "Email Not verified", alreadyuser.get());
				System.out.println("User Not Verified...");
			}
		
		return response;
	}

	// ====================== Forgot Password ======================//

	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto) {
		Optional<User> alreadyuser = userRepository.findByEmail(forgetdto.getEmail());
		alreadyuser.orElseThrow(()-> new NotFoundException("User Does Not Exist"));

		
//		if (alreadyuser.isEmpty()) {
//			System.out.println("User Not Found");
//			throw new UserNotFoundException();
//
//		} else {

			String url = "http://localhost:8080/user/resetpassword/" + alreadyuser.get().getToken();

			String text = "Hello " + alreadyuser.get().getFirstname() + "\n" + "You  requested to reset your Password."
					+ " To reset your password please click on the reset password link : " + url;

			maildto.setEmail(alreadyuser.get().getEmail());
			maildto.setSubject("Reset Password Link");
			maildto.setBody(text);

			try {
				queueProducer.produce(maildto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = responseCode.getResponse(200,
					"Request to reset password received." + "Check your inbox for the reset link.", forgetdto);
			System.out.println("Request to reset password received." + "\nCheck your inbox for the reset link.");
		
		return response;
	}

	// ======================== Reset Password ===========================//

	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto) {
		System.out.println(token);
		String userid = accesstoken.verifyAccessToken(token);
//		String userid = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);
		alreadyuser.orElseThrow(()-> new NotFoundException("User Does Not Exist"));

//		if (alreadyuser.isEmpty()) {
//			System.out.println("User Not Found");
//			throw new UserNotFoundException();
//		} else {
			User resetuser = alreadyuser.get();
			System.out.println(setpasswordDto.getPassword());
			resetuser.setPassword(passwordEncoder.encode(setpasswordDto.getPassword()));
			userRepository.save(resetuser);

			response = responseCode.getResponse(200, "You have successfully reset your password. You may now login.",
					resetuser);
			System.out.println("Password Reset Successfully...!");
	
		return response;
	}

	public ResponseStatus getAllUsers() {
		List<User> userlist = userRepository.findAll();
		response = responseCode.getResponse(200, "User List", userlist);
		System.out.println("All User get Successfully");
		return response;
	}

}
