package com.bridgelabz.fondooNotes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fondooNotes.accesstoken.AccessToken;
import com.bridgelabz.fondooNotes.dto.LabelDto;
import com.bridgelabz.fondooNotes.exception.AlreadyExistsException;
import com.bridgelabz.fondooNotes.exception.BlankException;
import com.bridgelabz.fondooNotes.exception.NotFoundException;
import com.bridgelabz.fondooNotes.model.Label;
import com.bridgelabz.fondooNotes.model.Note;
import com.bridgelabz.fondooNotes.repository.LabelRepository;
import com.bridgelabz.fondooNotes.repository.NoteRepository;
import com.bridgelabz.fondooNotes.response.ResponseCode;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

@Service("LabelServiceInterface")
public class LabelServiceImplementation implements LabelServiceInterface {

	@Autowired
	private AccessToken accessToken;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ResponseCode responseCode;

	private ResponseStatus response;

	// ================= Create Label ====================//

	@Override
	public ResponseStatus createLabel(LabelDto labeldto, String token) {
		String userid = accessToken.verifyAccessToken(token);
		System.out.println(userid);
		if (labeldto.getLabelname().isEmpty()) {
			throw new BlankException("Label Name Can Not Be Empty.");
		}
		boolean already = labelRepository.findByUseridAndLabelname(userid, labeldto.getLabelname()).isPresent();
		System.out.println(already);
		if (already) {
			throw new AlreadyExistsException("Label Name Already Exist.");

		} else {
			Label label = modelMapper.map(labeldto, Label.class);
			label.setUserid(userid);
			label.setCreated(LocalDateTime.now());
			label.setUpdated(LocalDateTime.now());
			labelRepository.save(label);
			response = responseCode.getResponse(201, "Label Created Successfully...!", label);
			System.out.println(label + "\nLabel Created Successfully...!");
		}
		return response;
	}

	// ================= Update Label ====================//

	@Override
	public ResponseStatus updateLabel(LabelDto labeldto, String token, String labelid) {
		String userid = accessToken.verifyAccessToken(token);

		if (labeldto.getLabelname().isEmpty()) {
			throw new BlankException("Label Name Can Not Be Empty.");
		}

		Optional<Label> already = labelRepository.findByLabelidAndUserid(labelid, userid);
		System.out.println(already);
		if (already.isEmpty()) {
			throw new NotFoundException("Label Not Found With Given Label Id.");
		}
		if (already.get().getLabelname().equalsIgnoreCase(labeldto.getLabelname())) {
			throw new AlreadyExistsException("Label Name Already Exist.");
		}
		already.get().setLabelname(labeldto.getLabelname());
		already.get().setUpdated(LocalDateTime.now());
		labelRepository.save(already.get());
		response = responseCode.getResponse(200, "Label Updated Successfully...!", already.get());
		System.out.println(already.get() + "\nLabel Updated Successfully...!");
		return response;
	}

	// ================= Delete Label ====================//

	@Override
	public ResponseStatus deleteLabel(String token, String labelid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Label> already = labelRepository.findByLabelidAndUserid(labelid, userid);
		if (already.isEmpty()) {
			throw new NotFoundException("Label Not found");
		} else {
			labelRepository.delete(already.get());
			response = responseCode.getResponse(200, "Label Deleted Successfully....!", null);
			System.out.println("Label Deleted Successfully....!");
		}
		return response;
	}

	// ================= Get All Labels of Specific User ====================//

	@Override
	public ResponseStatus getUserLabels(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Label> labellist = labelRepository.findAllByUserid(userid);
		System.out.println(labellist.toString());

		if (labellist.isEmpty()) {
			throw new BlankException("LabelList is Empty");
		} else {
			response = responseCode.getResponse(200, "Label List", labellist);
			System.out.println(labellist + "\nLabelList Get Successfully");
		}
		return response;
	}

	// ================= Get All Labels ====================//

	@Override
	public ResponseStatus getAll() {
		List<Label> labellist = labelRepository.findAll();
		response = responseCode.getResponse(200, "Label List", labellist);
		System.out.println(labellist + "\nLabelList Get Successfully");
		return response;
	}

	// ================== Add Labels To Note ==================//
	@Override
	public ResponseStatus addLabeltoNote(String token, String labelid, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Label> label = labelRepository.findByLabelidAndUserid(labelid, userid);
		if (label.isEmpty()) {
			throw new BlankException("Label not Exist");
		}

		Optional<Note> note = noteRepository.findByUseridAndNoteid(userid, noteid);
		Optional<Label> label2 = note.get().getLabellist().stream()
				.filter(data -> data.getLabelid().equals(label.get().getLabelid())).findFirst();
		if (!label2.isPresent()) {
			label.get().setUpdated(LocalDateTime.now());
			note.get().setUpdated(LocalDateTime.now());
			note.get().getLabellist().add(label.get());
			noteRepository.save(note.get());
			response = responseCode.getResponse(200, "Label Added To Note Successfully.", note.get());
			System.out.println(note.get() + "\nLabel Added To Note Successfully.");
		} else {
			throw new AlreadyExistsException("Label Already Added To List");
		}
		return response;
	}

	@Override
	public ResponseStatus removeLabelfromNote(String token, String labelid, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Label> label = labelRepository.findByLabelidAndUserid(labelid, userid);
		if (label.isEmpty()) {
			throw new BlankException("Label not Exist");
		}

		Optional<Note> note = noteRepository.findByUseridAndNoteid(userid, noteid);
		Optional<Label> label2 = note.get().getLabellist().stream()
				.filter(data -> data.getLabelid().equals(label.get().getLabelid())).findFirst();
		if (label2.isPresent()) {
			label.get().setUpdated(LocalDateTime.now());
			note.get().setUpdated(LocalDateTime.now());
			note.get().getLabellist().removeIf(list-> list.getLabelid().equals(label.get().getLabelid()));
			System.out.println(note.get().getLabellist());
			noteRepository.save(note.get());
			response = responseCode.getResponse(200, "Label Removed From Note Successfully.", note.get());
			System.out.println(note.get() + "\nLabel Removed From Note Successfully.");
		} else {
			throw new BlankException("Label Not present or Already Removed.");
		}
		return response;
	}

	@Override
	public ResponseStatus getLabelsofNotes(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		
		Optional<Note> note = noteRepository.findByUseridAndNoteid(userid, noteid);
		if(!note.isPresent()) { 
			throw new BlankException("Note Not Present");
		}
		List<Label> label = note.get().getLabellist();
		
		response = responseCode.getResponse(200, "Label List Of Note", label);
		System.out.println("Label List Of Note\n"+label);
		return response;
	}
}
