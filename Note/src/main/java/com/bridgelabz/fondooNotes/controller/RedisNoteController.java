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

import com.bridgelabz.fondooNotes.dto.CollaboratorDto;
import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.response.ResponseStatus;
import com.bridgelabz.fondooNotes.services.RedisNoteServiceInterface;

@RestController
@RequestMapping("/noteredis")
public class RedisNoteController {

	@Autowired
	private RedisNoteServiceInterface iNoteServiceInterface;

	/********************* Create Note **********************/
	@PostMapping(value = "/create")
	public ResponseEntity<ResponseStatus> createNote(@RequestBody NoteDto notedto, @RequestHeader String emailid) {
		System.out.println("In Create Note");
		ResponseStatus response = iNoteServiceInterface.createNote(notedto, emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.CREATED);
	}

	/******************** Update Note ***********************/
	@PutMapping(value = "/update")
	public ResponseEntity<ResponseStatus> updateNote(@RequestBody NoteDto notedto, @RequestHeader String emailid,
			@RequestHeader String noteid) {
		System.out.println("In Update Note");
		ResponseStatus response = iNoteServiceInterface.updateNote(notedto, emailid, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Delete Note ***********************/
	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponseStatus> deleteNote(@RequestHeader String emailid, @RequestHeader String noteid) {
		System.out.println("In Delete Note");
		ResponseStatus response = iNoteServiceInterface.deleteNote(emailid, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Archive Note ***********************/
	@GetMapping(value = "/archive")
	public ResponseEntity<ResponseStatus> archiveNote(@RequestHeader String emailid, @RequestHeader String noteid) {
		System.out.println("In Archive Note");
		ResponseStatus response = iNoteServiceInterface.archiveNote(emailid, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************** Pinned Note ***********************/
	@GetMapping(value = "/pinned")
	public ResponseStatus pinnedNote(@RequestHeader String emailid, @RequestHeader String noteid) {
		System.out.println("In Pinned Note");
		ResponseStatus response = iNoteServiceInterface.pinnedNote(emailid, noteid);
		return response;
	}

	/******************** Trash Note ***********************/
	@GetMapping(value = "/trash")
	public ResponseEntity<ResponseStatus> trashNote(@RequestHeader String emailid, @RequestHeader String noteid) {
		System.out.println("In Trash Note redid");
		ResponseStatus response = iNoteServiceInterface.trashNote(emailid, noteid);
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
	public ResponseEntity<ResponseStatus> updateColor(@RequestHeader String emailid, @RequestHeader String noteid,
			@RequestHeader String color) {
		System.out.println("In Update Color of Note");
		ResponseStatus response = iNoteServiceInterface.updateColor(emailid, noteid, color);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/*************** Get User Note List *****************/

	@GetMapping(value = "/usernotes")
	public ResponseEntity<ResponseStatus> getUserNotes(@RequestHeader String emailid) {
		System.out.println("In Get User Notes");
		ResponseStatus response = iNoteServiceInterface.getUserNotes(emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/******************* Set Reminder *********************/

	@PutMapping(value = "/setreminder")
	public ResponseEntity<ResponseStatus> setReminder(@RequestHeader String noteid, @RequestHeader String emailid,
			@RequestHeader String reminder) {
		System.out.println("In Setting Reminder");
		ResponseStatus response = iNoteServiceInterface.setReminder(noteid, emailid, reminder);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/****************** Delete Remainder *********************/

	@DeleteMapping(value = "/deletereminder")
	public ResponseEntity<ResponseStatus> deleteReminder(@RequestHeader String noteid, @RequestHeader String emailid) {
		System.out.println("In Deleting Reminder");
		ResponseStatus response = iNoteServiceInterface.deleteReminder(noteid, emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/****************** Add Collaborator List *********************/

	@PutMapping(value = "/addcollaborator")
	public ResponseEntity<ResponseStatus> addCollaborator(@RequestHeader String noteid, @RequestHeader String emailid,
			@RequestBody CollaboratorDto collaborator) {
		System.out.println("In Adding Collaborators");
		ResponseStatus response = iNoteServiceInterface.collaborateUsers(noteid, emailid, collaborator);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************* Get Note List Sorted In Ascending Order ************/

	@GetMapping(value = "/sorttitleascending")
	public ResponseEntity<ResponseStatus> sortByTitleAscending(@RequestHeader String emailid) {
		System.out.println("In Sorting Note List By Title In Ascending Order");
		ResponseStatus response = iNoteServiceInterface.sortByTitleAscendingOrder(emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************* Get Note List Sorted In Descending Order ************/

	@GetMapping(value = "/sorttitledescending")
	public ResponseEntity<ResponseStatus> sortByTitleDescending(@RequestHeader String emailid) {
		System.out.println("In Sorting Note List By Title In Descending Order");
		ResponseStatus response = iNoteServiceInterface.sortByTitleDescendingOrder(emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************* Get Note List Sorted In Ascending Order ************/

	@GetMapping(value = "/sortdateascending")
	public ResponseEntity<ResponseStatus> sortByDateAscending(@RequestHeader String emailid) {
		System.out.println("In Sorting Note List By Date In Ascending Order");
		ResponseStatus response = iNoteServiceInterface.sortByDateAscendingOrder(emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************* Get Note List Sorted In Descending Order ************/

	@GetMapping(value = "/sortdatedescending")
	public ResponseEntity<ResponseStatus> sortByDateDescending(@RequestHeader String emailid) {
		System.out.println("In Sorting Note List By Date In Descending Order");
		ResponseStatus response = iNoteServiceInterface.sortByDateDescendingOrder(emailid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
