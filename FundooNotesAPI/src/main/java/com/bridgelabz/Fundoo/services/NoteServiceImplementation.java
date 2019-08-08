package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

	@Override
	public ResponseStatus createNote(NoteDto notedto, String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserid(userid);
		if (alreadyuser.isEmpty()) {
			System.out.println("Note Not Created...!");
			throw new UserNotFoundException();

		} else {
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
		}

		return response;
	}

	@Override
	public ResponseStatus updateNote(NoteDto notedto, String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
		} else {
			already.get().setTitle(notedto.getTitle());
			already.get().setDescription(notedto.getDescription());
			already.get().setUpdateddate(LocalDateTime.now());
			noteRepository.save(already.get());
			Optional<User> user = userRepository.findByUserid(userid);

			userRepository.save(user.get());
			response = responseCode.getResponse(200, "Note Updated Successfully...!", user.get());
			System.out.println("Note Updated Successfully...!");
		}
		return response;
	}

	@Override
	public ResponseStatus deleteNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();

		} else {
			noteRepository.delete(already.get());
			response = responseCode.getResponse(200, "Note Deleted Scessfully", token + noteid);
			System.out.println("Note Deleted Scessfully");
		}
		return response;
	}

	@Override
	public ResponseStatus archiveNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
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

	@Override
	public ResponseStatus pinnedNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
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

	@Override
	public ResponseStatus trashNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();

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

	@Override
	public ResponseStatus getAll(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
		} else {
			response = responseCode.getResponse(200, "List Of Note", already.get().getNotelist());
			System.out.println("List Get Successfully");
		}
		return response;
	}

	@Override
	public ResponseStatus sortByNameAscendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
		} else {
//			List<Note> notelist = already.get().getNotelist();
//			System.out.println(notelist);
//
//			notelist.sort((note1, note2) -> note1.getTitle().compareToIgnoreCase(note2.getTitle()));
//			System.out.println(notelist);
//			response = responseCode.getResponse(200, "Sorted NoteList Get Sucessfully", notelist);

			List<Note> notelist = already.get().getNotelist();
			System.out.println("hi"+notelist);
			String [] note = new String [notelist.size()];
			note = notelist.toArray(note);
			System.out.println("hello"+note);
			Arrays.parallelSort(note);
			response = responseCode.getResponse(200, "Sorted NoteList Get Sucessfully", note.toString());

		}
		return response;
	}

	@Override
	public ResponseStatus sortByNameDescendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<User> already = userRepository.findByUserid(userid);
		if (already.isEmpty()) {
			throw new UserNotFoundException();
		} else {
			List<Note> notelist = already.get().getNotelist();
			System.out.println(notelist);
			String [] note = new String [notelist.size()];
			note = notelist.toArray(note);
			Arrays.parallelSort(note);
			response = responseCode.getResponse(200, "Sorted NoteList Get Sucessfully", note.toString());
		}
		return response;
	}

}
