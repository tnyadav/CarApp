package com.carapp.bean;

public class Ragistration {


	private String registrationPlateNumber;
	private String csdId;
	private String display;

	public Ragistration(String registrationPlateNumber, String csdId,String display) {
		super();
		this.registrationPlateNumber = registrationPlateNumber;
		this.csdId = csdId;
		if (display.equals("")||display.equals("yes")) {
			this.display = "yes";	
		}else {
			this.display="no";
		}
	}

	public String getRegistrationPlateNumber() {
		return registrationPlateNumber;
	}

	public void setRegistrationPlateNumber(String registrationPlateNumber) {
		this.registrationPlateNumber = registrationPlateNumber;
	}

	public String getCsdId() {
		return csdId;
	}

	public void setCsdId(String csdId) {
		this.csdId = csdId;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		if (display.equals("")||display.equals("yes")) {
			this.display = "yes";	
		}else {
			this.display="no";
		}
		
	}

	@Override
	public String toString() {
		return registrationPlateNumber;
	}

}
