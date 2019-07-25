package com.bridgelabz.Fundoo.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

public interface NoteServiceInterface {

	public ResponseStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token") String token,
		 HttpServletRequest request);

	public ResponseStatus updateNote(@RequestBody NoteDto noteDto, @PathVariable("token") String token,
			@PathVariable("noteId")String noteId, HttpServletRequest request);
	
	public ResponseStatus deleteNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId,
			HttpServletRequest request);
	
	public ResponseStatus archiveNote(@PathVariable("token")String token, @PathVariable("noteId")String 
			noteid, HttpServletRequest request);
	
	public ResponseStatus pinnedNote(@PathVariable("token")String token, @PathVariable("noteId")String noteId,
			HttpServletRequest request);
}
