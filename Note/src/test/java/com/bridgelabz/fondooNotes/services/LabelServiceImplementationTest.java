package com.bridgelabz.fondooNotes.services;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fondooNotes.dto.LabelDto;
import com.bridgelabz.fondooNotes.model.Label;
import com.bridgelabz.fondooNotes.repository.LabelRepository;

@RunWith(SpringRunner.class)
class LabelServiceImplementationTest {

	@Mock
	ModelMapper modelMapper;

	@Mock
	LabelRepository labelRepository;

	Label label = new Label("Holiday");
	Label label1 = new Label("Remember that");
	List<Label> labellist = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateLabel() {
		LabelDto labeldto = new LabelDto("Remember that");
		Optional<Label> already = Optional.of(label);
		when(labelRepository.findByLabelidAndUserid(anyString(), anyString())).thenReturn(already);
		when(modelMapper.map(labeldto, Label.class)).thenReturn(label);
		when(labelRepository.save(label)).thenReturn(label);
	}

	@Test
	void testGetUserLabels() {
		labellist.add(label);
		labellist.add(label1);
		when(labelRepository.findAllByUserid(anyString())).thenReturn(labellist);
	}

	@Test
	void testGetAll() {
		labellist.add(label);
		labellist.add(label1);
		when(labelRepository.findAll()).thenReturn(labellist);
	}

}
