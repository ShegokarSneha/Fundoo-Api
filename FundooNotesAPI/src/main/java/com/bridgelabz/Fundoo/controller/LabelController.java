package com.bridgelabz.Fundoo.controller;

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

import com.bridgelabz.Fundoo.dto.LabelDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;
import com.bridgelabz.Fundoo.services.LabelServiceInterface;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

	@Autowired
	LabelServiceInterface iLabelService;

	/******************** Label Creation **********************/

	@PostMapping(value = "/create/{token}")
	public ResponseEntity<ResponseStatus> createLabel(@RequestBody LabelDto labeldto,
			@PathVariable("token") String token) {
		ResponseStatus response = iLabelService.createLabel(labeldto, token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.CREATED);
	}

	/***************** Update Label ******************/

	@PostMapping(value = "/update/{token}/{labelid}")
	public ResponseEntity<ResponseStatus> updateLabel(@RequestBody LabelDto labeldto,
			@PathVariable("token") String token, @PathVariable("labelid") String labelid) {
		ResponseStatus response = iLabelService.updateLabel(labeldto, token, labelid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

	/**************** Delete Note *******************/

	@DeleteMapping(value = "/delete/{token}/{labelid}")
	public ResponseEntity<ResponseStatus> deleteLabel(@PathVariable("token") String token,
			@PathVariable("labelid") String labelid) {
		ResponseStatus response = iLabelService.deleteLabel(token, labelid);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	/************** Get all Labels *******************/
	
	@GetMapping(value = "/getall/{token}")
	public ResponseEntity<ResponseStatus> getAllLabel(@PathVariable("token")String token){
		ResponseStatus response = iLabelService.getAlllabels(token);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
