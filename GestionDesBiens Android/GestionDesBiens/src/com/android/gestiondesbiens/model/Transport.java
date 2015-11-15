package com.android.gestiondesbiens.model;

import java.sql.Date;

public class Transport {
	
	private int transportId;
	private String personnelName;
	private String transportDate;
	
	public int getTransportId() {
		return transportId;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public String getTransportDate() {
		return transportDate;
	}
	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public void setTransportDate(String personnelDate) {
		this.transportDate = personnelDate;
	}
	
	

}
