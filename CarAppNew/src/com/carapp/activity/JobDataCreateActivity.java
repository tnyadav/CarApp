package com.carapp.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.JobData;
import com.carapp.server.CreatePdf;
import com.carapp.server.UpdateDataBase;
import com.carapp.server.UploadFile;
import com.carapp.util.PdfInfo;
import com.carapp.util.PdfInfo.status;
import com.example.carappnew.R;

public class JobDataCreateActivity extends Activity {

	private Button /*btcust_reson_for_visit*/ btdealer_recomendation,
			btcust_approved_work;

	private Button btsubmit, customer_signature, saleperson_signature,
			attendall;
	private ImageView img_cust, img_sal;
	private LinearLayout /*layoutlinear_cust_reson_for_visit*/
			layoutlinear_deaer_recomendation, layoutlinear_cust_approved_work;
	private RadioGroup rbgradioButtongroup,radioGroupValuablesRemovedFromVehicle;

	private EditText Quotation1, Quotation2,observations;

	//private ArrayList<String> datacust_reson_for_visit; 
	private ArrayList<String> datadealer_recomendation;
	private ArrayList<String> datacust_approved_work;

	private boolean isCustomerSignaturedraw;
	private boolean isSalePersonSignaturedraw;
    private String entryInList;
	private String radiogrouptext;

	Context context;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_data_create);
		context = this;
		((CarAppSession) getApplication()).setCurrentUploadFileStatus(status.FILESUPLOADED);
		radiogrouptext = "";
		isCustomerSignaturedraw = false;
		isSalePersonSignaturedraw = false;
	//	datacust_reson_for_visit = new ArrayList<String>();
		datadealer_recomendation = new ArrayList<String>();
		datacust_approved_work = new ArrayList<String>();
		setcontent();
		
	}

	private void setcontent() {
	/*	btcust_reson_for_visit = (Button) findViewById(R.id.cust_reson_for_visit);
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
		
		customer_signature = (Button) findViewById(R.id.customer_sig);
		customer_signature.setOnClickListener(listener);

		saleperson_signature = (Button) findViewById(R.id.saleperson_sig);
		saleperson_signature.setOnClickListener(listener);
		img_cust = (ImageView) findViewById(R.id.image_cust);
		img_sal = (ImageView) findViewById(R.id.image_sale);

		/*layoutlinear_cust_reson_for_visit = (LinearLayout) findViewById(R.id.linear_cust_reson_for_visit);
		*/layoutlinear_deaer_recomendation = (LinearLayout) findViewById(R.id.linear_deaer_recomendation);
		layoutlinear_cust_approved_work = (LinearLayout) findViewById(R.id.linear_cust_approved_work);

		rbgradioButtongroup = (RadioGroup) findViewById(R.id.radioGroup1);
	
		rbgradioButtongroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						switch (checkedId) {
						case R.id.radio0:
							radiogrouptext = "Work Declined";
                            reset();
							break;
						case R.id.radio1:
							radiogrouptext = "No Work Required";
							reset();
							break;
						case R.id.radio2:
							radiogrouptext = "Unable to Assist";
							reset();
							break;

						default:
							break;
						}
					}
				});
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
		/*
		 * creating a job card in db and in CarAPP list or NOT in CarAPP list of existing job cards
		 * enable radio group according to condition
		 * 
        */
        radioGroupDisable();
		
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
			case R.id.customer_sig:
				Intent i111 = new Intent(getApplicationContext(),
						CaptureSignatureActivity.class);
				i111.putExtra("For", "customer");
				startActivityForResult(i111, 1004);
				break;
			case R.id.saleperson_sig:
				Intent i1111 = new Intent(getApplicationContext(),
						CaptureSignatureActivity.class);
				i1111.putExtra("For", "saleperson");
				startActivityForResult(i1111, 1005);
				break;

			case R.id.submit:
				
				status currentStatus = ((CarAppSession) getApplication()).getCurrentUploadFileStatus();
				Log.e("status", ""+currentStatus);
				Toast.makeText(context, ""+currentStatus, 1).show();
				
				switch (currentStatus) {
				case PDFCREATED:
					new UpdateDataBase(context, "storeindb",
							((CarAppSession) getApplication())).execute("");
					break;
				case DATABASEUPDATED:
					new UploadFile(context, PdfInfo.path,
							((CarAppSession) getApplication())).execute("");
					break;
				case FILESUPLOADED:
					if (Quotation1.getText().length() < 1) {

						Toast.makeText(context, "Fill  quotation fields",
								Toast.LENGTH_LONG).show();
						return;
					} 
					else if (entryInList.equals("yes") &&  Quotation2.getText().length() < 1) {
						Toast.makeText(context, "Fill  quotation fields",
								Toast.LENGTH_LONG).show();
						return;
					
						
					}
					else if (!isCustomerSignaturedraw || !isSalePersonSignaturedraw)
						
					{
						
						Toast.makeText(context, "Draw Signature", Toast.LENGTH_LONG)
						.show();
						return;
					} else if (isCustomerSignaturedraw && isSalePersonSignaturedraw) {
						
					JobData	jobData=new JobData();
							jobData.setCust_approved_work(TextUtils.join(",",datacust_approved_work)); 
							jobData.setDealer_recommendations(TextUtils.join(",", datadealer_recomendation));
							jobData.setRadiodata(radiogrouptext);
							jobData.setQuotation1(Quotation1.getText().toString());
							jobData.setQuotation2(Quotation2.getText().toString());
							jobData.setObservations(observations.getText().toString());
							jobData.setDiplay(entryInList);
							Log.e("jobData", ""+jobData);
							((CarAppSession)getApplication()).setJobData(jobData);
							Toast.makeText(context, "done", 3).show();
							
							
							new CreatePdf(context, "storeindb",((CarAppSession)getApplication())).execute("");
							
						

					}

					break;
				}
				
				break;
			case R.id.attend_All:
				if (datadealer_recomendation.size() > 0) {
					for (int j = 0; j < datadealer_recomendation.size(); j++) {
						if (!isStringAdded(datadealer_recomendation.get(j),
								datacust_approved_work)) {

							inflateEditRow(datadealer_recomendation.get(j),
									layoutlinear_cust_approved_work,
									datacust_approved_work,1);
						//	datacust_approved_work.add(datadealer_recomendation.get(j));
							

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
	/*	if (requestCode == 1001 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");
			if (!isStringAdded(value, datacust_reson_for_visit)) {
				//datacust_reson_for_visit.add(value);
				inflateEditRow(value, layoutlinear_cust_reson_for_visit,
						datacust_reson_for_visit,0);
			} else {
				Toast.makeText(context, "Allready Added", Toast.LENGTH_SHORT)
						.show();
			}

		} else */if (requestCode == 1002 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");
			if (!isStringAdded(value, datadealer_recomendation)) {
				//datadealer_recomendation.add(value);
				inflateEditRow(value, layoutlinear_deaer_recomendation,
						datadealer_recomendation,0);
			} else {
				Toast.makeText(context, "Allready Added", Toast.LENGTH_SHORT)
						.show();
			}

		} else if (requestCode == 1003 && resultCode == RESULT_OK) {

			value = data.getStringExtra("position");

				if (!isStringAdded(value, datacust_approved_work)) {

					inflateEditRow(value, layoutlinear_cust_approved_work,
							datacust_approved_work,1);
					//datacust_approved_work.add(value);

					

				} else {
					Toast.makeText(context, "Allready Added",
							Toast.LENGTH_SHORT).show();
				}
			

		} else if (requestCode == 1004 && resultCode == RESULT_OK) {
			File imgFile = new File(PdfInfo.path + "PhotoCustSIG.jpeg");
			if (imgFile.exists()) {

				try {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					Bitmap b = Bitmap.createScaledBitmap(myBitmap, 100, 100,
							true);
					img_cust.setImageBitmap(b);

					isCustomerSignaturedraw = true;
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			}

		} else if (requestCode == 1005 && resultCode == RESULT_OK) {

			File imgFile = new File(PdfInfo.path + "PhotoSaleSIG.jpeg");
			if (imgFile.exists()) {

				try {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					Bitmap b = Bitmap.createScaledBitmap(myBitmap, 100, 100,
							true);
					img_sal.setImageBitmap(b);
					isSalePersonSignaturedraw = true;
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			}
		}
	}

	private void inflateEditRow(final String value, final LinearLayout layout,
			final ArrayList<String> list,int which) {
		if (which==1) {
			radioGroupEnable();	
		}
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
				if (datacust_approved_work.size() < 1
						&& Quotation2.getText().toString().length() == 0) {
                radioGroupDisable();
					
				}

			}
		});

		// Inflate at the end of all rows but before the "Add new" button
		
		layout.addView(rowView, layout.getChildCount() - 1);
		list.add(value);
		//Toast.makeText(context,value+" size: "+layout.getChildCount(), 1).show();
		
		
	}

	private void radioGroupEnable() {
		entryInList="yes";
		Quotation2.setEnabled(true);
	   
		for (int i = 0; i < radioGroupValuablesRemovedFromVehicle.getChildCount(); i++) {
			
			radioGroupValuablesRemovedFromVehicle.getChildAt(i).setEnabled(true);
		
		}
		for (int i = 0; i < rbgradioButtongroup.getChildCount(); i++) {
			rbgradioButtongroup.clearCheck();

		}
	}

	private void radioGroupDisable() {
		
		
		//***
		    entryInList="no";
			Quotation2.setText("");
			Quotation2.setEnabled(false);
			
		
		for (int i = 0; i < radioGroupValuablesRemovedFromVehicle.getChildCount(); i++) {
			radioGroupValuablesRemovedFromVehicle.getChildAt(i).setEnabled(false);
			radioGroupValuablesRemovedFromVehicle.clearCheck();

		}
		
		
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
	private void reset() {
		   
		layoutlinear_cust_approved_work.removeViews(0,layoutlinear_cust_approved_work.getChildCount() - 1);
		datacust_approved_work.clear();
		radioGroupDisable();
			
			
	}
}
