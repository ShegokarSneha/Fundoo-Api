package com.bridgelabz.fondooNotes.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Label")
public class Label {
	@Id
	private String labelid;

	@Field("User_id")
	private String userid;

	@Field("Label_Name")
	private String labelname;

	@Field("Create_Time")
	private LocalDateTime created;

	@Field("Update_Time")
	private LocalDateTime updated;
	
	public String getLabelid() {
		return labelid;
	}

	public void setLabelid(String labelid) {
		this.labelid = labelid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
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

	@Override
	public String toString() {
		return "Label [labelid=" + labelid + ", userid=" + userid + ", labelname=" + labelname + ", created="
				+ created + ", updated=" + updated +"]";
	}

	

}
