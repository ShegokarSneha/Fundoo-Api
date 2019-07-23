package com.bridgelabz.Fundoo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.Fundoo.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
	public Optional<Note> findByUserId(String userId);

	public Optional<Note> findByUserIdAndNoteId(String userId, String noteId);

}