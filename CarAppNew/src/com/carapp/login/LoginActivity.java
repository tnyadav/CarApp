package com.carapp.login;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carapp.activity.NewJobActivity;
import com.carapp.server.AsyncWebServiceProcessingTask;
import com.carapp.util.AsynckCallback;
import com.carapp.util.PdfInfo;
import com.carapp.util.UIUtils;
import com.example.carappnew.R;
import com.example.tnutil.Callback1;
import com.example.tnutil.Util;

public class LoginActivity extends Activity {

	Context context;
	EditText username;
	Button login;
	TextView loginTitle;
	LinearLayout maincontainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_login);
		context = this;
		username = (EditText) findViewById(R.id.login_e_username);
		maincontainer=(LinearLayout)findViewById(R.id.main_container);
	//	login=(Button) findViewById(R.id.login_b_login);
		
	//	loginTitle=(TextView)findViewById(R.id.login_message);
	//	login.setOnClickListener(onClickListener);

	}
	

	public void login(View v) {
		String pass = username.getText().toString();

		if (pass.length() > 0) {
			String result = "";
			MultipartEntity entity = new MultipartEntity();
			try {
				entity.addPart("action", new StringBody("login"));
				entity.addPart("user_password", new StringBody(pass));

				new AsyncWebServiceProcessingTask(context, entity,
						"Checking password", new AsynckCallback() {

							@Override
							public void run(String result) {
								// TODO Auto-generated method stub
								if (UIUtils.checkJson(result, context)) {
									try {
										JSONObject jsonObject = new JSONObject(
												result);
										if (jsonObject.optString("satus")
												.equals("success")) {
											PdfInfo.name = jsonObject
													.optString("user_name");
											maincontainer.setVisibility(View.INVISIBLE);
										Util.showCustomDialog(context,"Success" , "Welcome, "
												+ PdfInfo.name, new Callback1() {
													
													@Override
													public void ok() {
														Intent intent = new Intent(context, NewJobActivity.class);
														startActivity(intent);
													}
												});
											

										} else if (jsonObject
												.optString("satus").equals(
														"error")) {
											Util.showCustomDialog(context, "Error", jsonObject.optString("msg"));
											
										}

									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();

									}

								}
							}
						}).execute(PdfInfo.newjobcard);
				Log.e("AsyncWebServiceProcessingTask", result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			blink(username);
		}
	}

	private void blink(EditText et) {

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(5); // You can manage the time of the blink with this
								// parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(5);
		et.startAnimation(anim);
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}



}
