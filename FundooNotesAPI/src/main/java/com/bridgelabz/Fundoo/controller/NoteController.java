package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@PostMapping(value = "/create/{token}")
	public ResponseStatus createNote(@RequestBody NoteDto notedto, @PathVariable("token") String token) {
		System.out.println("In Create Note");
		ResponseStatus response = iNoteServiceInterface.createNote(notedto, token);
		return response;
	}

	/******************** Update Note ***********************/
	@PostMapping(value = "update/{token}/{noteId}")
	public ResponseStatus updateNote(@RequestBody NoteDto noteDto, @PathVariable("token") String token,
			@PathVariable("noteId") String noteId) {
		System.out.println("In Update Note");
		ResponseStatus response = iNoteServiceInterface.updateNote(noteDto, token, noteId);
		return response;
	}

	/******************** Delete Note ***********************/
	@DeleteMapping(value = "delete/{token}/{noteId}")
	public ResponseStatus deleteNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId) {
		System.out.println("In Delete Note");
		ResponseStatus response = iNoteServiceInterface.deleteNote(token, noteId);
		return response;
	}

	/******************** Archive Note ***********************/
	@GetMapping(value = "archive/{token}/{noteId}")
	public ResponseStatus archiveNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId,
			HttpServletRequest request) {
		System.out.println("In Archive Note");
		ResponseStatus response = iNoteServiceInterface.archiveNote(token, noteId);
		return response;
	}

	/******************** Pinned Note ***********************/
	@GetMapping(value = "pinned/{token}/{noteId}")
	public ResponseStatus pinnedNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId) {
		System.out.println("In Pinned Note");
		ResponseStatus response = iNoteServiceInterface.pinnedNote(token, noteId);
		return response;
	}

	/******************** Trash Note ***********************/
	@GetMapping(value = "trash/{token}/{noteId}")
	public ResponseStatus trashNote(@PathVariable("token") String token, @PathVariable("noteId") String noteId) {
		System.out.println("In Trash Note");
		ResponseStatus response = iNoteServiceInterface.trashNote(token, noteId);
		return response;
	}
	
	/******************* Note List ****************************/
	@GetMapping(value = "/getall/{token}")
	public ResponseStatus getAll(@PathVariable("token")String token) {
		System.out.println("In Get All Notes");
		ResponseStatus response = iNoteServiceInterface.getAll(token);
		return response;
	}

}
