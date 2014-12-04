package com.carapp.bean;

import java.util.List;

import com.carapp.util.PdfInfo.status;

import android.app.Application;

public class CarAppSession extends Application {

	private CustomerData customerData;
	private WorkAssissment workAssissment;
	private JobData jobData;
	private status currentUploadFileStatus;
	private List<PartAssisment> partAssisments;
	
	
	
	
	public List<PartAssisment> getPartAssisments() {
		return partAssisments;
	}
	public void setPartAssisments(List<PartAssisment> partAssisments) {
		this.partAssisments = partAssisments;
	}
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
	
	public status getCurrentUploadFileStatus() {
		return currentUploadFileStatus;
	}
	public void setCurrentUploadFileStatus(status currentUploadFileStatus) {
		this.currentUploadFileStatus = currentUploadFileStatus;
	}
	public void removeSavedData() {
		this.customerData = null;
		this.workAssissment = null;
		this.jobData = null;
		this.partAssisments=null;
	}
	@Override
	public String toString() {
		return "LocalData [customerData=" + customerData + ", workAssissment="
				+ workAssissment + ", jobData=" + jobData + "]";
	}
	
}
