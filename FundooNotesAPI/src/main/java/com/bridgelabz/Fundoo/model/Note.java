package com.bridgelabz.Fundoo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Notes")
public class Note {
	@Id
	private String noteid;
	
	@Field("User_id")
	private String userid;
	
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
	
	@Field("Label_List")
	private List<Label> labellist;

	public String getNoteId() {
		return noteid;
	}

	public void setNoteId(String noteid) {
		this.noteid = noteid;
	}

	public String getUserId() {
		return userid;
	}

	public void setUserId(String userid) {
		this.userid = userid;
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

	public boolean isPinned() {
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
	
	public List<Label> getLabellist() {
		return labellist;
	}

	public void setLabellist(List<Label> labellist) {
		this.labellist = labellist;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteid + ", userId=" + userid + ", title=" + title + ", description=" + description
				+ ", pinned=" + pinned + ", archive=" + archive + ", trash=" + trash + ", createddate=" + createddate
				+ ", updateddate=" + updateddate + ", labellist=" + labellist + "]";
	}
	
	

}
