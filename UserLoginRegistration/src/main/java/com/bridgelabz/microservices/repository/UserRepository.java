package com.bridgelabz.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.microservices.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByEmail(String email);

	public Optional<User> findByUserid(String userid);
	
	public List<User> findAll();

}
