package com.bridgelabz.fondooNotes.services;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.model.Note;
import com.bridgelabz.fondooNotes.repository.NoteRepository;

@RunWith(SpringRunner.class)
public class NoteServiceImplementationTest {

	@Mock
	ModelMapper modelMapper;

	@Mock
	NoteRepository noteRepository;

	Note user = new Note("Today", "Holiday");
	Note user1 = new Note("smita", "Shegokar");
	List<Note> notelist = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateNote() {
		NoteDto notedto = new NoteDto("Today", "Holiday");
		Note note = new Note("Today", "Holiday");
		when(modelMapper.map(notedto, Note.class)).thenReturn(note);
		when(noteRepository.save(note)).thenReturn(note);
	}

	@Test
	public void testGetAllNotes() {
		notelist.add(user);
		notelist.add(user1);
		when(noteRepository.findAll()).thenReturn(notelist);
	}

	@Test
	public void testgetUserNotes() {
		notelist.add(user);
		notelist.add(user1);
		when(noteRepository.findAllByUserid(anyString())).thenReturn(notelist);
	}
}
