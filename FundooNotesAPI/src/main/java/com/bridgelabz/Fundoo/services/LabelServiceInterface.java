package com.bridgelabz.Fundoo.services;

import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.dto.LabelDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@Service
public interface LabelServiceInterface {

	public ResponseStatus createLabel(LabelDto labeldto, String token);

	public ResponseStatus updateLabel(LabelDto labeldto, String token, String labelid);

	public ResponseStatus deleteLabel(String token, String labelid);

	public ResponseStatus getAlllabels(String token);

}
