 package com.bridgelabz.fondooNotes.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fondooNotes.dto.CollaboratorDto;
import com.bridgelabz.fondooNotes.dto.MailDto;
import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.exception.AlreadyExistsException;
import com.bridgelabz.fondooNotes.exception.BlankException;
import com.bridgelabz.fondooNotes.exception.NotFoundException;
import com.bridgelabz.fondooNotes.model.Collaborator;
import com.bridgelabz.fondooNotes.model.Note;
import com.bridgelabz.fondooNotes.rabbitmq.QueueProducer;
import com.bridgelabz.fondooNotes.repository.NoteRepository;
import com.bridgelabz.fondooNotes.response.ResponseCode;
import com.bridgelabz.fondooNotes.response.ResponseStatus;
import com.bridgelabz.fondooNotes.uitility.AccessToken;

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

	@Autowired
	private QueueProducer queueProducer;
	
	@Autowired
	private RestTemplate restTemplate;

	private ResponseStatus response;

	MailDto maildto = new MailDto();

	// ================= Create Note ====================//

	@Override
	public ResponseStatus createNote(NoteDto notedto, String token) {
		ResponseStatus user = restTemplate.getForObject("http://localhost:8080/user/getuser/"+token, ResponseStatus.class);
		String userid = accessToken.verifyAccessToken(token);
		if(!(user.getMessage() == null)) {

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

		response = responseCode.getResponse(201, "Note Created Successfully...!", note);
		System.out.println("Note Created Successfully...!");
		}
		else {
			throw new NotFoundException("User Not Found");
		}
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
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		already.get().setTitle(notedto.getTitle());
		already.get().setDescription(notedto.getDescription());
		already.get().setUpdated(LocalDateTime.now());
		noteRepository.save(already.get());

//			Optional<User> user = userRepository.findByUserid(userid);
//			userRepository.save(user.get());

		response = responseCode.getResponse(200, "Note Updated Successfully...!", already.get());
		System.out.println("Note Updated Successfully...!");

		return response;
	}

	// ================= Delete Note ====================//

	@Override
	public ResponseStatus deleteNote(String token, String noteid) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

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
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		already.get().setColor(color);
		response = responseCode.getResponse(200, "Colour Updated Successfully", already.get());
		System.out.println("Colour Updated Successfully");
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
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
		LocalDateTime weekly = LocalDateTime.now().plusWeeks(1);
		LocalDateTime monthly = LocalDateTime.now().plusMonths(1);
		LocalDateTime yearly = LocalDateTime.now().plusYears(1);
		System.out.println(today);

		switch (reminder) {
		case "today":
			response = responseCode.getResponse(208, "Remainder time should be greater than now", today);
			System.out.println("Remainder time should be greater than now");
			break;

		case "tomorrow":
			already.get().setReminder(tomorrow);
			response = responseCode.getResponse(208, "Remainder set For Tomorrow", already.get());
			System.out.println("Remainder set successfully");
			break;

		case "weekly":
			already.get().setReminder(weekly);
			response = responseCode.getResponse(208, "Remainder set For Weekly", already.get());
			System.out.println("Remainder set successfully");
			break;

		case "monthly":
			already.get().setReminder(monthly);
			response = responseCode.getResponse(208, "Remainder set For Monthly", already.get());
			System.out.println("Remainder set successfully");
			break;

		case "yearly":
			already.get().setReminder(yearly);
			response = responseCode.getResponse(208, "Remainder set For Yearly.", already.get());
			System.out.println("Remainder set successfully");
			break;

		}
		noteRepository.save(already.get());

		return response;
	}

	// ================= Delete Remainder for Note ====================//

	@Override
	public ResponseStatus deleteReminder(String noteid, String token) {
		String userid = accessToken.verifyAccessToken(token);
		System.out.println(userid);
		Optional<Note> already = noteRepository.findByUseridAndNoteid(userid, noteid);
		already.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		already.get().setReminder(null);
		noteRepository.save(already.get());
		response = responseCode.getResponse(200, "Reminder Deleted Successfully", already.get());
		System.out.println("Reminder Deleted Successfully");

		return response;
	}

	// ============ Add Collaborator to Note ==================//

	@Override
	public ResponseStatus collaborateUsers(String noteid, String token, CollaboratorDto collaboratordto) {
		String userid = accessToken.verifyAccessToken(token);
		Optional<Note> note = noteRepository.findByUseridAndNoteid(userid, noteid);
		note.orElseThrow(() -> new NotFoundException("Note Not Found With Given Id"));

		System.out.println(note);

		Optional<Collaborator> coll = note.get().getCollaboratorlist().stream()
				.filter(data -> data.getCollaboratorlist().equals(collaboratordto.getCollaboratorlist())).findFirst();
		System.out.println(coll);
		if (!coll.isPresent()) {
			Collaborator collaborator = modelMapper.map(collaboratordto, Collaborator.class);
			note.get().getCollaboratorlist().add(collaborator);
			noteRepository.save(note.get());

			String text = collaborator.getOwner() + "\n Has added a note with you" + "\nNote Details : "
					+ note.get().getTitle() + " " + note.get().getDescription();

			for (int i = 0; i < collaborator.getCollaboratorlist().size(); i++) {
				maildto.setEmail(collaborator.getCollaboratorlist().get(i));
				maildto.setSubject("Note Collaboration");
				maildto.setBody(text);
				try {
					queueProducer.produce(maildto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			response = responseCode.getResponse(200, "Collaborator Added Successfully", note.get());
			System.out.println("Collaborator Added Successfully");
		} else {
			throw new AlreadyExistsException("Collaborator Is Already Exist");
		}
		return response;
	}

	// ============= Sort Notes By Name In Ascending Order =============//

	@Override
	public ResponseStatus sortByTitleAscendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Note> notelist = noteRepository.findAllByUserid(userid);
		if (notelist.isEmpty()) {
			throw new BlankException("Notes Not Exist");
		}
		notelist.sort(Comparator.comparing(Note::getTitle));
		response = responseCode.getResponse(200, "Sorted Note List Ascending Order", notelist);
		System.out.println("Note List Sorted By Title in Ascending Order" + "\n" + notelist);
		return response;
	}

	// ============= Sort Notes By Name In Descending Order =============//

	@Override
	public ResponseStatus sortByTitleDescendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Note> notelist = noteRepository.findAllByUserid(userid);
		if (notelist.isEmpty()) {
			throw new BlankException("Notes Not Exist");
		}
		notelist.sort(Comparator.comparing(Note::getTitle).reversed());
		response = responseCode.getResponse(200, "Sorted Note List In Descending Order", notelist);
		System.out.println("Note List Sorted By Title in Descending Order" + "\n" + notelist);
		return response;
	}

	// ============= Sort Notes By Date In Ascending Order =============//

	@Override
	public ResponseStatus sortByDateAscendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Note> notelist = noteRepository.findAllByUserid(userid);
		if (notelist.isEmpty()) {
			throw new BlankException("Notes Not Exist");
		}
		notelist.sort(Comparator.comparing(Note::getCreated));
		response = responseCode.getResponse(200, "Sorted Note List Ascending Order", notelist);
		System.out.println("Note List Sorted By Date in Ascending Order" + "\n" + notelist);
		return response;
	}

	// ============= Sort Notes By Date In Ascending Order =============//

	@Override
	public ResponseStatus sortByDateDescendingOrder(String token) {
		String userid = accessToken.verifyAccessToken(token);
		List<Note> notelist = noteRepository.findAllByUserid(userid);
		if (notelist.isEmpty()) {
			throw new BlankException("Notes Not Exist");
		}
		notelist.sort(Comparator.comparing(Note::getCreated).reversed());
		response = responseCode.getResponse(200, "Sorted Note List In Descending Order", notelist);
		System.out.println("Note List Sorted By Date in Descending Order" + "\n" + notelist);
		return response;
	}

}
