package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.accesstoken.AccessToken;
import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.exceptionhandling.BlankException;
import com.bridgelabz.Fundoo.exceptionhandling.NotFoundException;
import com.bridgelabz.Fundoo.model.Note;
import com.bridgelabz.Fundoo.model.User;
import com.bridgelabz.Fundoo.repository.NoteRepository;
import com.bridgelabz.Fundoo.repository.UserRepository;
import com.bridgelabz.Fundoo.result.ResponseCode;
import com.bridgelabz.Fundoo.result.ResponseStatus;

@Service("NoteServiceInterface")
public class NoteServiceImplementation implements NoteServiceInterface {

	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private AccessToken accessToken;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ResponseCode responseCode;

	ResponseStatus response;
	
	// ================= Create Note ====================//

	@Override
	public ResponseStatus createNote(NoteDto notedto, String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);
		alreadyuser.orElseThrow(() -> new NotFoundException("User Not Exist To Create Note"));
//		if (alreadyuser.isEmpty()) {
//			System.out.println("Note Not Created...!");
//			throw new UserNotFoundException();
//
//		} else {
		if (notedto.getTitle().isEmpty() || notedto.getDescription().isEmpty()) {
			throw new BlankException("Title And Description Can Not Be Empty.");
		}
		Note note = modelMapper.map(notedto, Note.class);
		note.setCreateddate(LocalDateTime.now());
		note.setUpdateddate(LocalDateTime.now());
		note.setUserId(alreadyuser.get().getUserid());
		note = noteRepository.save(note);
		List<Note> noteList = alreadyuser.get().getNotelist();

		if (!noteList.isEmpty()) {
			noteList.add(note);
			alreadyuser.get().setNotelist(noteList);
		} else {
			new ArrayList<Note>();
			noteList.add(note);
			alreadyuser.get().setNotelist(noteList);
		}
		userRepository.save(alreadyuser.get());
		response = responseCode.getResponse(201, "Note Created Successfully...!", notedto);
		System.out.println("Note Created Successfully...!");

		return response;
	}
	
	// ================= Update Note ====================//

	@Override
	public ResponseStatus updateNote(NoteDto notedto, String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With The Entered Credentials"));
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
		if (notedto.getTitle().isEmpty() && notedto.getDescription().isEmpty()) {
			throw new BlankException("Title And Description Can Not Be Empty.");
		}
		already.get().setTitle(notedto.getTitle());
		already.get().setDescription(notedto.getDescription());
		already.get().setUpdateddate(LocalDateTime.now());
		noteRepository.save(already.get());
		Optional<User> user = userRepository.findByUserid(userid);

		userRepository.save(user.get());
		response = responseCode.getResponse(200, "Note Updated Successfully...!", user.get());
		System.out.println("Note Updated Successfully...!");

		return response;
	}
	
	// ================= Delete Note ====================//

	@Override
	public ResponseStatus deleteNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With The Entered Credentials"));
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//
//		} else {
		if (already.get().isTrash() == false) {
			already.get().setTrash(true);
			already.get().setUpdateddate(LocalDateTime.now());
			noteRepository.save(already.get());
			response = responseCode.getResponse(200, "Note Deleted Scessfully", token + noteid);
			System.out.println("Note Deleted Scessfully");
		}
		response = responseCode.getResponse(200, "Note Already Deleted", token + noteid);
		System.out.println("Note Already Deleted");
		return response;
	}
	
	// ================= Archive Note ====================//

	@Override
	public ResponseStatus archiveNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		if (already.get().isArchive() == false) {
			already.get().setArchive(true);
			response = responseCode.getResponse(200, "Archived Successfully", already.get());
			System.out.println("Archived Successfully");
		} else {
			already.get().setArchive(false);
			response = responseCode.getResponse(200, "Restore Archived Note Successfully", already.get());
			System.out.println("UnArchived Successfully");
		}
		noteRepository.save(already.get());

		return response;
	}
	
	// ================= Pinned Note ====================//

	@Override
	public ResponseStatus pinnedNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
		if (already.get().isPinned() == false) {
			already.get().setPinned(true);
			response = responseCode.getResponse(200, "Pinned Successfully", already.get());
			System.out.println("Pinned Successfully");
		} else {
			already.get().setPinned(false);
			response = responseCode.getResponse(200, "UnPinned Successfully", already.get());
			System.out.println("UnPinned Successfully");
		}
		noteRepository.save(already.get());

		return response;
	}
	
	// ================= Trashed Note ====================//
	
	@Override
	public ResponseStatus trashNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

//		if (already..isEmpty()) {
//			throw new UserNotFoundException();
//
//		} else {
		if (already.get().isTrash() == false) {
			already.get().setTrash(true);
			response = responseCode.getResponse(200, "Trash Successfully", already.get());
			System.out.println("UnArchived Successfully");
		} else {
			already.get().setTrash(false);
			response = responseCode.getResponse(200, "Restore Trash Successfully", already.get());
			System.out.println("Restore Trash Successfully");
		}
		noteRepository.save(already.get());

		return response;
	}
	
	// ================= Get All Note ====================//

	@Override
	public ResponseStatus getAll(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		already.orElseThrow(() -> new NotFoundException("User Not Found To get Note List"));
//		if (already.isEmpty()) {
//			throw new UserNotFoundException();
//		} else {
		response = responseCode.getResponse(200, "List Of Note", already.get().getNotelist());
		System.out.println("List Get Successfully");

		return response;
	}

}
