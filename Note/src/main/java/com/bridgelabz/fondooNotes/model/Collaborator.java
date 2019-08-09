package com.bridgelabz.fondooNotes.model;

import java.util.List;

public class Collaborator {
	
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
		return "Collaborator [owner=" + owner + ", collaboratorlist=" + collaboratorlist + "]";
	}
	
	

}
