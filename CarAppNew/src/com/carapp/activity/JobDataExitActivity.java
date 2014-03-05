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

public class JobDataExitActivity extends Activity {

	private Button btcust_approved_work;

	private Button btsubmit, customer_signature, saleperson_signature;
	private ImageView img_cust, img_sal;
	private LinearLayout layoutlinear_cust_approved_work;

	private RadioGroup wheels_cleaned, wheels_balanced, alignment_done,
			tyres_polished, lock_nut_returned, car_tested_by_salesperson,
			work_inspected_by_salesperson, work_approved_by_salesperson,
			customer_satisfied;

	private RadioButton unableToAssist;
	private EditText Quotation2, observations, wheel_nuts_torqued,
			tyre_pressure_front, tyre_pressure_back;
	
	private boolean isCustomerSignaturedraw = false;;
	private boolean isSalePersonSignaturedraw = false;
	private JobData jobData;
	private ArrayList<String> datacust_approved_work;
    private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_data_exit);
		datacust_approved_work = new ArrayList<String>();
		context = this;
		((CarAppSession) getApplication()).setCurrentUploadFileStatus(status.FILESUPLOADED);
		jobData=((CarAppSession)getApplication()).getJobData();
		Log.e("jobData", ""+jobData);
		setcontent();
		setValue();
		
	}
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	private void setcontent() {

		btcust_approved_work = (Button) findViewById(R.id.cust_approved_work);
		btcust_approved_work.setOnClickListener(listener);

		btsubmit = (Button) findViewById(R.id.submit);
		btsubmit.setOnClickListener(listener);

		Quotation2 = (EditText) findViewById(R.id.quotation2);
		

		observations = (EditText) findViewById(R.id.et_observations);
		wheel_nuts_torqued = (EditText) findViewById(R.id.et_wheelnutstorqued);
		tyre_pressure_front = (EditText) findViewById(R.id.et_tyrepressurefront);
		tyre_pressure_back = (EditText) findViewById(R.id.et_tyrepressureback);

		customer_signature = (Button) findViewById(R.id.customer_sig);
		customer_signature.setOnClickListener(listener);

		saleperson_signature = (Button) findViewById(R.id.saleperson_sig);
		saleperson_signature.setOnClickListener(listener);
		img_cust = (ImageView) findViewById(R.id.image_cust);
		img_sal = (ImageView) findViewById(R.id.image_sale);

		layoutlinear_cust_approved_work = (LinearLayout) findViewById(R.id.linear_cust_approved_work);

		unableToAssist = (RadioButton) findViewById(R.id.unable_to_assist);
	/*	unableToAssist
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						
						if (arg1) {

							layoutlinear_cust_approved_work.removeViews(0,
									layoutlinear_cust_approved_work
											.getChildCount() - 1);
							datacust_approved_work.clear();
							//setValue();

						}
					}
				});
		*/
		wheels_cleaned = (RadioGroup) findViewById(R.id.rbg_wheels_cleaned);
		wheels_balanced = (RadioGroup) findViewById(R.id.rbg_wheealbalance);
		alignment_done = (RadioGroup) findViewById(R.id.rbg_allignmentdone);

		tyres_polished = (RadioGroup) findViewById(R.id.rbg_tyrespolished);
		lock_nut_returned = (RadioGroup) findViewById(R.id.rbg_locknutreturned);
		car_tested_by_salesperson = (RadioGroup) findViewById(R.id.rbg_cartestedbysalesperson);

		work_inspected_by_salesperson = (RadioGroup) findViewById(R.id.rbg_workinspectedbysalesperson);
		work_approved_by_salesperson = (RadioGroup) findViewById(R.id.rbg_workapprovedbysalesperson);
		customer_satisfied = (RadioGroup) findViewById(R.id.rbg_customersatisfied);

	

	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

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
				
				if (!isCustomerSignaturedraw || !isSalePersonSignaturedraw) {

					Toast.makeText(context, "Draw All Signature",
							Toast.LENGTH_LONG).show();
					return;
				} else if (Quotation2.getText().length() < 1) {
					Toast.makeText(context, "Fill quotation fields",
							Toast.LENGTH_LONG).show();
					return;
				}/*
				 * else if (radiogrouptext.length()<1) { Toast.makeText(context,
				 * "Select any radio button", Toast.LENGTH_LONG).show(); return;
				 * }
				 */
				else if (isCustomerSignaturedraw && isSalePersonSignaturedraw) { 
					
					setNewData();
					if (isDataFiledUpdated(jobData)) {
						
					
						status currentStatus = ((CarAppSession) getApplication()).getCurrentUploadFileStatus();
						Log.e("status", ""+currentStatus);
						Toast.makeText(context, ""+currentStatus, 1).show();
						
						switch (currentStatus) {
						case PDFCREATED:
							new UpdateDataBase(context, "update_vc",
									((CarAppSession) getApplication())).execute("");
							break;
						case DATABASEUPDATED:
							new UploadFile(context, PdfInfo.path,
									((CarAppSession) getApplication())).execute("");
							break;
						case FILESUPLOADED:
						
						jobData.setCust_approved_work(TextUtils.join(",",
								datacust_approved_work));
						// UploadDataInfo.radiodata=radiogrouptext;

						jobData.setQuotation2(Quotation2.getText()
								.toString());
						
						((CarAppSession)getApplication()).setJobData(jobData);
						new CreatePdf(context, "update_vc",((CarAppSession)getApplication())).execute("");
						break;

						}
					} else {
						Toast.makeText(context, "Fill all Data",
								Toast.LENGTH_LONG).show();
						return;
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
		if (requestCode == 1003 && resultCode == RESULT_OK) {

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

				unableToAssist.setChecked(false);

			} else {
				if (!isStringAdded(value, datacust_approved_work)) {

					inflateEditRow(value, layoutlinear_cust_approved_work,
							datacust_approved_work);
					datacust_approved_work.add(value);
					unableToAssist.setChecked(false);
					
				} else {
					Toast.makeText(context, "Allready Added",
							Toast.LENGTH_SHORT).show();
				}
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
				unableToAssist.setChecked(false);

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

	private void setNewData() {
		jobData.setObservations(observations.getText().toString());
		jobData.setWheel_nuts_torqued(wheel_nuts_torqued.getText()
				.toString());
		jobData.setTyre_pressure_front(tyre_pressure_front.getText()
				.toString());
		jobData.setTyre_pressure_back(tyre_pressure_back.getText()
				.toString());

		int checkedRadioButton = wheels_cleaned.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setWheels_cleaned("Yes");
			break;
		case R.id.radio1:
			jobData.setWheels_cleaned("No");
			break;
		default:
			break;
		}
		checkedRadioButton = wheels_balanced.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setWheels_balanced("Yes");
			break;
		case R.id.radio1:
			jobData.setWheels_balanced("No");
			break;
		default:
			break;
		}
		checkedRadioButton = alignment_done.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setAlignment_done("Yes");
			break;
		case R.id.radio1:
			jobData.setAlignment_done("No");
			break;
		default:
			break;
		}

		checkedRadioButton = tyres_polished.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setTyres_polished("Yes");
			break;
		case R.id.radio1:
			jobData.setTyres_polished("No");
			break;
		default:
			break;
		}
		checkedRadioButton = lock_nut_returned.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setLock_nut_returned("Yes");
			break;
		case R.id.radio1:
			jobData.setLock_nut_returned("No");
			break;
		default:
			break;
		}
		checkedRadioButton = car_tested_by_salesperson
				.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setCar_tested_by_salesperson("Yes");
			break;
		case R.id.radio1:
			jobData.setCar_tested_by_salesperson("No");
			break;
		default:
			break;
		}
		checkedRadioButton = work_inspected_by_salesperson
				.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setWork_inspected_by_salesperson("Yes");
			break;
		case R.id.radio1:
			jobData.setWork_inspected_by_salesperson("No");
			break;
		default:
			break;
		}
		checkedRadioButton = work_approved_by_salesperson
				.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setWork_approved_by_salesperson("Yes");
			break;
		case R.id.radio1:
			jobData.setWork_approved_by_salesperson("No");
			break;
		default:
			break;
		}
		checkedRadioButton = customer_satisfied.getCheckedRadioButtonId();
		switch (checkedRadioButton) {
		case R.id.radio0:
			jobData.setCustomer_satisfied("Yes");
			break;
		case R.id.radio1:
			jobData.setCustomer_satisfied("No");
			break;
		default:
			break;
		}
	}

	

	private void setValue() {

		if (jobData.getDiplay().equals("yes")) {
			String[] cust_reson_for_visit;

			cust_reson_for_visit =jobData.getCust_approved_work().split(",");
			Log.e("lenghths", cust_reson_for_visit.length + " ");
			for (int i = 0; i < cust_reson_for_visit.length; i++) {

				if (!isStringAdded(cust_reson_for_visit[i], datacust_approved_work)) {

					datacust_approved_work.add(cust_reson_for_visit[i]);
					inflateEditRow(cust_reson_for_visit[i],
							layoutlinear_cust_approved_work, datacust_approved_work);
					
				}

			}
			Quotation2.setText(jobData.getQuotation2());
			
			unableToAssist.setChecked(false);
			unableToAssist.setEnabled(false);
		}else {
			btcust_approved_work.setEnabled(false);
			unableToAssist.setChecked(true);
			unableToAssist.setEnabled(false);
		}
		
		observations.setText(jobData.getObservations());

	}
	
	public static boolean isDataFiledUpdated(JobData jobData) {
		Log.e("jobData",""+ jobData);
		if (jobData.getWheel_nuts_torqued().length() > 0 
				&& jobData.getWheels_cleaned() .length() > 0
				&& jobData.getWheels_balanced().length() > 0
				&& jobData.getAlignment_done().length() > 0
				&& jobData.getTyre_pressure_front().length() > 0
				&& jobData.getTyre_pressure_back().length() > 0
				&& jobData.getTyres_polished().length() > 0
				&& jobData.getLock_nut_returned().length() > 0
				&& jobData.getCar_tested_by_salesperson().length() > 0
				&& jobData.getWork_inspected_by_salesperson().length() > 0
				&& jobData.getWork_approved_by_salesperson().length() > 0
				&& jobData.getCustomer_satisfied().length() > 0) {
			return true;
		} else {
			
			return false;
		}
	}
}
