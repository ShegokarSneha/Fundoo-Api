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
import com.bridgelabz.Fundoo.exceptionhandling.UserNotFoundException;
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

	public ResponseStatus createNote(NoteDto notedto, String token) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserId(userId);
		if (alreadyuser.isEmpty()) {
			System.out.println("Note Not Created...!");
			throw new UserNotFoundException();
			// response = responseCode.getResponse(200, "Note Not Created...!", notedto);

		} else {
			Note note = modelMapper.map(notedto, Note.class);
			note.setCreateddate(LocalDateTime.now());
			note.setUpdateddate(LocalDateTime.now());
			note.setUserId(alreadyuser.get().getUserId());
			note = noteRepository.save(note);
			List<Note> noteList = alreadyuser.get().getNotelist();
			// System.out.println(noteList);
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
		}

		return response;
	}

	public ResponseStatus updateNote(NoteDto notedto, String token, String noteId) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUserIdAndNoteId(userId, noteId);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
			// response = responseCode.getResponse(401, "Invalid Credentials", token +
			// noteId);
			// System.out.println("Invalid Credentials");
		} else {
			already.get().setTitle(notedto.getTitle());
			already.get().setDescription(notedto.getDescription());
			already.get().setUpdateddate(LocalDateTime.now());
			noteRepository.save(already.get());
			Optional<User> user = userRepository.findByUserId(userId);

			userRepository.save(user.get());
			response = responseCode.getResponse(200, "Note Updated Successfully...!", user.get());
			System.out.println("Note Updated Successfully...!");
		}
		return response;
	}

	public ResponseStatus deleteNote(String token, String noteId) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUserIdAndNoteId(userId, noteId);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
			/*
			 * response = responseCode.getResponse(401, "Invalid Credentials", token +
			 * noteId); System.out.println("Invalid Credentials");
			 */
		} else {
			noteRepository.delete(already.get());
			response = responseCode.getResponse(200, "Note Deleted Scessfully", token + noteId);
			System.out.println("Note Deleted Scessfully");
		}
		return response;
	}

	public ResponseStatus archiveNote(String token, String noteid) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUserIdAndNoteId(userId, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
			// response = responseCode.getResponse(401, "Invalid Credentials", token +
			// noteid);
//			System.out.println("Invalid Credentials");
		} else {
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
		}

		return response;
	}

	public ResponseStatus pinnedNote(String token, String noteId) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUserIdAndNoteId(userId, noteId);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
			// response = responseCode.getResponse(401, "Invalid Credentials", token +
			// noteId);
			// System.out.println("Invalid Credentials");
		} else {
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
		}

		return response;
	}

	public ResponseStatus trashNote(String token, String noteId) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUserIdAndNoteId(userId, noteId);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
			/*
			 * response = responseCode.getResponse(401, "Invalid Credentials", token +
			 * noteId); System.out.println("Invalid Credentials");
			 */
		} else {
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
		}
		return response;
	}

	public ResponseStatus getAll(String token) {
		String userId = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserId(userId);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
//			response = responseCode.getResponse(401, "Invalid Credentials", token);
		} else {
			response = responseCode.getResponse(200, "List Of Note", already.get().getNotelist());
			System.out.println("List Get Successfully");
		}
		return response;
	}

}
