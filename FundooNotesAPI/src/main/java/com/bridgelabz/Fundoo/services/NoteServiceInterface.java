package com.bridgelabz.Fundoo.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.NoteStatus;

public interface NoteServiceInterface {
	
	public NoteStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token") String token, HttpServletRequest request);
	public NoteStatus updateNote (@RequestBody NoteDto noteDto, @PathVariable("token") String token, HttpServletRequest request);

}
