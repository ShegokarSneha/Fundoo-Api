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

	
	List<Note> notelist = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateNote() {
		Note user = new Note();
		NoteDto notedto = new NoteDto();
		user.setTitle("Today");
		user.setDescription("Holiday");
		when(modelMapper.map(notedto, Note.class)).thenReturn(user);
		when(noteRepository.save(user)).thenReturn(user);
	}

	@Test
	public void testGetAllNotes() {
		Note user = new Note();
		user.setTitle("Today");
		user.setDescription("Holiday");
		notelist.add(user);
		when(noteRepository.findAll()).thenReturn(notelist);
	}

	@Test
	public void testgetUserNotes() {
		Note user = new Note();
		user.setTitle("Today");
		user.setDescription("Holiday");
		notelist.add(user);
		when(noteRepository.findAllByUserid(anyString())).thenReturn(notelist);
	}
}
