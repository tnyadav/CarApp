package com.carapp.bean;

public class CustomerData {

	private String Branch;
	private String Saleperson;
	private String Customer;
	private String company;
	private String email;
	private String ContactNo;
	private String Address;
	private String Make;
	private String Model;
	private String Year;
	private String Odomstrer;
	private String Registration;
	private String Date;
	private String Time;
	private String cust_resonfor_visit ;
	
	//private String csdId;
	
	public String getBranch() {
		return Branch;
	}
	public void setBranch(String branch) {
		Branch = branch;
	}
	public String getSaleperson() {
		return Saleperson;
	}
	public void setSaleperson(String saleperson) {
		Saleperson = saleperson;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public String getContactNo() {
		return ContactNo;
	}
	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getMake() {
		return Make;
	}
	public void setMake(String make) {
		Make = make;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getOdomstrer() {
		return Odomstrer;
	}
	public void setOdomstrer(String odomstrer) {
		Odomstrer = odomstrer;
	}
	public String getRegistration() {
		return Registration;
	}
	public void setRegistration(String registration) {
		Registration = registration;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String strTime) {
		this.Time = strTime;
	}
	/*public String getCsdId() {
		return csdId;
	}
	public void setCsdId(String csdId) {
		this.csdId = csdId;
	}*/
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCust_resonfor_visit() {
		return cust_resonfor_visit;
	}
	public void setCust_resonfor_visit(String cust_resonfor_visit) {
		this.cust_resonfor_visit = cust_resonfor_visit;
	}

	
	@Override
	public String toString() {
		return "CustomerData [Branch=" + Branch + ", Saleperson=" + Saleperson
				+ ", Customer=" + Customer + ", company=" + company
				+ ", email=" + email + ", ContactNo=" + ContactNo
				+ ", Address=" + Address + ", Make=" + Make + ", Model="
				+ Model + ", Year=" + Year + ", Odomstrer=" + Odomstrer
				+ ", Registration=" + Registration + ", Date=" + Date
				+ ", Time=" + Time + ", cust_resonfor_visit="
				+ cust_resonfor_visit + "]";
	}
	
	
	
}
