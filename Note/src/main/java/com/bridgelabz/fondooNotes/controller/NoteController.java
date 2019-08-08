package com.bridgelabz.fondooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.response.ResponseStatus;
import com.bridgelabz.fondooNotes.services.NoteServiceInterface;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteServiceInterface iNoteServiceInterface;

	/********************* Create Note **********************/
	@PostMapping(value = "/create")
	public ResponseEntity<ResponseStatus> createNote(@RequestBody NoteDto notedto, @RequestHeader String token) {
		System.out.println("In Create Note");
		ResponseStatus response = iNoteServiceInterface.createNote(notedto, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.CREATED);
	}

	/******************** Update Note ***********************/
	@PutMapping(value = "/update")
	public ResponseEntity<ResponseStatus> updateNote(@RequestBody NoteDto notedto, @RequestHeader String token,
			@RequestHeader String noteid) {
		System.out.println("In Update Note");
		ResponseStatus response = iNoteServiceInterface.updateNote(notedto, token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Delete Note ***********************/
	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponseStatus> deleteNote(@RequestHeader String token, @RequestHeader String noteid) {
		System.out.println("In Delete Note");
		ResponseStatus response = iNoteServiceInterface.deleteNote(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Archive Note ***********************/
	@GetMapping(value = "/archive")
	public ResponseEntity<ResponseStatus> archiveNote(@RequestHeader String token, @RequestHeader String noteid) {
		System.out.println("In Archive Note");
		ResponseStatus response = iNoteServiceInterface.archiveNote(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Pinned Note ***********************/
	@GetMapping(value = "/pinned")
	public ResponseStatus pinnedNote(@RequestHeader String token, @RequestHeader String noteid) {
		System.out.println("In Pinned Note");
		ResponseStatus response = iNoteServiceInterface.pinnedNote(token, noteid);
		return response;
	}

	/******************** Trash Note ***********************/
	@GetMapping(value = "/trash")
	public ResponseEntity<ResponseStatus> trashNote(@RequestHeader String token, @RequestHeader String noteid) {
		System.out.println("In Trash Note");
		ResponseStatus response = iNoteServiceInterface.trashNote(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************* Note List ****************************/
	@GetMapping(value = "/getall")
	public ResponseEntity<ResponseStatus> getAll() {
		System.out.println("In Get All Notes");
		ResponseStatus response = iNoteServiceInterface.getAllNotes();
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/***************** Change Color Of Note ****************/

	@PutMapping(value = "/color")
	public ResponseEntity<ResponseStatus> updateColor(@RequestHeader String token, @RequestHeader String noteid,
			@RequestHeader String color) {
		System.out.println("In Update Color of Note");
		ResponseStatus response = iNoteServiceInterface.updateColor(token, noteid, color);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/*************** Get User Note List *****************/
	
	@GetMapping(value = "/usernotes")
	public ResponseEntity<ResponseStatus> getUserNotes(@RequestHeader String token) {
		System.out.println("In Get User Notes");
		ResponseStatus response = iNoteServiceInterface.getUserNotes(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************* Set Reminder *********************/

	@PutMapping(value = "/setreminder")
	public ResponseEntity<ResponseStatus> setReminder(@RequestHeader String noteid, @RequestHeader String token,
			@RequestHeader String reminder) {
		System.out.println("In Setting Reminder");
		ResponseStatus response = iNoteServiceInterface.setReminder(noteid, token, reminder);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/****************** Delete Remainder *********************/
	@DeleteMapping(value = "/deletereminder")
	public ResponseEntity<ResponseStatus> deleteReminder(@RequestHeader String noteid, @RequestHeader String token) {
		System.out.println("In Deleting Reminder");
		ResponseStatus response = iNoteServiceInterface.deleteReminder(noteid, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);

	}

}
