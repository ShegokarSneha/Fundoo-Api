package com.bridgelabz.fondooNotes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fondooNotes.accesstoken.AccessToken;
import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.exception.BlankException;
import com.bridgelabz.fondooNotes.exception.NotFoundException;
import com.bridgelabz.fondooNotes.model.Note;
import com.bridgelabz.fondooNotes.repository.NoteRepository;
import com.bridgelabz.fondooNotes.response.ResponseCode;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

@Service("NoteServiceInterface")
public class NoteServiceImplementation implements NoteServiceInterface {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private AccessToken accessToken;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ResponseCode responseCode;

	private ResponseStatus response;

	// ================= Create Note ====================//
	@Override
	public ResponseStatus createNote(NoteDto notedto, String token) {
		String userid = accessToken.verifyAccessToken(token);

		if (notedto.getTitle().isEmpty() || notedto.getDescription().isEmpty()) {
			throw new BlankException("Title And Description Can Not Be Empty.");
		}

		Note note = modelMapper.map(notedto, Note.class);
		note.setCreated(LocalDateTime.now());
		note.setUpdated(LocalDateTime.now());

		// white Color Code initially
		note.setColor("#FFFFFF");

		note.setUserid(userid);
		System.out.println(note.getUserid());
		noteRepository.save(note);

		response = responseCode.getResponse(201, "Note Created Successfully...!", notedto);
		System.out.println("Note Created Successfully...!");
		return response;
	}

	// ================= Update Note ====================//

	@Override
	public ResponseStatus updateNote(NoteDto notedto, String token, String noteid) {
		if (notedto.getTitle().isEmpty() && notedto.getDescription().isEmpty()) {
			throw new BlankException("Title And Description Can Not Be Empty.");
		}
		String userid = accessToken.verifyAccessToken(token);
		System.out.println(userid);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");
		} else {
			already.get().setTitle(notedto.getTitle());
			already.get().setDescription(notedto.getDescription());
			already.get().setUpdated(LocalDateTime.now());
			noteRepository.save(already.get());

//			Optional<User> user = userRepository.findByUserid(userid);
//			userRepository.save(user.get());

			response = responseCode.getResponse(200, "Note Updated Successfully...!", already.get());
			System.out.println("Note Updated Successfully...!");
		}
		return response;
	}

	// ================= Delete Note ====================//

	@Override
	public ResponseStatus deleteNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");

		}
		if (already.get().isTrash() == false) {
			already.get().setTrash(true);
			already.get().setUpdated(LocalDateTime.now());
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
		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");
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

	// ================= Pinned Note ====================//

	@Override
	public ResponseStatus pinnedNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");
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

	// ================= Trashed Note ====================//

	@Override
	public ResponseStatus trashNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");

		} else {
			if (already.get().isTrash() == false) {
				already.get().setTrash(true);
				response = responseCode.getResponse(200, "Trash Successfully", already.get());
				System.out.println("Trash Successfully");
			} else {
				already.get().setTrash(false);
				response = responseCode.getResponse(200, "Restore Trash Successfully", already.get());
				System.out.println("Restore Trash Successfully");
			}
			noteRepository.save(already.get());
		}
		return response;
	}

	// ================= Get All Note ====================//

	@Override
	public ResponseStatus getAllNotes() {
		List<Note> notelist = noteRepository.findAll();
		if (notelist.isEmpty()) {
			throw new NotFoundException("No Notes Present");
		} else {
			response = responseCode.getResponse(200, "List Of Note", notelist);
			System.out.println("List Get Successfully");
		}
		return response;
	}

	// ================= Update Color of Note ====================//

	@Override
	public ResponseStatus updateColor(String token, String noteid, String color) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);

		if (already.isEmpty()) {
			throw new NotFoundException("Note Not Found With Given Id");
		} else {
			already.get().setColor(color);
			response = responseCode.getResponse(200, "Colour Updated Successfully", already.get());
			System.out.println("Colour Updated Successfully");
		}
		return response;
	}

	// ================ Get All Notes of specific User =============//

	@Override
	public ResponseStatus getUserNotes(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Note> notelist = noteRepository.findAllByUserid(userid);
		if (notelist.isEmpty()) {
			throw new NotFoundException("No Notes Present");
		} else {
			response = responseCode.getResponse(200, "User NoteList", notelist);
			System.out.println("User Notelist Get Successfully.");
		}
		return response;
	}

	// ================= Setting Remainder for Note ====================//

	@Override
	public ResponseStatus setReminder(String noteid, String token, String reminder) {
		String userid = accessToken.verifyAccessToken(token);
		System.out.println(userid);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		System.out.println(already.get());
		if (!already.isPresent()) {
			response = responseCode.getResponse(206, "Note is not present", noteid);
			return response;
		}

//		 DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime today = LocalDateTime.now();
		System.out.println(today);

		LocalDateTime reminderDateFormat = LocalDateTime.parse(reminder, formatter);
		if (today.compareTo(reminderDateFormat) > 0) {
			response = responseCode.getResponse(208, "Remainder time should be greater than now", reminderDateFormat);
			System.out.println("Remainder time should be greater than now");
		} else {
			System.out.println(reminderDateFormat);
			already.get().setReminder(reminderDateFormat);
			noteRepository.save(already.get());

			response = responseCode.getResponse(208, "Remainder set successfully", already.get());
			System.out.println("Remainder set successfully");
		}
		return response;
	}

	// ================= Delete Remainder for Note ====================//

	@Override
	public ResponseStatus deleteReminder(String noteid, String token) {
		String userid = accessToken.verifyAccessToken(token);
		System.out.println(userid);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		if (!already.isPresent()) {
			response = responseCode.getResponse(404, "Note is not present", noteid);
			System.out.println("Note is not present");
		} else {
			already.get().setReminder(null);
			noteRepository.save(already.get());
			response = responseCode.getResponse(200, "Reminder Deleted Successfully", already.get());
			System.out.println("Reminder Deleted Successfully");
		}
		return response;
	}

//	public ResponseStatus sortByNameAscendingOrder(String token) { 
//		String userid = accessToken.verifyAccessToken(token);
//		Optional<User> already = userRepository.findByUserid(userid); 
//		if (already.isEmpty()) { 
//			throw new UserNotFoundException(); 
//			} else { 
//				// List<Note> notelist = already.get().getNotelist(); 
//				// System.out.println(notelist); // //
//	  notelist.sort((note1, note2) -> note1.getTitle().compareToIgnoreCase(note2.getTitle())); //
//	  System.out.println(notelist); 
//	  // response = responseCode.getResponse(200, "Sorted NoteList Get Sucessfully", notelist);
//	  
//	  List<Note> notelist = already.get().getNotelist(); 
//	System.out.println("hi" + notelist); 
//	String[] note = new String[notelist.size()]; 
//	note = notelist.toArray(note); System.out.println("hello" + note);
//	  Arrays.parallelSort(note); response = responseCode.getResponse(200,
//	  "Sorted NoteList Get Sucessfully", note.toString());
//	 
//	  } 
//	return response; 
//	}

}
