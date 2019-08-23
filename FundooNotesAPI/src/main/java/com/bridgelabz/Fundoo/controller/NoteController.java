package com.bridgelabz.Fundoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseStatus> createNote(@RequestBody NoteDto notedto,
			@PathVariable("token") String token) {
		System.out.println("In Create Note");
		ResponseStatus response = iNoteServiceInterface.createNote(notedto, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.CREATED);
	}

	/******************** Update Note ***********************/
	@PostMapping(value = "update/{token}/{noteid}")
	public ResponseEntity<ResponseStatus> updateNote(@RequestBody NoteDto notedto, @PathVariable("token") String token,
			@PathVariable("noteid") String noteid) {
		System.out.println("In Update Note");
		ResponseStatus response = iNoteServiceInterface.updateNote(notedto, token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Delete Note ***********************/
	@DeleteMapping(value = "delete/{token}/{noteid}")
	public ResponseEntity<ResponseStatus> deleteNote(@PathVariable("token") String token,
			@PathVariable("noteid") String noteid) {
		System.out.println("In Delete Note");
		ResponseStatus response = iNoteServiceInterface.deleteNote(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Archive Note ***********************/
	@GetMapping(value = "archive/{token}/{noteid}")
	public ResponseEntity<ResponseStatus> archiveNote(@PathVariable("token") String token,
			@PathVariable("noteid") String noteid, HttpServletRequest request) {
		System.out.println("In Archive Note");
		ResponseStatus response = iNoteServiceInterface.archiveNote(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Pinned Note ***********************/
	@GetMapping(value = "pinned/{token}/{noteid}")
	public ResponseStatus pinnedNote(@PathVariable("token") String token, @PathVariable("noteid") String noteid) {
		System.out.println("In Pinned Note");
		ResponseStatus response = iNoteServiceInterface.pinnedNote(token, noteid);
		return response;
	}

	/******************** Trash Note ***********************/
	@GetMapping(value = "trash/{token}/{noteid}")
	public ResponseEntity<ResponseStatus> trashNote(@PathVariable("token") String token,
			@PathVariable("noteId") String noteId) {
		System.out.println("In Trash Note");
		ResponseStatus response = iNoteServiceInterface.trashNote(token, noteId);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************* Note List ****************************/
	@GetMapping(value = "/getall/{token}")
	public ResponseEntity<ResponseStatus> getAll(@PathVariable("token") String token) {
		System.out.println("In Get All Notes");
		ResponseStatus response = iNoteServiceInterface.getAll(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
