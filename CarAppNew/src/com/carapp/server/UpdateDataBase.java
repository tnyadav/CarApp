package com.carapp.server;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.CustomerData;
import com.carapp.bean.JobData;
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


	public UpdateDataBase(Context context, String action,CarAppSession carAppSession) {
		this.context = context;
		this.action = action;
		this.carAppSession=carAppSession;
		customerData=this.carAppSession.getCustomerData();
		workAssissment=this.carAppSession.getWorkAssissment();
		jobData=this.carAppSession.getJobData();

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
		MultipartEntity paramList = new MultipartEntity();
		paramList.addPart("action",new StringBody( action));
		Log.e("action of updatedatabase", action);
		paramList.addPart("branch",new StringBody( customerData.getBranch()));
		paramList.addPart("salesperson",new StringBody(customerData.getSaleperson()));
		paramList.addPart("customer",new StringBody(customerData.getCustomer()));
		paramList.addPart("contact",new StringBody(customerData.getContactNo()));
		paramList.addPart("address",new StringBody(customerData.getAddress()));
		paramList.addPart("make",new StringBody( customerData.getMake()));
		paramList.addPart("model",new StringBody( customerData.getModel()));
		paramList.addPart("year",new StringBody( customerData.getYear()));
		paramList.addPart("odometer",new StringBody(customerData.getOdomstrer()));
		paramList.addPart("reg_plate_no",new StringBody(customerData.getRegistration()));
		paramList.addPart("date",new StringBody( customerData.getDate()));
		paramList.addPart("time",new StringBody( customerData.getTime()));
        
		paramList.addPart("company",new StringBody( customerData.getCompany()));
		paramList.addPart("email",new StringBody( customerData.getEmail()));
		
		paramList.addPart("cust_visit_reason",new StringBody(customerData.getCust_resonfor_visit()));
		
		

		paramList.addPart("tyre_condition_lf",new StringBody(workAssissment.getTyer_condition_lf()));
		paramList.addPart("tyre_condition_lb",new StringBody(workAssissment.getTyer_condition_lb()));
		paramList.addPart("tyre_condition_rf",new StringBody(workAssissment.getTyer_condition_rf()));
		paramList.addPart("tyre_condition_rb",new StringBody(workAssissment.getTyer_condition_rb()));

		paramList.addPart("tyre_size_lf",new StringBody(workAssissment.getTyre_size_lf()));
		paramList.addPart("tyre_size_lb",new StringBody(workAssissment.getTyre_size_lb()));
		paramList.addPart("tyre_size_rf",new StringBody(workAssissment.getTyre_size_rf()));
		paramList.addPart("tyre_size_rb",new StringBody(workAssissment.getTyre_size_rb()));

		paramList.addPart("tyre_depth_lf",new StringBody(workAssissment.getTyre_depth_lf()));
		paramList.addPart("tyre_depth_lb",new StringBody(workAssissment.getTyre_depth_lb()));
		paramList.addPart("tyre_depth_rf",new StringBody(workAssissment.getTyre_depth_rf()));
		paramList.addPart("tyre_depth_rb",new StringBody(workAssissment.getTyre_depth_rb()));

		paramList.addPart("brake_pad_lf",new StringBody(workAssissment.getBrake_pad_lf()));
		paramList.addPart("brake_pad_lb",new StringBody(workAssissment.getBrake_pad_lb()));
		paramList.addPart("brake_pad_rf",new StringBody(workAssissment.getBrake_pad_rf()));
		paramList.addPart("brake_pad_rb",new StringBody(workAssissment.getBrake_pad_rb()));

		paramList.addPart("brake_disk_lf",new StringBody(workAssissment.getBrake_disk_lf()));
		paramList.addPart("brake_disk_lb",new StringBody(workAssissment.getBrake_disk_lb()));
		paramList.addPart("brake_disk_rf",new StringBody(workAssissment.getBrake_disk_rf()));
		paramList.addPart("brake_disk_rb",new StringBody(workAssissment.getBrake_disk_rb()));

		paramList.addPart("shocker_lf",new StringBody(workAssissment.getShocker_lf()));
		paramList.addPart("shocker_lb",new StringBody(workAssissment.getShocker_lb()));
		paramList.addPart("shocker_rf",new StringBody(workAssissment.getShocker_rf()));
		paramList.addPart("shocker_rb",new StringBody(workAssissment.getShocker_rb()));

		paramList.addPart("wheel_lf",new StringBody(workAssissment.getWheel_lf()));
		paramList.addPart("wheel_lb",new StringBody(workAssissment.getWheel_lb()));
		paramList.addPart("wheel_rf",new StringBody(workAssissment.getWheel_rf()));
		paramList.addPart("wheel_rb",new StringBody(workAssissment.getWheel_rb()));

		paramList.addPart("immoblizer",new StringBody(workAssissment.getImmoblizer_f()));
		paramList.addPart("battery",new StringBody(workAssissment.getBattery_f()));

		paramList.addPart("spare_wheel",new StringBody(workAssissment.getSpare_wheel_b()));
		paramList.addPart("lock_nut",new StringBody(workAssissment.getLock_nut_b()));

		paramList.addPart("ph_damage_lf",new StringBody(workAssissment.getPhysical_damage_lf()));
		paramList.addPart("ph_damage_lb",new StringBody(workAssissment.getPhysical_damage_lb()));
		paramList.addPart("ph_damage_rf",new StringBody(workAssissment.getPhysical_damage_rf()));
		paramList.addPart("ph_damage_rb",new StringBody(workAssissment.getPhysical_damage_rb()));
		paramList.addPart("ir_by_ph_damage",new StringBody(workAssissment.getPhysical_damage_f()));
		paramList.addPart("sw_ln_ph_damage",new StringBody(workAssissment.getPhysical_damage_b()));

		// ***********************************************************************
		paramList.addPart("display",new StringBody(jobData.getDiplay()));
		//************************************************************************
		paramList.addPart("dealer_recommendations",new StringBody(jobData.getDealer_recommendations()));
		paramList.addPart("cust_approved_work",new StringBody(jobData.getCust_approved_work()));

		paramList.addPart("radiodata",new StringBody(jobData.getRadiodata()));

		paramList.addPart("quotation1",new StringBody(jobData.getQuotation1()));
		paramList.addPart("quotation2",new StringBody(jobData.getQuotation2()));
    
	    paramList.addPart("csd_id",new StringBody(PdfInfo.csdId));
	    paramList.addPart("observations",new StringBody(jobData.getObservations()));
		
		
		if (PdfInfo.mode==PdfInfo.EXIT_MODE) {
			
			
			paramList.addPart("wheel_nuts_torqued",new StringBody(jobData.getWheel_nuts_torqued()));
			paramList.addPart("wheels_cleaned",new StringBody(jobData.getWheels_cleaned()));
			paramList.addPart("wheels_balanced",new StringBody(jobData.getWheels_balanced()));
			paramList.addPart("alignment_done",new StringBody(jobData.getAlignment_done()));
			paramList.addPart("tyre_pressure_front",new StringBody(jobData.getTyre_pressure_front()));
			paramList.addPart("tyre_pressure_back",new StringBody(jobData.getTyre_pressure_back()));
			paramList.addPart("tyres_polished",new StringBody(jobData.getTyres_polished()));
			paramList.addPart("lock_nut_returned",new StringBody(jobData.getLock_nut_returned()));
			paramList.addPart("car_tested_by_salesperson",new StringBody(jobData.getCar_tested_by_salesperson()));
			paramList.addPart("work_inspected_by_salesperson",new StringBody(jobData.getWork_inspected_by_salesperson()));
			paramList.addPart("work_approved_by_salesperson",new StringBody(jobData.getWork_approved_by_salesperson()));
			paramList.addPart("customer_satisfied",new StringBody(jobData.getCustomer_satisfied()));

		}
		
		
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(PdfInfo.newjobcard);

			httppost.setEntity(paramList);
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
