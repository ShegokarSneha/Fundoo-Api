package com.bridgelabz.Fundoo.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

public interface NoteServiceInterface {

	public ResponseStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token") String token);

	public ResponseStatus updateNote(@RequestBody NoteDto noteDto, @PathVariable("token") String token,
			@PathVariable("noteId")String noteId);
	
	public ResponseStatus deleteNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId);
	
	public ResponseStatus archiveNote(@PathVariable("token")String token, @PathVariable("noteId")String noteid);
	
	public ResponseStatus pinnedNote(@PathVariable("token")String token, @PathVariable("noteId")String noteId);
	
	public ResponseStatus trashNote(@PathVariable("token")String token, @PathVariable("noteId")String noteId);
	
	public ResponseStatus getAll(String token);
	
}
