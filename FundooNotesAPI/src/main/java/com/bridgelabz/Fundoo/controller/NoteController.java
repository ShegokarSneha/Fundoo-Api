package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.NoteStatus;
import com.bridgelabz.Fundoo.services.NoteServiceImplementation;

@RestController
@RequestMapping("user/note")
public class NoteController {
	
	@Autowired
	private NoteServiceImplementation noteServiceImplementation;
	
	/********************* Create Note **********************/
	@RequestMapping(value = "/create/{token}", method = RequestMethod.POST)
	public NoteStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token")String token, HttpServletRequest request) {
		System.out.println("In Create Note");
		NoteStatus status = noteServiceImplementation.createNote(notedto, token, request);
		return status;
	}
	
	/******************** Update Note ***********************/
	@RequestMapping(value = "/update/{token}", method = RequestMethod.POST)
	public NoteStatus updateNote(@RequestBody NoteDto noteDto, @PathVariable("token")String token, HttpServletRequest request) {
		System.out.println("In Update Note");
		NoteStatus status = noteServiceImplementation.updateNote(noteDto, token, request);
		return status;
	}
	

}
