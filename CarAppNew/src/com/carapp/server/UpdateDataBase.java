package com.carapp.server;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.CustomerData;
import com.carapp.bean.JobData;
import com.carapp.bean.PartAssisment;
import com.carapp.bean.WorkAssissment;
import com.carapp.util.PdfInfo;
import com.example.carappnew.R;
import com.example.tnutil.Util;

public class UpdateDataBase extends AsyncTask<String, Integer, String> {

	Context context;
	private Dialog dialog ;
	private String action;
	private CarAppSession carAppSession;
	private CustomerData customerData;
	private WorkAssissment workAssissment;
	private JobData jobData;
	 private List<PartAssisment> partAssisments;


	public UpdateDataBase(Context context, String action,CarAppSession carAppSession) {
		this.context = context;
		this.action = action;
		this.carAppSession=carAppSession;
		customerData=this.carAppSession.getCustomerData();
		workAssissment=this.carAppSession.getWorkAssissment();
		jobData=this.carAppSession.getJobData();
		partAssisments=this.carAppSession.getPartAssisments();
	}

	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();

		dialog = new Dialog(context,
				android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
		window.getAttributes().windowAnimations = R.style.PauseDialogAnimation;
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		WindowManager.LayoutParams wlp = window.getAttributes();
    	wlp.gravity = Gravity.CENTER;
    	window.setAttributes(wlp);
    	
		dialog.setContentView(R.layout.custom);
		TextView text = (TextView) dialog.findViewById(R.id.title);
		text.setText("Updating database...\n Please wait...");
		dialog.show();
		
		Log.e("customerData", ""+customerData);
		Log.e("workAssissment", ""+workAssissment);
		Log.e("jobData", ""+jobData);

	}

	@Override
	protected String doInBackground(String... params) {
		String responseData="";
		//List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		try {
			 JSONObject paramList = new JSONObject();
			
		
		//MultipartEntity paramList = new MultipartEntity();
		
		paramList.put("action", action);
		Log.e("action of updatedatabase", action);
		paramList.put("branch", customerData.getBranch());
		paramList.put("salesperson",customerData.getSaleperson());
		paramList.put("customer",customerData.getCustomer());
		paramList.put("contact",customerData.getContactNo());
		paramList.put("address",customerData.getAddress());
		paramList.put("make", customerData.getMake());
		paramList.put("model", customerData.getModel());
		paramList.put("year", customerData.getYear());
		paramList.put("odometer",customerData.getOdomstrer());
		paramList.put("reg_plate_no",customerData.getRegistration());
		paramList.put("date", customerData.getDate());
		paramList.put("time", customerData.getTime());
        
		paramList.put("company", customerData.getCompany());
		paramList.put("email", customerData.getEmail());
		
		paramList.put("cust_visit_reason",customerData.getCust_resonfor_visit());
		paramList.put("fleet_selected",customerData.getFleetSelection());
		paramList.put("auth_number",customerData.getAuthNumber());
		String parts_assessment="";
		if (partAssisments!=null) {
			
			 parts_assessment= TextUtils.join(",",partAssisments);
		}
		
		paramList.put("parts_assessment",parts_assessment);
		

		paramList.put("tyre_condition_lf",workAssissment.getTyer_condition_lf());
		paramList.put("tyre_condition_lb",workAssissment.getTyer_condition_lb());
		paramList.put("tyre_condition_rf",workAssissment.getTyer_condition_rf());
		paramList.put("tyre_condition_rb",workAssissment.getTyer_condition_rb());

		paramList.put("tyre_size_lf",workAssissment.getTyre_size_lf());
		paramList.put("tyre_size_lb",workAssissment.getTyre_size_lb());
		paramList.put("tyre_size_rf",workAssissment.getTyre_size_rf());
		paramList.put("tyre_size_rb",workAssissment.getTyre_size_rb());

		paramList.put("tyre_depth_lf",workAssissment.getTyre_depth_lf());
		paramList.put("tyre_depth_lb",workAssissment.getTyre_depth_lb());
		paramList.put("tyre_depth_rf",workAssissment.getTyre_depth_rf());
		paramList.put("tyre_depth_rb",workAssissment.getTyre_depth_rb());

		paramList.put("brake_pad_lf",workAssissment.getBrake_pad_lf());
		paramList.put("brake_pad_lb",workAssissment.getBrake_pad_lb());
		paramList.put("brake_pad_rf",workAssissment.getBrake_pad_rf());
		paramList.put("brake_pad_rb",workAssissment.getBrake_pad_rb());

		paramList.put("brake_disk_lf",workAssissment.getBrake_disk_lf());
		paramList.put("brake_disk_lb",workAssissment.getBrake_disk_lb());
		paramList.put("brake_disk_rf",workAssissment.getBrake_disk_rf());
		paramList.put("brake_disk_rb",workAssissment.getBrake_disk_rb());

		paramList.put("shocker_lf",workAssissment.getShocker_lf());
		paramList.put("shocker_lb",workAssissment.getShocker_lb());
		paramList.put("shocker_rf",workAssissment.getShocker_rf());
		paramList.put("shocker_rb",workAssissment.getShocker_rb());

		paramList.put("wheel_lf",workAssissment.getWheel_lf());
		paramList.put("wheel_lb",workAssissment.getWheel_lb());
		paramList.put("wheel_rf",workAssissment.getWheel_rf());
		paramList.put("wheel_rb",workAssissment.getWheel_rb());

		paramList.put("immoblizer",workAssissment.getImmoblizer_f());
		paramList.put("battery",workAssissment.getBattery_f());

		paramList.put("spare_wheel",workAssissment.getSpare_wheel_b());
		paramList.put("lock_nut",workAssissment.getLock_nut_b());

		paramList.put("ph_damage_lf",workAssissment.getPhysical_damage_lf());
		paramList.put("ph_damage_lb",workAssissment.getPhysical_damage_lb());
		paramList.put("ph_damage_rf",workAssissment.getPhysical_damage_rf());
		paramList.put("ph_damage_rb",workAssissment.getPhysical_damage_rb());
		paramList.put("ir_by_ph_damage",workAssissment.getPhysical_damage_f());
		paramList.put("sw_ln_ph_damage",workAssissment.getPhysical_damage_b());

		// ***********************************************************************
		paramList.put("display",jobData.getDiplay());
		//************************************************************************
		paramList.put("dealer_recommendations",jobData.getDealer_recommendations());
		paramList.put("cust_approved_work",jobData.getCust_approved_work());

		paramList.put("radiodata",jobData.getRadiodata());

		paramList.put("quotation1",jobData.getQuotation1());
		paramList.put("quotation2",jobData.getQuotation2());
    
	    paramList.put("csd_id",PdfInfo.csdId);
	    paramList.put("observations",jobData.getObservations());
		
		
		if (PdfInfo.mode==PdfInfo.EXIT_MODE) {
			
			
			paramList.put("wheel_nuts_torqued",jobData.getWheel_nuts_torqued());
			paramList.put("wheels_cleaned",jobData.getWheels_cleaned());
			paramList.put("wheels_balanced",jobData.getWheels_balanced());
			paramList.put("alignment_done",jobData.getAlignment_done());
			paramList.put("tyre_pressure_front",jobData.getTyre_pressure_front());
			paramList.put("tyre_pressure_back",jobData.getTyre_pressure_back());
			paramList.put("tyres_polished",jobData.getTyres_polished());
			paramList.put("lock_nut_returned",jobData.getLock_nut_returned());
			paramList.put("car_tested_by_salesperson",jobData.getCar_tested_by_salesperson());
			paramList.put("work_inspected_by_salesperson",jobData.getWork_inspected_by_salesperson());
			paramList.put("work_approved_by_salesperson",jobData.getWork_approved_by_salesperson());
			paramList.put("customer_satisfied",jobData.getCustomer_satisfied());

		}
		
		
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(PdfInfo.newjobcard);
			StringEntity se = new StringEntity(paramList.toString());
			httppost.setEntity(se);
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity getresponse = response.getEntity();

			responseData = EntityUtils.toString(getresponse);
			Log.e("responseData", responseData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return responseData; 
	}

	@Override
	protected void onPostExecute(String result) {
		
		super.onPostExecute(result);
		
		dialog.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		try {
			JSONObject jsonObject=new JSONObject(result);
			if (jsonObject.optString("msg").equals("success")) {
				/*Util.showCustomDialog(context, "Message", "Record updated successfully. Upload all file to server ? ", new Callback1() {
					
					@Override
					public void ok() {*/
				carAppSession.setCurrentUploadFileStatus(PdfInfo.status.DATABASEUPDATED);
						new UploadFile(context, PdfInfo.path,carAppSession).execute("");
				/*	}
				});*/
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
			Util.showCustomDialog(context, "Error", "Server not responds. Please try again later" );
		}


	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		
		super.onProgressUpdate(values);
	

	}

}
