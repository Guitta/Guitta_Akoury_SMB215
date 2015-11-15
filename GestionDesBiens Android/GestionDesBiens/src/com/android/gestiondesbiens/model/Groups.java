package com.android.gestiondesbiens.model;

public class Groups {

	private int Id;
	private String username;
	private String groupname;
	private String description;
	
	public int getId() {
		return Id;
	}
	public String getUsername() {
		return username;
	}
	public String getGroupname() {
		return groupname;
	}
	public String getDescription() {
		return description;
	}
	public void setId(int id) {
		Id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
