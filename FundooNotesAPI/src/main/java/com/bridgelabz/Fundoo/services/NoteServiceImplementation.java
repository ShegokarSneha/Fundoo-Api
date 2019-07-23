package com.bridgelabz.Fundoo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.Fundoo.accesstoken.AccessToken;
import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.model.Note;
import com.bridgelabz.Fundoo.model.User;
import com.bridgelabz.Fundoo.repository.LoginRegistrationRepository;
import com.bridgelabz.Fundoo.repository.NoteRepository;
import com.bridgelabz.Fundoo.result.NoteStatus;

@Service
public class NoteServiceImplementation implements NoteServiceInterface {

	@Autowired
	NoteRepository noteRepository;
	@Autowired
	AccessToken accessToken;
	@Autowired
	LoginRegistrationRepository userRepository;
	@Autowired
	ModelMapper modelMapper;

	NoteStatus noteStatus = new NoteStatus();

	public NoteStatus createNote(NoteDto notedto, String token, HttpServletRequest request) {
		Note note = null;
		String userId = accessToken.verifyAccessToken(token);
		Optional<User> alreadyuser = userRepository.findByUserId(userId);
		if (!alreadyuser.isEmpty()) {
			note = modelMapper.map(notedto, Note.class);
			note.setUserId(alreadyuser.get().getUserId());
			note.setCreateddate(LocalDateTime.now());
			note.setUpdateddate(LocalDateTime.now());
			note = noteRepository.save(note);
			List<Note> noteList = alreadyuser.get().getNoteList();
			if (noteList.isEmpty()) {
				noteList.add(note);
				alreadyuser.get().setNote(noteList);
			} else {
				new ArrayList<Note>();
				noteList.add(note);
				alreadyuser.get().setNote(noteList);
			}
			userRepository.save(alreadyuser.get());
			noteStatus.setTitle(note.getTitle());
			noteStatus.setStatus("Note Created Successfully...!");
			System.out.println("Note Created Successfully...!");
		} else {
			noteStatus.setTitle(notedto.getTitle());
			noteStatus.setStatus("Note Not Created...!");
			System.out.println("Note Not Created...!");
		}
		return noteStatus;
	}

	public NoteStatus updateNote(NoteDto noteDto, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	

}