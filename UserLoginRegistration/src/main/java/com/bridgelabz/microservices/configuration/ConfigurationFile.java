package com.bridgelabz.microservices.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConfigurationFile {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper ModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
