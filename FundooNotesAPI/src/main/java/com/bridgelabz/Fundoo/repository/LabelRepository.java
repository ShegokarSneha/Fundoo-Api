package com.bridgelabz.Fundoo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.Fundoo.model.Label;

public interface LabelRepository extends MongoRepository<Label, String>{
	
	Optional<Label> findByLabelidAndUserid(String labelid,String userid);
	
	Optional<Label> findByLabelid(String labelid);
	
	Optional<Label> findByUserid(String userid);

}
