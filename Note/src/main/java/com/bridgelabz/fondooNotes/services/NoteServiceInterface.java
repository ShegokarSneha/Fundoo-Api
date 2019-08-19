package com.bridgelabz.fondooNotes.services;

import com.bridgelabz.fondooNotes.dto.CollaboratorDto;
import com.bridgelabz.fondooNotes.dto.NoteDto;
import com.bridgelabz.fondooNotes.model.Note;
import com.bridgelabz.fondooNotes.response.ResponseStatus;

public interface NoteServiceInterface {

	public Note createNote(NoteDto notedto, String token);

	public ResponseStatus updateNote(NoteDto notedto, String token, String noteid);

	public ResponseStatus deleteNote(String token, String noteid);

	public ResponseStatus archiveNote(String token, String noteid);

	public ResponseStatus pinnedNote(String token, String noteid);

	public ResponseStatus trashNote(String token, String noteid);

	public ResponseStatus getAllNotes();

	public ResponseStatus updateColor(String token, String noteid, String color);

	public ResponseStatus setReminder(String noteid, String token, String reminder);

	public ResponseStatus deleteReminder(String noteid, String token);

	public ResponseStatus getUserNotes(String token);

	public ResponseStatus collaborateUsers(String noteid, String token, CollaboratorDto collaborator);

	public ResponseStatus sortByTitleAscendingOrder(String token);

	public ResponseStatus sortByTitleDescendingOrder(String token);

	public ResponseStatus sortByDateAscendingOrder(String token);

	public ResponseStatus sortByDateDescendingOrder(String token);

}
