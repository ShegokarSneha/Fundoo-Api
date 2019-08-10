package com.bridgelabz.microservices.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.microservices.Utility.AccessToken;
import com.bridgelabz.microservices.dto.ForgetPasswordDto;
import com.bridgelabz.microservices.dto.LoginDto;
import com.bridgelabz.microservices.dto.MailDto;
import com.bridgelabz.microservices.dto.RegisterDto;
import com.bridgelabz.microservices.dto.ResetPasswordDto;
import com.bridgelabz.microservices.exceptions.NotFoundException;
import com.bridgelabz.microservices.model.User;
import com.bridgelabz.microservices.rabbitmq.QueueProducer;
import com.bridgelabz.microservices.repository.UserRepository;
import com.bridgelabz.microservices.response.ResponseCode;
import com.bridgelabz.microservices.response.ResponseStatus;

@Service("ServiceInterface")
public class UserServiceImplementation implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AccessToken accessToken;

	@Autowired
	private ResponseCode responseCode;

	@Autowired
	private QueueProducer queueProducer;

	private ResponseStatus response;

	private MailDto maildto = new MailDto();

	private Path path = Paths.get("/home/admin1/Downloads");

	// ========================= Registering User ============================//

	@Override
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
			user.setToken(accessToken.generateAccessToken(user.getUserid()));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = responseCode.getResponse(201, "User Registered Successfully...", register);
			System.out.println("\nUser Registered Successfully...");
		}
		return response;

	}

	// ======================== Verify User ======================//

	@Override
	public ResponseStatus verifyUser(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);
		alreadyuser.orElseThrow(() -> new NotFoundException());
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

	@Override
	public ResponseStatus login(LoginDto login) {
		String password = login.getPassword();
		Optional<User> alreadyuser = userRepository.findByEmail(login.getEmail());
		alreadyuser.orElseThrow(() -> new NotFoundException());

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

	@Override
	public ResponseStatus forgetPassword(ForgetPasswordDto forgetdto) {
		Optional<User> alreadyuser = userRepository.findByEmail(forgetdto.getEmail());
		alreadyuser.orElseThrow(() -> new NotFoundException());

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
				"Request to reset password received." + "\nCheck your inbox for the reset link.", forgetdto);
		System.out.println("Request to reset password received." + "\nCheck your inbox for the reset link.");
		return response;

	}

	// ======================== Reset Password ===========================//

	@Override
	public ResponseStatus resetPassword(String token, ResetPasswordDto setpasswordDto) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);

		alreadyuser.orElseThrow(() -> new NotFoundException());
		User resetuser = alreadyuser.get();
		System.out.println(setpasswordDto.getPassword());
		resetuser.setPassword(passwordEncoder.encode(setpasswordDto.getPassword()));
		userRepository.save(resetuser);

		response = responseCode.getResponse(200, "You have successfully reset your password. You may now login.",
				setpasswordDto);
		System.out.println("Password Reset Successfully...!");

		return response;
	}

	// ================ Get All Users ==================//

	@Override
	public ResponseStatus getAllUsers() {
		List<User> userlist = userRepository.findAll();
		response = responseCode.getResponse(200, "User List", userlist);
		System.out.println("All User get Successfully");
		return response;
	}

	// ================ Upload Profile Picture ==================//

	@Override
	public ResponseStatus uploadProfilePic(MultipartFile imagefile, String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		already.orElseThrow(() -> new NotFoundException());
		UUID uuid = UUID.randomUUID();
		String fileid = uuid.toString();
		try {
			Files.copy(imagefile.getInputStream(), path.resolve(fileid), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		already.get().setProfilepic(fileid);
		userRepository.save(already.get());
		response = responseCode.getResponse(200, "Profile Picture set Successfully", fileid);
		System.out.println("Profile Picture set Successfully");
		return response;
	}

	// ================ Get Profile Picture ==================//

	@Override
	public ResponseStatus getProfilePic(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		already.orElseThrow(() -> new NotFoundException());
		String pic = already.get().getProfilepic();
		response = responseCode.getResponse(200, "Profile Picture get Successfully", pic);
		System.out.println("Profile Picture get Successfully");
		return response;
	}

}
