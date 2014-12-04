package com.carapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;

public class JsonParser {

	private JSONObject jsonObject;
	Resources resource;

	public JsonParser(Context context, JSONObject jsonObject) {
		super();
		try {
			this.jsonObject = jsonObject.getJSONObject("csd");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		this.resource = context.getResources();
	}



	public CustomerData getCustomerData() {
          CustomerData customerData=new CustomerData();
          
          customerData.setBranch(jsonObject.optString("branch"));
          customerData.setSaleperson(jsonObject.optString("salesperson"));
          customerData.setCustomer(jsonObject.optString("customer"));
          customerData.setContactNo(jsonObject.optString("contact_number"));
          customerData.setCompany(jsonObject.optString("company"));
          customerData.setEmail(jsonObject.optString("email"));
          customerData.setAddress(jsonObject.optString("address"));
          customerData.setMake(jsonObject.optString("make"));
          customerData.setModel(jsonObject.optString("model"));
          customerData.setYear(jsonObject.optString("year"));
          customerData.setOdomstrer(jsonObject.optString("odometer"));
          customerData.setRegistration(jsonObject.optString("reg_plate_no"));
          customerData.setDate(jsonObject.optString("date"));
          customerData.setTime(jsonObject.optString("time")); 
          customerData.setCust_resonfor_visit(jsonObject.optString("cust_visit_reason"));
          customerData.setFleetSelection(jsonObject.optString("fleet_selected"));
          
          
          
		  return customerData;
	}

	public WorkAssissment getWorkAssissment() {
         WorkAssissment workAssissment=new WorkAssissment();
         workAssissment.setTyer_condition_lf(jsonObject.optString("tyre_condition_lf"));
         workAssissment.setTyer_condition_lb(jsonObject.optString("tyre_condition_lb"));
         workAssissment.setTyer_condition_rf(jsonObject.optString("tyre_condition_rf"));
         workAssissment.setTyer_condition_rb(jsonObject.optString("tyre_condition_rb"));

         workAssissment.setTyre_size_lf(jsonObject.optString("tyre_size_lf"));
         workAssissment.setTyre_size_lb(jsonObject.optString("tyre_size_lb"));

         workAssissment.setBrake_pad_lf(jsonObject.optString("brake_pad_lf"));
         workAssissment.setBrake_disk_lf(jsonObject.optString("brake_disk_lf"));

         workAssissment.setShocker_lf(jsonObject.optString("shocker_lf"));
         workAssissment.setWheel_lf(jsonObject.optString("wheel_lf"));

         workAssissment.setPhysical_damage_lf(jsonObject.optString("ph_damage_lf"));
         workAssissment.setPhysical_damage_rb(jsonObject.optString("ph_damage_rb"));
         workAssissment.setPhysical_damage_lb(jsonObject.optString("ph_damage_lb"));
         workAssissment.setPhysical_damage_rf(jsonObject.optString("ph_damage_rf"));
         workAssissment.setPhysical_damage_f(jsonObject.optString("ir_by_ph_damage"));
         workAssissment.setPhysical_damage_b(jsonObject.optString("sw_ln_ph_damage"));

         workAssissment.setBrake_pad_lb(jsonObject.optString("brake_pad_lb"));
         workAssissment.setBrake_disk_lb(jsonObject.optString("brake_disk_lb"));
         workAssissment.setShocker_lb(jsonObject.optString("shocker_lb"));
         workAssissment.setWheel_lb(jsonObject.optString("wheel_lb"));

         workAssissment.setTyre_size_rf(jsonObject.optString("tyre_size_rf"));
       //  workAssissment.settyjsonObject.optString("tyre_depth_rfx"));
      //   workAssissment.setjsonObject.optString("tyre_depth_rfy"));

         workAssissment.setBrake_pad_rf(jsonObject.optString("brake_pad_rf"));
         workAssissment.setBrake_disk_rf(jsonObject.optString("brake_disk_rf"));
         workAssissment.setShocker_rf(jsonObject.optString("shocker_rf"));
         workAssissment.setWheel_rf(jsonObject.optString("wheel_rf"));

         workAssissment.setTyre_size_rb(jsonObject.optString("tyre_size_rb"));
         //workAssissment.settyjsonObject.optString("tyre_depth_rbx"));
         //workAssissment.setjsonObject.optString("tyre_depth_rby"));
         workAssissment.setBrake_pad_rb(jsonObject.optString("brake_pad_rb"));
         workAssissment.setBrake_disk_rb(jsonObject.optString("brake_disk_rb"));
         workAssissment.setShocker_rb(jsonObject.optString("shocker_rb"));
         workAssissment.setWheel_rb(jsonObject.optString("wheel_rb"));

         workAssissment.setTyre_depth_lf(jsonObject.optString("tyre_depth_lf"));
         workAssissment.setTyre_depth_lb(jsonObject.optString("tyre_depth_lb"));
         workAssissment.setTyre_depth_rf(jsonObject.optString("tyre_depth_rf"));
         workAssissment.setTyre_depth_rb(jsonObject.optString("tyre_depth_rb"));

         workAssissment.setImmoblizer_f(jsonObject.optString("immoblizer"));
         workAssissment.setBattery_f(jsonObject.optString("battery"));

         workAssissment.setSpare_wheel_b(jsonObject.optString("spare_wheel"));
         workAssissment.setLock_nut_b(jsonObject.optString("lock_nut"));
         
		return workAssissment;
	}

	public JobData getJobdata() {
        JobData jobData=new JobData();
        
        jobData.setDealer_recommendations(jsonObject.optString("dealer_recommendations"));
        jobData.setCust_approved_work(jsonObject.optString("cust_approved_work"));
        jobData.setRadiodata(jsonObject.optString("radiodata"));
        jobData.setObservations(jsonObject.optString("observations"));
        jobData.setWheel_nuts_torqued(jsonObject.optString("wheel_nuts_torqued"));
        jobData.setWheels_cleaned(jsonObject.optString("wheels_cleaned"));
        jobData.setWheels_balanced(jsonObject.optString("wheels_balanced"));
        jobData.setAlignment_done(jsonObject.optString("alignment_done"));
        jobData.setTyre_pressure_front(jsonObject.optString("tyre_pressure_front"));
        jobData.setTyre_pressure_back(jsonObject.optString("tyre_pressure_back"));
        jobData.setTyres_polished(jsonObject.optString("tyres_polished"));
        jobData.setLock_nut_returned(jsonObject.optString("lock_nut_returned"));
        jobData.setCar_tested_by_salesperson(jsonObject.optString("car_tested_by_salesperson"));
        jobData.setWork_inspected_by_salesperson(jsonObject.optString("work_inspected_by_salesperson"));
        jobData.setWork_approved_by_salesperson(jsonObject.optString("work_approved_by_salesperson"));
        jobData.setCustomer_satisfied(jsonObject.optString("customer_satisfied"));
        jobData.setQuotation1(jsonObject.optString("quotation1"));
        jobData.setQuotation2(jsonObject.optString("quotation2"));
        jobData.setDiplay(jsonObject.optString("display"));
		
		return jobData;
	}

}
