package com.carapp.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.CustomerData;
import com.carapp.util.PdfInfo;
import com.carapp.util.UIUtils;
import com.example.carappnew.R;
import com.example.tnutil.Util;

public class CustomerDataActivity extends BaseBroadcastReceiverActivity {

	private EditText etBranch, etSaleperson, etCustomer, etContactNo,
			etcompany, etemail, etAddress, etMake, etModel, etYear, etOdometer,
			etRegistration, etDate, etTime;
	private String strBranch, strSaleperson, strCustomer, strContactNo,
			strAddress, strcompany, stremail, strMake, strModel, strYear,
			strOdomstrer, strRegistration, strDate, strTime,custresonForVisit;
	private Button btcust_reson_for_visit;
	private LinearLayout layoutlinear_cust_reson_for_visit;
	ScrollView LinearLayout1;
	private CheckBox companyRadio;
	private Button btnext;
	EditText[] edittext;
	DateFormat formatDateTime = DateFormat.getDateInstance();
	Calendar dateTime = Calendar.getInstance();
	static final int DATE_DIALOG_ID = 999;
	public static CustomerDataActivity customerdata;
	String emailformat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Context context;
	Intent i;
    private CustomerData customerData; 
    private ArrayList<String> datacust_reson_for_visit; 
    
    //
    private Spinner fleet;
	
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_data);
		LinearLayout1=(ScrollView)findViewById(R.id.LinearLayout11);
		customerdata = this;
		context = this;
		datacust_reson_for_visit = new ArrayList<String>();
		if (PdfInfo.mode==PdfInfo.EDIT_MODE || PdfInfo.mode==PdfInfo.EXIT_MODE || PdfInfo.mode==PdfInfo.CHECKOUT_MODE) {
			this.customerData=((CarAppSession)getApplication()).getCustomerData();
		}else {
			customerData=new CustomerData();
		}
		
		setContent();
	}

	void setContent() {

		i = getIntent();

		btnext = (Button) findViewById(R.id.customer_data_1_next);
		btnext.setOnClickListener(listener);

		etBranch = (EditText) findViewById(R.id.branch);
		etBranch.setText(PdfInfo.branch);

		etSaleperson = (EditText) findViewById(R.id.saleperson);
		etSaleperson.setText(PdfInfo.name);

		etCustomer = (EditText) findViewById(R.id.customer);
		etCustomer.addTextChangedListener(textwatcher);

		etContactNo = (EditText) findViewById(R.id.contactno);
		etContactNo.addTextChangedListener(textwatcher);

		etemail = (EditText) findViewById(R.id.email);
		etemail.addTextChangedListener(textwatcher);

		etcompany = (EditText) findViewById(R.id.company);
		etcompany.addTextChangedListener(textwatcher);

		companyRadio = (CheckBox) findViewById(R.id.company_radio);
		companyRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				
				if (isChecked) {
					etcompany.setText("");
					etcompany.setEnabled(false);
				} else {
					etcompany.setEnabled(true);
				}
			}
		});
        fleet=(Spinner)findViewById(R.id.fleet);
        List<String> option= new ArrayList<String>();
        option.add("Select");
        option.add("Value1");
        option.add("Value2");
        option.add("Value3");
        option.add("Value4");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this,
				R.layout.item_spiner,
				option);
        fleet.setAdapter(spinnerArrayAdapter);
        fleet.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				etcompany.setText("");
				etcompany.setEnabled(false);
				companyRadio.setChecked(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
		etAddress = (EditText) findViewById(R.id.address);
		etAddress.addTextChangedListener(textwatcher);

		etMake = (EditText) findViewById(R.id.make);
		etMake.addTextChangedListener(textwatcher);

		etModel = (EditText) findViewById(R.id.model);
		etModel.addTextChangedListener(textwatcher);
		
		etYear = (EditText) findViewById(R.id.year);
        etYear.addTextChangedListener(textwatcher);

		etOdometer = (EditText) findViewById(R.id.odometer);
		etOdometer.addTextChangedListener(textwatcher);

		etRegistration = (EditText) findViewById(R.id.registration);
		etRegistration.setText(i.getStringExtra("CarNoPlatefinal"));
       
		layoutlinear_cust_reson_for_visit = (LinearLayout) findViewById(R.id.linear_cust_reson_for_visit);
		btcust_reson_for_visit = (Button) findViewById(R.id.cust_reson_for_visit);
		btcust_reson_for_visit.setOnClickListener(listener);
		
		//current date
		etDate = (EditText) findViewById(R.id.date);
		String sysdate = android.text.format.DateFormat.format("dd/MM/yyyy",
				new java.util.Date()).toString();
		etDate.setText(sysdate);
		
		//current time
		etTime = (EditText) findViewById(R.id.time);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
		String test = sdf.format(cal.getTime());
		etTime.setText("" + test);
		
		TextView tvDate = (TextView) findViewById(R.id.tv_date);
		TextView tvTime = (TextView) findViewById(R.id.tv_time);
		
		String sysdate1 = android.text.format.DateFormat.format("dd/MM/yyyy",
				new java.util.Date()).toString();
		// etDate.setText(UploadDataInfo.strDate);
		etDate.setText(sysdate1);
		// etTime.setText(UploadDataInfo.strTime);
		etTime = (EditText) findViewById(R.id.time);
		Calendar cal1 = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.US);
		String test1 = sdf1.format(cal1.getTime());
		etTime.setText(test1);
		
		edittext = new EditText[] { etBranch, etSaleperson,
				etCustomer,
				etContactNo,
				// etemail,
				// etcompany,
				etAddress, etMake, etModel, etYear, etOdometer, etRegistration,
				etDate, etTime };
		
		etBranch.setKeyListener(null);
		etSaleperson.setKeyListener(null);
		etRegistration.setKeyListener(null);
		etDate.setKeyListener(null);
        etTime.setKeyListener(null);
		
		if (PdfInfo.mode==PdfInfo.EDIT_MODE|| PdfInfo.mode==PdfInfo.EXIT_MODE || PdfInfo.mode==PdfInfo.CHECKOUT_MODE) {
			
			companyRadio.setEnabled(false);
			//set value*******************************
			etBranch.setText(customerData.getBranch());
            etSaleperson.setText(customerData.getSaleperson());
			etCustomer.setText(customerData.getCustomer());
			etContactNo.setText(customerData.getContactNo());
			etcompany.setText(customerData.getCompany());
			if (customerData.getCompany() == null
					|| customerData.getCompany().equalsIgnoreCase("")) {
				companyRadio.setChecked(true);
			}
			etemail.setText(customerData.getEmail());
			etAddress.setText(customerData.getAddress());
			etMake.setText(customerData.getMake());
			etModel.setText(customerData.getModel());
			etYear.setText(customerData.getYear());
			etRegistration.setText(customerData.getRegistration());
       
		//  disable editing**************************
			
			
			
			etMake.setKeyListener(null);
			etModel.setKeyListener(null);
			etYear.setKeyListener(null);
			
			String[] tempData = customerData.getCust_resonfor_visit().split(",");
			Log.e("lenghths", tempData.length + " "+customerData.getCust_resonfor_visit());
			for (int i = 0; i < tempData.length; i++) {

				if (!isStringAdded(tempData[i], datacust_reson_for_visit)) {

					datacust_reson_for_visit.add(tempData[i]);
					inflateEditRow(tempData[i],
							layoutlinear_cust_reson_for_visit, datacust_reson_for_visit);
					

				}

			}

		}
		
		 if (PdfInfo.mode==PdfInfo.EDIT_MODE) {
	    	    
				etOdometer.setText(customerData.getOdomstrer());
				etCustomer.setKeyListener(null);
				etcompany.setKeyListener(null);
				etAddress.setKeyListener(null);
				etOdometer.setKeyListener(null);
	            tvDate.setText("Job Edit Date");
	            tvTime.setText("Job Edit Time");
	            btnext.setBackgroundResource(R.drawable.arrow_down_green);
	           
	          
	        }
		 
		if (PdfInfo.mode==PdfInfo.EXIT_MODE) {
			etCustomer.setKeyListener(null);
			etcompany.setKeyListener(null);
			etAddress.setKeyListener(null);
            tvDate.setText("Job Exit Date");
            tvTime.setText("Job Exit Time");
            
		}
		
		
  	}

	private void nextScreen1() {

		strBranch = etBranch.getText().toString();
		strSaleperson = etSaleperson.getText().toString();
		strCustomer = etCustomer.getText().toString();
		strContactNo = etContactNo.getText().toString();
		strcompany = etcompany.getText().toString();
		stremail = etemail.getText().toString();
		strAddress = etAddress.getText().toString();
		strMake = etMake.getText().toString();
		strModel = etModel.getText().toString();
		strYear = etYear.getText().toString();
		strOdomstrer = etOdometer.getText().toString();
		strRegistration = etRegistration.getText().toString();
		strDate = etDate.getText().toString();
		strTime = etTime.getText().toString();
		custresonForVisit=TextUtils.join(",",datacust_reson_for_visit);
		

		if (strBranch.length() > 0 && strSaleperson.length() > 0
				&& strCustomer.length() > 0 && strContactNo.length() > 0
				&& strAddress.length() > 0 && strMake.length() > 0
				&& strModel.length() > 0 && strYear.length() > 0
				&& strOdomstrer.length() > 0 && strRegistration.length() > 0
				&& strDate.length() > 0 && strTime.length() > 0) {
			if (etemail.getText().toString().length() > 0
					&& !Pattern.compile(emailformat)
							.matcher(etemail.getText().toString().trim())
							.matches()) {
				
				Util.showCustomDialog(context, "Message", "The email address provided is incorrect. Please verify and try again.");
				
			} else {
				customerData.setBranch(strBranch);
				
				customerData.setSaleperson(strSaleperson);
				customerData.setCustomer(strCustomer);
				customerData.setContactNo(strContactNo);
				customerData.setCompany(strcompany);
				customerData.setEmail(stremail);
				customerData.setAddress(strAddress);
				customerData.setMake(strMake);
				customerData.setModel(strModel);
				customerData.setYear(strYear);
				customerData.setOdomstrer(strOdomstrer);
				customerData.setRegistration(strRegistration);
				customerData.setDate(strDate);
				customerData.setTime(strTime);
				customerData.setCust_resonfor_visit(custresonForVisit);
				Log.e("customerData", ""+customerData);
				((CarAppSession)getApplication()).setCustomerData(customerData);
				/*
				View u = findViewById(R.id.LinearLayout11);
	            u.setDrawingCacheEnabled(true);
	            int totalHeight = LinearLayout1.getChildAt(0).getHeight();
	            int totalWidth = LinearLayout1.getChildAt(0).getWidth();
	            u.layout(0, 0, totalWidth, totalHeight);    
	            u.buildDrawingCache(true);
	            Bitmap b = Bitmap.createBitmap(u.getDrawingCache());             
	            u.setDrawingCacheEnabled(false);
	            
	            File path = new File(PdfInfo.path);
	    		if (!path.exists()) {
	    			path.mkdir();
	    		}

	    		FileOutputStream fos = null;
	    		try {
	    			fos = new FileOutputStream(new File(PdfInfo.path+"cap.jpeg"));
	    		} catch (FileNotFoundException e) {
	    			e.printStackTrace();
	    		}

	    		b.compress(Bitmap.CompressFormat.PNG, 95, fos);*/
	            
				//CaptureScreen captureScreen =new CaptureScreen(u, context);
				//captureScreen.save();

				Intent i = new Intent(getApplicationContext(),
						CarViewActivity.class);

				startActivity(i);
				overridePendingTransition(R.anim.slide_in_up,
						R.anim.slide_out_up);
			}

		} else {

			Toast.makeText(getApplicationContext(), "Enter All Field",
					Toast.LENGTH_LONG).show();

		}

	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.customer_data_1_next:
				nextScreen1();

				break;
			case R.id.cust_reson_for_visit:

				Intent i = new Intent(getApplicationContext(),
						CustResonForVisitListActivity.class);
				i.putExtra("listname", 0);
				startActivityForResult(i, 1001);
				break;
			default:
				break;
			}

		}
	};
	TextWatcher textwatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			if (validate(edittext)) {
				btnext.setBackgroundResource(R.drawable.arrow_down_green);
			} else {
				btnext.setBackgroundResource(R.drawable.arrow_down_red);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			

		}

		@Override
		public void afterTextChanged(Editable s) {
			

		}
	};

	@Override
	protected void onPause() {
		
		super.onPause();
	}

	public void chooseDate() {
		new DatePickerDialog(CustomerDataActivity.this, d, dateTime.get(Calendar.YEAR),
				dateTime.get(Calendar.MONTH),
				dateTime.get(Calendar.DAY_OF_MONTH)).show();
	}

	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			monthOfYear = monthOfYear + 1;
			dateTime.set(Calendar.YEAR, year);

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		String []value= data.getStringArrayExtra("position");
		if (requestCode == 1001 && resultCode == RESULT_OK) {

		/*	value = data.getStringExtra("position");
			if (!isStringAdded(value, datacust_reson_for_visit)) {
				inflateEditRow(value, layoutlinear_cust_reson_for_visit,
						datacust_reson_for_visit);
			} else {
				Toast.makeText(context, "Allready Added", Toast.LENGTH_SHORT)
						.show();
			}*/
			
			
			for (int i = 0; i < value.length; i++) {
				if (!isStringAdded(value[i], datacust_reson_for_visit)) {
					inflateEditRow(value[i],  layoutlinear_cust_reson_for_visit,
							datacust_reson_for_visit);
				}
			}
			

	}
	}
	private boolean validate(EditText[] fields) {
		boolean result = true;
		for (int i = 0; i < fields.length; i++) {
			EditText currentField = fields[i];

			if (currentField.getText().toString().length() <= 0) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();

	}
	@Override
	protected void onResume() {
		
		super.onResume();
		UIUtils.deleteFiles();
	}
	private void inflateEditRow(final String value, final LinearLayout layout,
			final ArrayList<String> list) {
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.list_item_1, null);

		final TextView textvalue = (TextView) rowView
				.findViewById(R.id.list_item_1_textview);
		textvalue.setText(value);
		final ImageButton delete = (ImageButton) rowView
				.findViewById(R.id.list_item_1_delete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				removeFromlist(value, list);
				layout.removeView(rowView);
				/*if (datacust_approved_work.size() < 1
						&& Quotation2.getText().toString().length() == 0) {
               	
				}*/

			}
		});

		// Inflate at the end of all rows but before the "Add new" button
		
		layout.addView(rowView, layout.getChildCount() - 1);
		list.add(value);
		//Toast.makeText(context,value+" size: "+layout.getChildCount(), 1).show();
		
		
	}
	
	private boolean isStringAdded(String value, ArrayList<String> list) {
		boolean added = false;
		/*for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(value)) {

				added = true;
			}

		}*/
		if (list.contains(value)) {
			added = true;
		}
		return added;

	}
	private void removeFromlist(String value, ArrayList<String> list) {
		list.remove(value);
		
	}

	@Override
	protected void setTitleBar() {
		getActionBar().setTitle("Customer Data");
		
	}
	
	
}
