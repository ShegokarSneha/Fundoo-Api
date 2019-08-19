package com.bridgelabz.fondooNotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationFile {

	@Bean
	public ModelMapper ModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
