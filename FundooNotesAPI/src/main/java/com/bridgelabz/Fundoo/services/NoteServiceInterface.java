package com.bridgelabz.Fundoo.services;

import com.bridgelabz.Fundoo.dto.NoteDto;
import com.bridgelabz.Fundoo.result.ResponseStatus;

public interface NoteServiceInterface {

	public ResponseStatus createNote(NoteDto notedto,String token);

	public ResponseStatus updateNote(NoteDto notedto, String token, String noteid);
	
	public ResponseStatus deleteNote(String token, String noteid);
	
	public ResponseStatus archiveNote(String token, String noteid);
	
	public ResponseStatus pinnedNote(String token, String noteid);
	
	public ResponseStatus trashNote(String token, String noteid);
	
	public ResponseStatus getAll(String token);
	
//	public ResponseStatus sortByNameAscendingOrder(String token);
//	
//	public ResponseStatus sortByNameDescendingOrder(String token);
	
}
