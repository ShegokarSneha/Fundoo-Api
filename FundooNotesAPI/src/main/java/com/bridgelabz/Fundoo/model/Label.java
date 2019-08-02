package com.bridgelabz.Fundoo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Label")
public class Label {
	@Id
	private String labelid;

	@Field("User_Id")
	private String userid;

	@Field("Label_Name")
	private String labelname;

	@Field("Create_Time")
	private LocalDateTime createtime;

	@Field("Update_Time")
	private LocalDateTime updatetime;

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

	public LocalDateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}

	public LocalDateTime getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "Label [labelid=" + labelid + ", userid=" + userid + ", labelname=" + labelname + ", createtime="
				+ createtime + ", updatetime=" + updatetime + "]";
	}

}
