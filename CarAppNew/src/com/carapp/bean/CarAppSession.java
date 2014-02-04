package com.carapp.bean;

import android.app.Application;

public class CarAppSession extends Application {

	private CustomerData customerData;
	private WorkAssissment workAssissment;
	private JobData jobData;
	
	
	
	public CustomerData getCustomerData() {
		return customerData;
	}
	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}
	public WorkAssissment getWorkAssissment() {
		return workAssissment;
	}
	public void setWorkAssissment(WorkAssissment workAssissment) {
		this.workAssissment = workAssissment;
	}
	public JobData getJobData() {
		return jobData;
	}
	public void setJobData(JobData jobData) {
		this.jobData = jobData;
	}
	
	public void removeSavedData() {
		this.customerData = null;
		this.workAssissment = null;
		this.jobData = null;
	}
	@Override
	public String toString() {
		return "LocalData [customerData=" + customerData + ", workAssissment="
				+ workAssissment + ", jobData=" + jobData + "]";
	}
	
}
