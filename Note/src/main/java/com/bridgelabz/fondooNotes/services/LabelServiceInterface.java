package com.bridgelabz.fondooNotes.services;

import org.springframework.stereotype.Service;

import com.bridgelabz.fondooNotes.dto.LabelDto;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

@Service
public interface LabelServiceInterface {

	public ResponseStatus createLabel(LabelDto labeldto, String token);

	public ResponseStatus updateLabel(LabelDto labeldto, String token, String labelid);

	public ResponseStatus deleteLabel(String token, String labelid);

	public ResponseStatus getUserLabels(String token);
	
	public ResponseStatus getAll();

	public ResponseStatus addLabeltoNote(String token, String labelid, String noteid);

	public ResponseStatus removeLabelfromNote(String token, String labelid, String noteid);

	public ResponseStatus getLabelsofNotes(String token, String noteid);

}
