package com.android.gestiondesbiens.model;

public class Transactions {

	private int itemId;
	private String itemCode;
	private String itemDateCreated;
	private String itemName;
	private String itemSpecification;
	private int centerSrcId;
	private String centerSrcName;
	private String salleSrcName;
	private String personnelSrcName;
	private int centerDesId;
	private String centerDesName;
	private String salleDesName;
	private String personnelDesName;
	private String transactionDateCreated;
	private String status;
	private String userName;
	private String typeName;
	private String name;
	private int locationIdSrc;
	private int locationIdDest;
	private int transportId;
	
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
	public int getCenterSrcId() {
		return centerSrcId;
	}
	public String getCenterSrcName() {
		return centerSrcName;
	}
	public String getSalleSrcName() {
		return salleSrcName;
	}
	public String getPersonnelSrcName() {
		return personnelSrcName;
	}
	public int getCenterDesId() {
		return centerDesId;
	}
	public String getCenterDesName() {
		return centerDesName;
	}
	public String getSalleDesName() {
		return salleDesName;
	}
	public String getPersonnelDesName() {
		return personnelDesName;
	}
	public String getTransactionDateCreated() {
		return transactionDateCreated;
	}
	public String getStatus() {
		return status;
	}
	public String getUserName() {
		return userName;
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
	public void setCenterSrcId(int centerSrcId) {
		this.centerSrcId = centerSrcId;
	}
	public void setCenterSrcName(String centerSrcName) {
		this.centerSrcName = centerSrcName;
	}
	public void setSalleSrcName(String salleSrcName) {
		this.salleSrcName = salleSrcName;
	}
	public int getTransportId() {
		return transportId;
	}
	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}
	public void setPersonnelSrcName(String personnelSrcName) {
		this.personnelSrcName = personnelSrcName;
	}
	public void setCenterDesId(int centerDesId) {
		this.centerDesId = centerDesId;
	}
	public void setCenterDesName(String centerDesName) {
		this.centerDesName = centerDesName;
	}
	public void setSalleDesName(String salleDesName) {
		this.salleDesName = salleDesName;
	}
	public void setPersonnelDesName(String personnelDesName) {
		this.personnelDesName = personnelDesName;
	}
	public void setTransactionDateCreated(String transactionDateCreated) {
		this.transactionDateCreated = transactionDateCreated;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLocationIdSrc() {
		return locationIdSrc;
	}
	public void setLocationIdSrc(int locationIdSrc) {
		this.locationIdSrc = locationIdSrc;
	}
	public int getLocationIdDest() {
		return locationIdDest;
	}
	public void setLocationIdDest(int locationIdDest) {
		this.locationIdDest = locationIdDest;
	}
	
	

}
