package com.bridgelabz.Fundoo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Notes")
public class Note {
	@Id
	private String noteId;
	
	@Field("User_Id")
	private String userId;
	
	@Field("Title")
	private String title;
	
	@Field("Description")
	private String description;
	
	@Field("Pined")
	private boolean pinned;
	
	@Field("Archive")
	private boolean archive;
	
	@Field("Trash")
	private boolean trash;
	
	@Field("Created_Date")
	private LocalDateTime createddate;
	
	@Field("Updated_Date")
	private LocalDateTime updateddate;

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPinnned() {
		return pinned;
	}

	public void setPinned(boolean pin) {
		this.pinned = pin;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public LocalDateTime getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDateTime createddate) {
		this.createddate = createddate;
	}

	public LocalDateTime getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(LocalDateTime updateddate) {
		this.updateddate = updateddate;
	}

}
