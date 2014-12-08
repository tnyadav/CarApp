package com.carapp.activity;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.JsonParser;
import com.carapp.bean.WorkAssissment;
import com.carapp.util.PdfInfo;
import com.carapp.util.SharedPreferencesUtil;
import com.example.carappnew.R;

@SuppressLint("SetJavaScriptEnabled")
public class CarViewActivity extends BaseBroadcastReceiverActivity {

//	TextView header;
	WebView w;
	Intent intent;
	public static CarViewActivity mainactivity;
  private WorkAssissment workAssissment;

	@SuppressLint("JavascriptInterface")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_carview);
		// w = new WebView(this);
		mainactivity = this;
		
		intent = getIntent();
		//header = (TextView) findViewById(R.id.mainactivity_hedertext);
		
		w = (WebView) findViewById(R.id.webview);
		w.getSettings().setLoadWithOverviewMode(true);
		w.getSettings().setUseWideViewPort(true);
		WebSettings webSettings = w.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);

		JsInterface jsInterface = new JsInterface();
		w.addJavascriptInterface(jsInterface, "android");
		w.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {
				Log.e("intent value", "start");
			//	if (PdfInfo.mode==PdfInfo.EXIT_MODE||PdfInfo.mode==PdfInfo.EDIT_MODE) {
					loadValueToPage();
			//	}

			}
		});
		if (PdfInfo.mode == PdfInfo.EDIT_MODE || PdfInfo.mode == PdfInfo.EXIT_MODE) {
			
			workAssissment=((CarAppSession)getApplication()).getWorkAssissment();
			
			Log.e("intent value", ""+workAssissment);
			if (PdfInfo.mode == PdfInfo.EXIT_MODE) {
				//header.setText("WORK ASSESSMENT");
				getActionBar().setTitle("Work Assissment");
			}else {
				getActionBar().setTitle("Car Assissment");
			}
			w.loadUrl("file:///android_asset/carapp.html");
			//loadValueToPage();
		} else {
			w.loadUrl("file:///android_asset/car.html");
			workAssissment =new WorkAssissment();
		// ********testing*****
			/*String json=SharedPreferencesUtil.getPreferences(context, "car", "");
			try {
				JsonParser jsonParser = new JsonParser(
						context, new JSONObject(json));
				((CarAppSession) getApplication())
				.setWorkAssissment(jsonParser
						.getWorkAssissment());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workAssissment=((CarAppSession)getApplication()).getWorkAssissment();
			w.loadUrl("file:///android_asset/carapp.html");*/
		//********end test*****	
			
		}
	}

	private class JsInterface {

	

		public void takePicture(String filename) {
			Log.d("MSG FROM JAVASCRIPT", filename);
			
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			try {
				File path = new File(PdfInfo.path);
				if (!path.exists()) {
					path.mkdir();
				}
				String sysdate = android.text.format.DateFormat.format(
						"ddMMyyyy", new java.util.Date()).toString();
				File file = new File(PdfInfo.path
						+ ((CarAppSession)getApplication()).getCustomerData().getRegistration() + "_" + filename + "_"
						+ sysdate + ".jpg");
				Uri outputFileUri = Uri.fromFile(file);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		
				startActivityForResult(cameraIntent, 1111);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void setValueFromWabePage(String tyer_condition_lf,
				String tyre_size_lf, String tyre_depth_lfx,
				String tyre_depth_lfy, String brake_pad_lf,
				String brake_disk_lf, String shocker_lf, String wheel_lf,
				String physical_damage_lf, String tyer_condition_lb,
				String tyre_size_lb, String tyre_depth_lbx,
				String tyre_depth_lby, String brake_pad_lb,
				String brake_disk_lb, String shocker_lb, String wheel_lb,
				String physical_damage_lb, String tyer_condition_rf,
				String tyre_size_rf, String tyre_depth_rfx,
				String tyre_depth_rfy, String brake_pad_rf,
				String brake_disk_rf, String shocker_rf, String wheel_rf,
				String physical_damage_rf, String tyer_condition_rb,
				String tyre_size_rb, String tyre_depth_rbx,
				String tyre_depth_rby, String brake_pad_rb,
				String brake_disk_rb, String shocker_rb, String wheel_rb,
				String physical_damage_rb, String immoblizer_f,
				String battery_f, String physical_damage_f,
				String spare_wheel_b, String lock_nut_b,
				String physical_damage_b)
			{

			
			
			
			
			/*
			
			workAssissment.setTyer_condition_lf(tyer_condition_lf);
			UploadDataInfo.tyre_size_lf = tyre_size_lf;
			UploadDataInfo.tyre_depth_lfx = tyre_depth_lfx;
			UploadDataInfo.tyre_depth_lfy = tyre_depth_lfy;
			UploadDataInfo.brake_pad_lf = brake_pad_lf;
			UploadDataInfo.brake_disk_lf = brake_disk_lf;
			UploadDataInfo.shocker_lf = shocker_lf;
			UploadDataInfo.wheel_lf = wheel_lf;
			UploadDataInfo.physical_damage_lf = physical_damage_lf;

			UploadDataInfo.tyer_condition_lb = tyer_condition_lb;
			UploadDataInfo.tyre_size_lb = tyre_size_lb;
			UploadDataInfo.tyre_depth_lbx = tyre_depth_lbx;
			UploadDataInfo.tyre_depth_lby = tyre_depth_lby;
			UploadDataInfo.brake_pad_lb = brake_pad_lb;
			UploadDataInfo.brake_disk_lb = brake_disk_lb;
			UploadDataInfo.shocker_lb = shocker_lb;
			UploadDataInfo.wheel_lb = wheel_lb;
			UploadDataInfo.physical_damage_lb = physical_damage_lb;

			UploadDataInfo.tyer_condition_rf = tyer_condition_rf;
			UploadDataInfo.tyre_size_rf = tyre_size_rf;
			UploadDataInfo.tyre_depth_rfx = tyre_depth_rfx;
			UploadDataInfo.tyre_depth_rfy = tyre_depth_rfy;
			UploadDataInfo.brake_pad_rf = brake_pad_rf;
			UploadDataInfo.brake_disk_rf = brake_disk_rf;
			UploadDataInfo.shocker_rf = shocker_rf;
			UploadDataInfo.wheel_rf = wheel_rf;
			UploadDataInfo.physical_damage_rf = physical_damage_rf;

			UploadDataInfo.tyer_condition_rb = tyer_condition_rb;
			UploadDataInfo.tyre_size_rb = tyre_size_rb;
			UploadDataInfo.tyre_depth_rbx = tyre_depth_rbx;
			UploadDataInfo.tyre_depth_rby = tyre_depth_rby;
			UploadDataInfo.brake_pad_rb = brake_pad_rb;
			UploadDataInfo.brake_disk_rb = brake_disk_rb;
			UploadDataInfo.shocker_rb = shocker_rb;
			UploadDataInfo.wheel_rb = wheel_rb;
			UploadDataInfo.physical_damage_rb = physical_damage_rb;

			UploadDataInfo.immoblizer_f = immoblizer_f;
			UploadDataInfo.battery_f = battery_f;
			UploadDataInfo.physical_damage_f = physical_damage_f;
			UploadDataInfo.spare_wheel_b = spare_wheel_b;
			UploadDataInfo.lock_nut_b = lock_nut_b;
			UploadDataInfo.physical_damage_b = physical_damage_b;*/

			/*
			if (tyer_condition_lf.equals("Tyre Condition")
					|| tyre_size_lf.equals("")
					|| tyre_depth_lfx.equals("Tyre Depth X")
					|| tyre_depth_lfy.equals("Tyre Depth Y")
					|| brake_pad_lf.equals("Brake Pads")
					|| brake_disk_lf.equals("Brake Disks")
					|| shocker_lf.equals("Shocks") || wheel_lf.equals("Wheels")
					|| physical_damage_lf.equals("Physical Damage")
					|| tyer_condition_lb.equals("Tyre Condition")
					|| tyre_size_lb.equals("")
					|| tyre_depth_lbx.equals("Tyre Depth X")
					|| tyre_depth_lby.equals("Tyre Depth Y")
					|| brake_pad_lb.equals("Brake Pads")
					|| brake_disk_lb.equals("Brake Disks")
					|| shocker_lb.equals("Shocks") || wheel_lb.equals("Wheels")
					|| physical_damage_lb.equals("Physical Damage")
					|| tyer_condition_rf.equals("Tyre Condition")
					|| tyre_size_rf.equals("")
					|| tyre_depth_rfx.equals("Tyre Depth X")
					|| tyre_depth_rfy.equals("Tyre Depth Y")
					|| brake_pad_rf.equals("Brake Pads")
					|| brake_disk_rf.equals("Brake Disks")
					|| shocker_rf.equals("Shocks") || wheel_rf.equals("Wheels")
					|| physical_damage_rf.equals("Physical Damage")
					|| tyer_condition_rb.equals("Tyre Condition")
					|| tyre_size_rb.equals("")
					|| tyre_depth_rbx.equals("Tyre Depth X")
					|| tyre_depth_rby.equals("Tyre Depth Y")
					|| brake_pad_rb.equals("Brake Pads")
					|| brake_disk_rb.equals("Brake Disks")
					|| shocker_rb.equals("Shocks") || wheel_rb.equals("Wheels")
					|| physical_damage_rb.equals("Physical Damage")
					|| immoblizer_f.equals("Immobilizer")
					|| battery_f.equals("Battery")
					|| physical_damage_f.equals("Physical Damage")
					|| spare_wheel_b.equals("Spare Wheel")
					|| lock_nut_b.equals("Lock Nut")
					|| physical_damage_b.equals("Physical Damage")) {
				Toast.makeText(getApplicationContext(), "Enter All Fields",
						Toast.LENGTH_LONG).show();
			} else {
				*/
				

				 workAssissment.setTyer_condition_lf(tyer_condition_lf);
		         workAssissment.setTyer_condition_lb(tyer_condition_lb);
		         workAssissment.setTyer_condition_rf(tyer_condition_rf);
		         workAssissment.setTyer_condition_rb(tyer_condition_rb);

		         workAssissment.setTyre_size_lf(tyre_size_lf);
		         workAssissment.setTyre_size_lb(tyre_size_lb);

		         workAssissment.setBrake_pad_lf(brake_pad_lf);
		         workAssissment.setBrake_disk_lf(brake_disk_lf);

		         workAssissment.setShocker_lf(shocker_lf);
		         workAssissment.setWheel_lf(wheel_lf);

		         workAssissment.setPhysical_damage_lf(physical_damage_lf);
		         workAssissment.setPhysical_damage_rb(physical_damage_rb);
		         workAssissment.setPhysical_damage_lb(physical_damage_lb);
		         workAssissment.setPhysical_damage_rf(physical_damage_lf);
		         workAssissment.setPhysical_damage_f(physical_damage_f);
		         workAssissment.setPhysical_damage_b(physical_damage_b);

		         workAssissment.setBrake_pad_lb(brake_pad_lb);
		         workAssissment.setBrake_disk_lb(brake_disk_lb);
		         workAssissment.setShocker_lb(shocker_lb);
		         workAssissment.setWheel_lb(wheel_lb);

		         workAssissment.setTyre_size_rf(tyre_size_rf);
		       //  workAssissment.settytyre_depth_rfx);
		      //   workAssissment.settyre_depth_rfy);

		         workAssissment.setBrake_pad_rf(brake_pad_rf);
		         workAssissment.setBrake_disk_rf(brake_disk_rf);
		         workAssissment.setShocker_rf(shocker_rf);
		         workAssissment.setWheel_rf(wheel_rf);

		         workAssissment.setTyre_size_rb(tyre_size_rb);
		         //workAssissment.settytyre_depth_rbx);
		         //workAssissment.settyre_depth_rby);
		         workAssissment.setBrake_pad_rb(brake_pad_rb);
		         workAssissment.setBrake_disk_rb(brake_disk_rb);
		         workAssissment.setShocker_rb(shocker_rb);
		         workAssissment.setWheel_rb(wheel_rb);

		         workAssissment.setTyre_depth_lf(tyre_depth_lfx+tyre_depth_lfy);
		         workAssissment.setTyre_depth_lb(tyre_depth_lbx+tyre_depth_lby);
		         workAssissment.setTyre_depth_rf(tyre_depth_rfx+tyre_depth_rfy);
		         workAssissment.setTyre_depth_rb(tyre_depth_rbx+tyre_depth_rby);

		         workAssissment.setImmoblizer_f(immoblizer_f);
		         workAssissment.setBattery_f(battery_f);

		         workAssissment.setSpare_wheel_b(spare_wheel_b);
		         workAssissment.setLock_nut_b(lock_nut_b);
		         
		         Log.e("workAssissment", ""+workAssissment);
		         
		         ((CarAppSession)getApplication()).setWorkAssissment(workAssissment);

				/*if (PdfInfo.mode==PdfInfo.EXIT_MODE) {
					
					Intent i = new Intent(getApplicationContext(),JobDataExitActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
					
				}else if (PdfInfo.mode==PdfInfo.EDIT_MODE) {
					
					Intent i = new Intent(getApplicationContext(),JobDataEditActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
					
				} else {

					Intent i = new Intent(getApplicationContext(),JobDataCreateActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
				}
*/
		         Intent i = new Intent(getApplicationContext(),PartAssismentActivity.class);
				 startActivity(i);
			     overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
			}

		}
//	}

	private void loadValueToPage() {

	
		String action = "edit";
		
		Log.e("**********", "############");
		
				w.loadUrl("javascript:loadValueToPage(\""
				+ action
				+ "\",\""
				+ workAssissment.getTyer_condition_lf()
				+ "\",\""
				+ workAssissment.getTyer_condition_lb()
				+ "\",\""
				+ workAssissment.getTyer_condition_rf()
				+ "\",\""
				+ workAssissment.getTyer_condition_rb()
				+ "\",\""

				+ workAssissment.getTyre_size_lf()
				+ "\",\""
				+ workAssissment.getTyre_size_lb()
				+ "\",\""
				+ workAssissment.getTyre_size_rf()
				+ "\",\""
				+ workAssissment.getTyre_size_rb()
				+ "\",\""

				// *********************************************************
				+ workAssissment.getTyre_depth_lf()
				+ "\",\""
				+ workAssissment.getTyre_depth_lb()
				+ "\",\""
				+ workAssissment.getTyre_depth_rf()
				+ "\",\""
				+ workAssissment.getTyre_depth_rb()
				+ "\",\""

				// ***************************************************************

				+ workAssissment.getBrake_pad_lf() + "\",\""
				+ workAssissment.getBrake_pad_lb() + "\",\""
				+ workAssissment.getBrake_pad_rf() + "\",\""
				+ workAssissment.getBrake_pad_rb() + "\",\""

				+ workAssissment.getBrake_disk_lf() + "\",\""
				+ workAssissment.getBrake_disk_lb() + "\",\""
				+ workAssissment.getBrake_disk_rf() + "\",\""
				+ workAssissment.getBrake_disk_rb() + "\",\""

				+ workAssissment.getShocker_lf() + "\",\""
				+ workAssissment.getShocker_lb() + "\",\""
				+ workAssissment.getShocker_rf() + "\",\""
				+ workAssissment.getShocker_rb() + "\",\""

				+ workAssissment.getWheel_lf() + "\",\"" 
				+ workAssissment.getWheel_lb() + "\",\"" 
				+ workAssissment.getWheel_rf() + "\",\""
				+ workAssissment.getWheel_rb() + "\",\""

				+ workAssissment.getPhysical_damage_lf() + "\",\""
				+ workAssissment.getPhysical_damage_lb() + "\",\""
				+ workAssissment.getPhysical_damage_rf() + "\",\""
				+ workAssissment.getPhysical_damage_rb() + "\",\""

				+ workAssissment.getImmoblizer_f() + "\",\""
				+ workAssissment.getBattery_f() + "\",\""
				+ workAssissment.getPhysical_damage_f() + "\",\""

				+ workAssissment.getSpare_wheel_b() + "\",\""
				+ workAssissment.getLock_nut_b() + "\",\""
				+ workAssissment.getPhysical_damage_b() + "\")");

		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onResume() {
		
		super.onResume();
	w.onResume();
	}
	@Override
	protected void onPause() {
		
		super.onPause();
    w.onPause();
	}

	@Override
	protected void setTitleBar() {
		// TODO Auto-generated method stub
		
	}
}