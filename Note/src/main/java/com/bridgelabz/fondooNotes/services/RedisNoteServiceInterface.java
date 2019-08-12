package com.bridgelabz.fondooNotes.services;

import org.springframework.stereotype.Service;

import com.bridgelabz.fondooNotes.dto.CollaboratorDto;
import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

@Service
public interface RedisNoteServiceInterface {

	public ResponseStatus createNote(NoteDto notedto, String emailid);

	public ResponseStatus updateNote(NoteDto notedto, String emailid, String noteid);

	public ResponseStatus deleteNote(String emailid, String noteid);

	public ResponseStatus archiveNote(String emailid, String noteid);

	public ResponseStatus pinnedNote(String emailid, String noteid);

	public ResponseStatus trashNote(String emailid, String noteid);

	public ResponseStatus getAllNotes();

	public ResponseStatus updateColor(String emailid, String noteid, String color);

	public ResponseStatus setReminder(String noteid, String emailid, String reminder);

	public ResponseStatus deleteReminder(String noteid, String emailid);

	public ResponseStatus getUserNotes(String emailid);

	public ResponseStatus collaborateUsers(String noteid, String emailid, CollaboratorDto collaborator);

	public ResponseStatus sortByTitleAscendingOrder(String emailid);

	public ResponseStatus sortByTitleDescendingOrder(String emailid);

	public ResponseStatus sortByDateAscendingOrder(String emailid);

	public ResponseStatus sortByDateDescendingOrder(String emailid);

}
