package com.android.gestiondesbiens.model;

public class Items {
	
	private int itemId;
	private String itemCode;
	private String itemDateCreated;
	private String itemName;
	private String itemSpecification;
	private String centerName;
	private String salleName;
	private String personnelName;
	private String typeName;
	
	public int getItemId() {
		return itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public String getItemDateCreated() {
		return itemDateCreated;
	}
	public String getItemName() {
		return itemName;
	}
	public String getItemSpecification() {
		return itemSpecification;
	}
	public String getCenterName() {
		return centerName;
	}
	public String getSalleName() {
		return salleName;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setItemDateCreated(String itemDateCreated) {
		this.itemDateCreated = itemDateCreated;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setItemSpecification(String itemSpecification) {
		this.itemSpecification = itemSpecification;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public void setSalleName(String salleName) {
		this.salleName = salleName;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
