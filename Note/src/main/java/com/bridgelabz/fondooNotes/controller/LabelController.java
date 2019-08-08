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

import com.bridgelabz.fondooNotes.dto.LabelDto;
import com.bridgelabz.fondooNotes.response.ResponseStatus;
import com.bridgelabz.fondooNotes.services.LabelServiceInterface;

@RestController
@RequestMapping(value = "/label")
public class LabelController {
	
	@Autowired
	LabelServiceInterface ilabelService;

	/******************** Label Creation **********************/

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseStatus> createLabel(@RequestBody LabelDto labeldto,
			@RequestHeader String token) {
		System.out.println("In Create Label");
		ResponseStatus response = ilabelService.createLabel(labeldto, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.CREATED);
	}

	/***************** Update Label ******************/

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseStatus> updateLabel(@RequestBody LabelDto labeldto,
			@RequestHeader String token, @RequestHeader String labelid) {
		System.out.println("In Update Label");
		ResponseStatus response = ilabelService.updateLabel(labeldto, token, labelid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/**************** Delete Note *******************/

	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponseStatus> deleteLabel(@RequestHeader String token,
			@RequestHeader String labelid) {
		ResponseStatus response = ilabelService.deleteLabel(token, labelid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/************** Get all Labels of Specific User *******************/

	@GetMapping(value = "/userlabels")
	public ResponseEntity<ResponseStatus> getAll(@RequestHeader String token) {
		System.out.println("Get List Of Labels Of Specific User");
		ResponseStatus response = ilabelService.getUserLabels(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/************** Get all Labels *******************/

	@GetMapping(value = "/getall")
	public ResponseEntity<ResponseStatus> getAllLabels() {
		System.out.println("Get All Labels");
		ResponseStatus response = ilabelService.getAll();
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/************** Add Label To Note ****************/
	
	@PutMapping(value = "/addlabeltonote")
	public ResponseEntity<ResponseStatus> addLabeltoNote(@RequestHeader String token,
			@RequestHeader String labelid,@RequestHeader String noteid){
		System.out.println("In Adding Label to Notes");
		ResponseStatus response = ilabelService.addLabeltoNote(token, labelid, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/************** Add Label To Note ****************/
			
	@PutMapping(value = "/removelabelfromnote")
	public ResponseEntity<ResponseStatus> removeLabelfromNote(@RequestHeader String token,
			@RequestHeader String labelid,@RequestHeader String noteid){
		System.out.println("In Remove Label From Notes");
		ResponseStatus response = ilabelService.removeLabelfromNote(token, labelid, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/**************** Get Label List Of Note ***************/
	
	@GetMapping(value = "/getlabelnote")
	public ResponseEntity<ResponseStatus> getLabelofNote(@RequestHeader String token, @RequestHeader String noteid){
		System.out.println("In Get Label From Notes");
		ResponseStatus response = ilabelService.getLabelsofNotes(token, noteid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
