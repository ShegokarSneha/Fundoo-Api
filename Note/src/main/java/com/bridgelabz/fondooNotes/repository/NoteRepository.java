package com.bridgelabz.fondooNotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fondooNotes.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
	public Optional<Note> findByUserid(String userid);
	
	public Optional<Note> findByNoteid(String noteid);

	public Optional<Note> findByUseridAndNoteid(String userid, String noteid);
	
	public List<Note> findAll();
	
	public List<Note> findAllByUserid(String userid);

}
