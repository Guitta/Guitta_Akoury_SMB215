package com.android.gestiondesbiens.model;

public class Personnel {
	
	private int personnelId;
	
	public int getPersonnelId() {
		return personnelId;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	private String personnelName;

}
