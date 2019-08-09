package com.bridgelabz.fondooNotes.dto;

import java.util.List;

public class CollaboratorDto {

	private String owner;

	private List<String> collaboratorlist;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String ownerid) {
		this.owner = ownerid;
	}

	public List<String> getCollaboratorlist() {
		return collaboratorlist;
	}

	public void setCollaboratorlist(List<String> collaboratorlist) {
		this.collaboratorlist = collaboratorlist;
	}

	@Override
	public String toString() {
		return "Collaborator [ownerid=" + owner + ", collaboratorlist=" + collaboratorlist + "]";
	}

}
