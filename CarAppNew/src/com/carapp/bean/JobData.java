package com.carapp.bean;

public class JobData {

	private String dealer_recommendations ;
	private String cust_approved_work ;
	public  String radiodata;
	private String Quotation1 ;
	private String Quotation2;
	private String ValuablesRemovedFromVehicle ;
	private String observations;
	
	private String wheel_nuts_torqued;
	private String wheels_cleaned;
	private String wheels_balanced;
	private String alignment_done;
	private String tyre_pressure_front;
	private String tyre_pressure_back;
	private String tyres_polished;
	private String lock_nut_returned;
	private String car_tested_by_salesperson;
	private String work_inspected_by_salesperson;
	private String work_approved_by_salesperson;
	private String customer_satisfied;
	private String diplay;
	
	

	public String getDealer_recommendations() {
		return dealer_recommendations;
	}
	public void setDealer_recommendations(String dealer_recommendations) {
		this.dealer_recommendations = dealer_recommendations;
	}
	public String getCust_approved_work() {
		return cust_approved_work;
	}
	public void setCust_approved_work(String cust_approved_work) {
		this.cust_approved_work = cust_approved_work;
	}
	public  String getRadiodata() {
		return radiodata;
	}
	public  void setRadiodata(String radiodata) {
		this.radiodata = radiodata;
	}
	public String getQuotation1() {
		return Quotation1;
	}
	public void setQuotation1(String quotation1) {
		Quotation1 = quotation1;
	}
	public String getQuotation2() {
		return Quotation2;
	}
	public void setQuotation2(String quotation2) {
		Quotation2 = quotation2;
	}
	public String getValuablesRemovedFromVehicle() {
		return ValuablesRemovedFromVehicle;
	}
	public void setValuablesRemovedFromVehicle(String valuablesRemovedFromVehicle) {
		ValuablesRemovedFromVehicle = valuablesRemovedFromVehicle;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public String getWheel_nuts_torqued() {
		return wheel_nuts_torqued;
	}
	public void setWheel_nuts_torqued(String wheel_nuts_torqued) {
		this.wheel_nuts_torqued = wheel_nuts_torqued;
	}
	public String getWheels_cleaned() {
		return wheels_cleaned;
	}
	public void setWheels_cleaned(String wheels_cleaned) {
		this.wheels_cleaned = wheels_cleaned;
	}
	public String getWheels_balanced() {
		return wheels_balanced;
	}
	public void setWheels_balanced(String wheels_balanced) {
		this.wheels_balanced = wheels_balanced;
	}
	public String getAlignment_done() {
		return alignment_done;
	}
	public void setAlignment_done(String alignment_done) {
		this.alignment_done = alignment_done;
	}
	public String getTyre_pressure_front() {
		return tyre_pressure_front;
	}
	public void setTyre_pressure_front(String tyre_pressure_front) {
		this.tyre_pressure_front = tyre_pressure_front;
	}
	public String getTyre_pressure_back() {
		return tyre_pressure_back;
	}
	public void setTyre_pressure_back(String tyre_pressure_back) {
		this.tyre_pressure_back = tyre_pressure_back;
	}
	public String getTyres_polished() {
		return tyres_polished;
	}
	public void setTyres_polished(String tyres_polished) {
		this.tyres_polished = tyres_polished;
	}
	public String getLock_nut_returned() {
		return lock_nut_returned;
	}
	public void setLock_nut_returned(String lock_nut_returned) {
		this.lock_nut_returned = lock_nut_returned;
	}
	public String getCar_tested_by_salesperson() {
		return car_tested_by_salesperson;
	}
	public void setCar_tested_by_salesperson(String car_tested_by_salesperson) {
		this.car_tested_by_salesperson = car_tested_by_salesperson;
	}
	public String getWork_inspected_by_salesperson() {
		return work_inspected_by_salesperson;
	}
	public void setWork_inspected_by_salesperson(
			String work_inspected_by_salesperson) {
		this.work_inspected_by_salesperson = work_inspected_by_salesperson;
	}
	public String getWork_approved_by_salesperson() {
		return work_approved_by_salesperson;
	}
	public void setWork_approved_by_salesperson(String work_approved_by_salesperson) {
		this.work_approved_by_salesperson = work_approved_by_salesperson;
	}
	public String getCustomer_satisfied() {
		return customer_satisfied;
	}
	public void setCustomer_satisfied(String customer_satisfied) {
		this.customer_satisfied = customer_satisfied;
	}
	public String getDiplay() {
		return diplay;
	}
	public void setDiplay(String diplay) {
		this.diplay = diplay;
	}
	
	@Override
	public String toString() {
		return "JobData [dealer_recommendations=" + dealer_recommendations
				+ ", cust_approved_work=" + cust_approved_work + ", radiodata="
				+ radiodata + ", Quotation1=" + Quotation1 + ", Quotation2="
				+ Quotation2 + ", ValuablesRemovedFromVehicle="
				+ ValuablesRemovedFromVehicle + ", observations="
				+ observations + ", wheel_nuts_torqued=" + wheel_nuts_torqued
				+ ", wheels_cleaned=" + wheels_cleaned + ", wheels_balanced="
				+ wheels_balanced + ", alignment_done=" + alignment_done
				+ ", tyre_pressure_front=" + tyre_pressure_front
				+ ", tyre_pressure_back=" + tyre_pressure_back
				+ ", tyres_polished=" + tyres_polished + ", lock_nut_returned="
				+ lock_nut_returned + ", car_tested_by_salesperson="
				+ car_tested_by_salesperson
				+ ", work_inspected_by_salesperson="
				+ work_inspected_by_salesperson
				+ ", work_approved_by_salesperson="
				+ work_approved_by_salesperson + ", customer_satisfied="
				+ customer_satisfied + ", diplay=" + diplay + "]";
	}
}
