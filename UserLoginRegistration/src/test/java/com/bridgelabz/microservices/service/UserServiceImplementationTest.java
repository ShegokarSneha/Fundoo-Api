package com.bridgelabz.microservices.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.microservices.dto.LoginDto;
import com.bridgelabz.microservices.dto.RegisterDto;
import com.bridgelabz.microservices.exceptions.AlreadyExistsException;
import com.bridgelabz.microservices.exceptions.NotFoundException;
import com.bridgelabz.microservices.model.User;
import com.bridgelabz.microservices.repository.UserRepository;

@RunWith(SpringRunner.class)
class UserServiceImplementationTest {

	@Mock
	UserRepository userRepository;

	@Mock
	ModelMapper modelMapper;

	@Mock
	BCryptPasswordEncoder passwordEncoder;

	User user = new User("Sneha", "Shegokar", "sneha", "sneha@gmail.com", "sneha123");

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRegistration() {
		RegisterDto register = new RegisterDto("Sneha", "Shegokar", "sneha", "sneha@gmail.com", "sneha123");
		Optional<User> already = Optional.of(user);
		when(userRepository.findByEmail(anyString()).isPresent()).thenThrow(new AlreadyExistsException());
		when(modelMapper.map(register, User.class)).thenReturn(user);
		when(passwordEncoder.encode(register.getPassword())).thenReturn(user.getPassword());
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(register.getFirstname(), already.get().getFirstname());
	}

	@Test
	void testLogin() {
		LoginDto login = new LoginDto("sneha@gmail.com", "sneha123");
		Optional<User> already = Optional.of(user);
		when(userRepository.findByEmail(anyString()).isEmpty()).thenThrow(new NotFoundException());
		user.setVerfied(true);
		when(passwordEncoder.matches(login.getPassword(), user.getPassword())).thenReturn(true);
	}

	@Test
	void testGetAllUsers() {
		User user1 = new User("smita", "Shegokar", "smita", "smita@gmail.com", "smita123");
		List<User> userlist = new ArrayList<>();
		userlist.add(user);
		userlist.add(user1);
		when(userRepository.findAll()).thenReturn(userlist);
		assertNotNull(userlist);
	}

	@Test
	void testGetUser() {
		User user = new User("Sneha", "Shegokar", "sneha", "sneha@gmail.com", "sneha123");
		Optional<User> already = Optional.of(user);
		when(userRepository.findByUserid(anyString()).isEmpty()).thenThrow(new NotFoundException());
		assertNotNull(already.get());
		assertEquals(user.getFirstname(), already.get().getFirstname());
	}

}
