package com.carapp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.JobData;
import com.carapp.server.CreatePdf;
import com.carapp.server.UpdateDataBase;
import com.carapp.server.UploadFile;
import com.carapp.util.JobDataDetail;
import com.carapp.util.PdfInfo;
import com.carapp.util.PdfInfo.status;
import com.example.carappnew.R;

public class JobDataEditActivity extends Activity {

	private Button btdealer_recomendation,
			btcust_approved_work;

	private Button btsubmit,attendall;
	private LinearLayout /*layoutlinear_cust_reson_for_visit*/
			layoutlinear_deaer_recomendation, layoutlinear_cust_approved_work;
	private RadioGroup radioGroupValuablesRemovedFromVehicle;

	private EditText Quotation1, Quotation2,observations;

	private ArrayList<String> datadealer_recomendation;
	private ArrayList<String> datacust_approved_work;
  	private JobData jobData; 
	

	private String radiogrouptext;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_data_edit);
		context = this;
		((CarAppSession) getApplication()).setCurrentUploadFileStatus(status.FILESUPLOADED);
		this.jobData=((CarAppSession)getApplication()).getJobData();
		radiogrouptext = "";
		new ArrayList<String>();
		datadealer_recomendation = new ArrayList<String>();
		datacust_approved_work = new ArrayList<String>();
		setcontent();
	}

	private void setcontent() {
		Log.e("jobData", ""+jobData);
		/*btcust_reson_for_visit = (Button) findViewById(R.id.cust_reson_for_visit);
		btcust_reson_for_visit.setOnClickListener(listener);*/
		btdealer_recomendation = (Button) findViewById(R.id.deaer_recomendation);
		btdealer_recomendation.setOnClickListener(listener);
		btcust_approved_work = (Button) findViewById(R.id.cust_approved_work);
		btcust_approved_work.setOnClickListener(listener);

		btsubmit = (Button) findViewById(R.id.submit);
		btsubmit.setOnClickListener(listener);
		attendall = (Button) findViewById(R.id.attend_All);
		attendall.setOnClickListener(listener);
		observations = (EditText) findViewById(R.id.et_observations);
		
		Quotation1 = (EditText) findViewById(R.id.quotation1);
		Quotation2 = (EditText) findViewById(R.id.quotation2);
		

		//layoutlinear_cust_reson_for_visit = (LinearLayout) findViewById(R.id.linear_cust_reson_for_visit);
		layoutlinear_deaer_recomendation = (LinearLayout) findViewById(R.id.linear_deaer_recomendation);
		layoutlinear_cust_approved_work = (LinearLayout) findViewById(R.id.linear_cust_approved_work);


        radioGroupValuablesRemovedFromVehicle=(RadioGroup)findViewById(R.id.radioGroupValuablesRemovedFromVehicle);
        
        radioGroupValuablesRemovedFromVehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			
				switch (checkedId) {
				case R.id.radioGroupValuablesRemovedFromVehicleYes:
					radiogrouptext="Yes";
					break;
				case R.id.radioGroupValuablesRemovedFromVehicleNo:
					radiogrouptext="No";
					break;

				default:
					break;
				}
			}
		});
			
			setValue();
			
		
		
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.cust_reson_for_visit:

				Intent i = new Intent(getApplicationContext(),
						CustResonForVisitList.class);
				i.putExtra("listname", 0);
				startActivityForResult(i, 1001);
				break;
			case R.id.deaer_recomendation:
				Intent i1 = new Intent(getApplicationContext(),
						CustResonForVisitList.class);
				i1.putExtra("listname", 1);
				startActivityForResult(i1, 1002);
				break;

			case R.id.cust_approved_work:
				Intent i11 = new Intent(getApplicationContext(),
						CustResonForVisitList.class);
				i11.putExtra("listname", 2);
				startActivityForResult(i11, 1003);
				break;
			

			case R.id.submit:
				
				
				
				
				 if (Quotation1.getText().length() < 1
						|| Quotation2.getText().length() < 1) {
					Toast.makeText(context, "Fill all quotation fields",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					
					status currentStatus = ((CarAppSession) getApplication()).getCurrentUploadFileStatus();
					Log.e("status", ""+currentStatus);
					Toast.makeText(context, ""+currentStatus, 1).show();
					
					switch (currentStatus) {
					case PDFCREATED:
						new UpdateDataBase(context, "edit_csd",
								((CarAppSession) getApplication())).execute("");
						break;
					case DATABASEUPDATED:
						new UploadFile(context, PdfInfo.path,
								((CarAppSession) getApplication())).execute("");
						break;
					case FILESUPLOADED:
						
					jobData.setCust_approved_work(TextUtils.join(",",datacust_approved_work)); 
					jobData.setDealer_recommendations(TextUtils.join(",", datadealer_recomendation));
					jobData.setRadiodata(radiogrouptext);
					jobData.setQuotation1(Quotation1.getText().toString());
					jobData.setQuotation2(Quotation2.getText().toString());
					jobData.setObservations(observations.getText().toString());
					((CarAppSession)getApplication()).setJobData(jobData);
					
						
						new CreatePdf(context, "edit_csd",((CarAppSession)getApplication())).execute("");
						
					}
				}

				break;
			case R.id.attend_All:
				if (datadealer_recomendation.size() > 0) {
					for (int j = 0; j < datadealer_recomendation.size(); j++) {
						if (!isStringAdded(datadealer_recomendation.get(j),
								datacust_approved_work)) {

							inflateEditRow(datadealer_recomendation.get(j),
									layoutlinear_cust_approved_work,
									datacust_approved_work);
							datacust_approved_work.add(datadealer_recomendation
									.get(j));
						}

					}
				}

				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
		String value;
		/*if (requestCode == 1001 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");
			if (!isStringAdded(value, datacust_reson_for_visit)) {
				datacust_reson_for_visit.add(value);
				inflateEditRow(value, layoutlinear_cust_reson_for_visit,
						datacust_reson_for_visit);
			} else {
				Toast.makeText(context, "Allready Added", Toast.LENGTH_SHORT)
						.show();
			}

		} else*/ if (requestCode == 1002 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");
			if (!isStringAdded(value, datadealer_recomendation)) {
				datadealer_recomendation.add(value);
				inflateEditRow(value, layoutlinear_deaer_recomendation,
						datadealer_recomendation);
			} else {
				Toast.makeText(context, "Allready Added", Toast.LENGTH_SHORT)
						.show();
			}

		} else if (requestCode == 1003 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");

			if (value.equals("all")) {
				layoutlinear_cust_approved_work.removeViews(0,
						layoutlinear_cust_approved_work.getChildCount() - 1);
				datacust_approved_work.clear();
				for (int i = 0; i < JobDataDetail.getCustApprovedWork().size(); i++) {
					datacust_approved_work.add(JobDataDetail
							.getCustApprovedWork().get(i));
					inflateEditRow(JobDataDetail.getCustApprovedWork().get(i),
							layoutlinear_cust_approved_work,
							datacust_approved_work);
				}

			} else {
				if (!isStringAdded(value, datacust_approved_work)) {

					inflateEditRow(value, layoutlinear_cust_approved_work,
							datacust_approved_work);
					datacust_approved_work.add(value);

					

				} else {
					Toast.makeText(context, "Allready Added",
							Toast.LENGTH_SHORT).show();
				}
			}

		
		} 
	}

	private void inflateEditRow(final String value, final LinearLayout layout,
			final ArrayList<String> list) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.list_item_1, null);

		final TextView textvalue = (TextView) rowView
				.findViewById(R.id.list_item_1_textview);
		textvalue.setText(value);
		final Button delete = (Button) rowView
				.findViewById(R.id.list_item_1_delete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					removeFromlist(value, list);
				layout.removeView(rowView);
				

			}
		});

		// Inflate at the end of all rows but before the "Add new" button
		layout.addView(rowView, layout.getChildCount() - 1);
	}


		
		
	

	@Override
	protected void onDestroy() {
	
		super.onDestroy();
		Log.i("jobdata", "onDestroy");

		
		
	}

	private boolean isStringAdded(String value, ArrayList<String> list) {
		boolean added = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(value)) {

				added = true;
			}

		}
		return added;

	}

	private void removeFromlist(String value, ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(value)) {

				list.remove(i);
			}

		}
	}

	@Override
	protected void onResume() {
		
		super.onResume();

	}
	private void setValue() {

		String[] tempData;

		tempData = jobData.getCust_approved_work().split(",");
		Log.e("lenghths", tempData.length + " ");
		for (int i = 0; i < tempData.length; i++) {

			if (!isStringAdded(tempData[i], datacust_approved_work)) {

				datacust_approved_work.add(tempData[i]);
				inflateEditRow(tempData[i],
						layoutlinear_cust_approved_work, datacust_approved_work);
			

			}

		}
		
	/*	tempData = jobData.getCust_resonfor_visit().split(",");
		Log.e("lenghths", tempData.length + " ");
		for (int i = 0; i < tempData.length; i++) {

			if (!isStringAdded(tempData[i], datacust_reson_for_visit)) {

				datacust_reson_for_visit.add(tempData[i]);
				inflateEditRow(tempData[i],
						layoutlinear_cust_reson_for_visit, datacust_reson_for_visit);
				

			}

		}*/
		
		tempData = jobData.getDealer_recommendations().split(",");
		Log.e("lenghths", tempData.length + " ");
		for (int i = 0; i < tempData.length; i++) {

			if (!isStringAdded(tempData[i], datadealer_recomendation)) {

				datacust_approved_work.add(tempData[i]);
				inflateEditRow(tempData[i],
						layoutlinear_deaer_recomendation, datadealer_recomendation);
				

			}

		}
  Quotation1.setText(jobData.getQuotation1());
  Quotation2.setText(jobData.getQuotation2());
  observations.setText(jobData.getObservations());
  RadioButton radioGroupValuablesRemovedFromVehicleYes=(RadioButton)findViewById(R.id.radioGroupValuablesRemovedFromVehicleYes);
  RadioButton radioGroupValuablesRemovedFromVehicleNo=(RadioButton)findViewById(R.id.radioGroupValuablesRemovedFromVehicleNo);
  if (jobData.getRadiodata().equals("Yes")) {
	  radioGroupValuablesRemovedFromVehicleYes.setChecked(true);
	  radiogrouptext="Yes";
   } else {
   radioGroupValuablesRemovedFromVehicleNo.setChecked(true);
   radiogrouptext="No";
}


	}
}
