package com.bridgelabz.fondooNotes.dto;

public class LabelDto {

	private String labelname;
	
	public LabelDto(String labelname) {
		this.labelname = labelname;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	@Override
	public String toString() {
		return "LabelDto [labelname=" + labelname + "]";
	}

}
