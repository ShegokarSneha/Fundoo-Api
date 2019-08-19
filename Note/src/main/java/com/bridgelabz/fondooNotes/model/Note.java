package com.bridgelabz.fondooNotes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
	private LocalDateTime created;

	@Field("Updated_Date")
	private LocalDateTime updated;

	@Field("Colour")
	private String color;

	@Field("Reminder")
	private LocalDateTime reminder;
	
	public Note(String title, String description) {
		this.title = title;
		this.description = description;
	}

	@DBRef
	@Field("Label_List")
	private List<Label> labellist = new ArrayList<Label>();

	@Field("Collaborator_List")
	private List<Collaborator> collaboratorlist = new ArrayList<Collaborator>();

	public String getNoteid() {
		return noteid;
	}

	public void setNoteid(String noteid) {
		this.noteid = noteid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}

	public List<Label> getLabellist() {
		return labellist;
	}

	public void setLabellist(List<Label> labellist) {
		this.labellist = labellist;
	}

	public List<Collaborator> getCollaboratorlist() {
		return collaboratorlist;
	}

	public void setCollaboratorlist(List<Collaborator> collaboratorlist) {
		this.collaboratorlist = collaboratorlist;
	}

	@Override
	public String toString() {
		return "Note [noteid=" + noteid + ", userid=" + userid + ", title=" + title + ", description=" + description
				+ ", pinned=" + pinned + ", archive=" + archive + ", trash=" + trash + ", created=" + created
				+ ", updated=" + updated + ", color=" + color + ", reminder=" + reminder + ", labellist=" + labellist
				+ ", collaboratorlist=" + collaboratorlist + "]";
	}

}
