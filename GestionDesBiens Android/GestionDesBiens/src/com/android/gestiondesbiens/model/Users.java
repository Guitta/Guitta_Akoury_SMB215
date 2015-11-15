package com.android.gestiondesbiens.model;

public class Users {
	
	private String name;
	private String username;
	private String password;
	private String groupname;
	private String registerDt;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getRegisterDt() {
		return registerDt;
	}
	public void setRegisterDt(String l) {
		this.registerDt = l;
	}

}
