package com.bridgelabz.fondooNotes.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fondooNotes.model.Label;
import com.bridgelabz.fondooNotes.model.Note;

@Repository
public interface LabelRepository extends MongoRepository<Label, String>{
	
	public Optional<Label> findByLabelidAndUserid(String labelid,String userid);
	
	public Optional<Label> findByUseridAndLabelname(String userid , String labelname);
	
	public List<Label> findAllByUserid(String userid);
	
	public List<Note> findAllNotesByLabelid(String labelid);
	
	public List<Label> findAll();

}
