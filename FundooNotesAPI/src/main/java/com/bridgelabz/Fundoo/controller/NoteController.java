package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;
import com.bridgelabz.Fundoo.services.NoteServiceInterface;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteServiceInterface iNoteServiceInterface;

	/********************* Create Note **********************/
	@RequestMapping(value = "/create/{token}", method = RequestMethod.POST)
	public ResponseStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token") String token,
			HttpServletRequest request) {
		System.out.println("In Create Note");
		ResponseStatus response = iNoteServiceInterface.createNote(notedto, token, request);
		return response;
	}

	/******************** Update Note ***********************/
	@RequestMapping(value = "update/{token}/{noteId}", method = RequestMethod.POST)
	public ResponseStatus updateNote(@RequestBody NoteDto noteDto, @PathVariable("token") String token,
			@PathVariable("noteId") String noteId, HttpServletRequest request) {
		System.out.println("In Update Note");
		ResponseStatus response = iNoteServiceInterface.updateNote(noteDto, token, noteId, request);
		return response;
	}

	/******************** Delete Note ***********************/
	@RequestMapping(value = "delete/{token}/{noteId}", method = RequestMethod.GET)
	public ResponseStatus deleteNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId,
			HttpServletRequest request) {
		System.out.println("In Delete Note");
		ResponseStatus response = iNoteServiceInterface.deleteNote(token, noteId, request);
		return response;
	}
	
	/******************** Archive Note ***********************/
	@RequestMapping(value = "archive/{token}/{noteId}", method = RequestMethod.GET)
	public ResponseStatus archiveNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId,
			HttpServletRequest request) {
		System.out.println("In Archive Note");
		ResponseStatus response = iNoteServiceInterface.archiveNote(token, noteId, request);
		return response;
	}
	
	/******************** Pinned Note ***********************/
	@RequestMapping(value = "pinned/{token}/{noteId}", method = RequestMethod.GET)
	public ResponseStatus pinnedNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId,
			HttpServletRequest request) {
		System.out.println("In Pinned Note");
		ResponseStatus response = iNoteServiceInterface.pinnedNote(token, noteId, request);
		return response;
	}

}
