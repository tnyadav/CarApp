package com.carapp.activity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.carapp.server.AsyncWebServiceProcessingTask;
import com.carapp.util.AsynckCallback;
import com.carapp.util.PdfInfo;
import com.carapp.util.UIUtils;
import com.example.carappnew.R;
import com.example.tnutil.Util;
import com.example.tnutil.Util.Callback2;

public class splashActivity extends Activity {
	public static String Client = "none";
	public static String Branch = "none";
	public static String Licenserequestemailaddress;

	public static boolean LicenseCheck = false;

	private Context context;
	public static String messagecheck = "Please wait while performing initial configuration";
	public static String messagechecksucces = "Initial configuration completed successfully";
	public static String messagecheckerror = " Could not find initial configuration information! CarApp is now exiting";
	private static String messagecheckdateerror = " Warning: please correct today’s date in the device ";
	private static String messagecheckday = "Please wait while checking day";
	private static String messagecheckdayerror = "Warning: day is different from server";
	//private static String messagecheckLicense = "Please wait while checking License";
	//private static String messagecheckLicenseerror = "Warning: License not found, requesting new license";

	public static String t = "splashActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.splash);
		context = this;

		if (Client.equals("none") || Branch.equals("none")) {
			getClientBranch();

		}

	}

	private void getClientBranch() {

		MultipartEntity entity = new MultipartEntity();
		try {
			entity.addPart("action", new StringBody("init_config"));

			new AsyncWebServiceProcessingTask(context, entity, messagecheck,
					new AsynckCallback() {

						@Override
						public void run(String result) {
							if (UIUtils.checkJson(result, context)) {
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									if (jsonObject.optString("satus").equals(
											"success")) {
										PdfInfo.client = jsonObject
												.optString("client");
										PdfInfo.branch = jsonObject
												.optString("branch");
										Util.showCustomDialogWithoutButton(
												context, "Message",
												messagechecksucces,
												new Callback2() {
													@Override
													public void ok(
															final Dialog dialog) {
														final Handler handler = new Handler();
														final Runnable runnable = new Runnable() {
															@Override
															public void run() {
																dialog.dismiss();
																new AsyncWebServiceProcessingTask(
																		context,
																		null,
																		"Please wait while checking date",
																		new AsynckCallback() {

																			@Override
																			public void run(
																					String result) {
																				Log.e("date",
																						""
																								+ result);
																				String sysdate = android.text.format.DateFormat
																						.format("dd/MM/yyyy",
																								new java.util.Date())
																						.toString();
																				if (result
																						.equals(sysdate)) {
																					Toast.makeText(
																							context,
																							"date match",
																							Toast.LENGTH_SHORT)
																							.show();
																					new AsyncWebServiceProcessingTask(
																							context,
																							null,
																							messagecheckday,
																							new AsynckCallback() {
																								@Override
																								public void run(
																										String result) {
																									int day = Integer
																											.parseInt(result);
																									Calendar calendar = Calendar
																											.getInstance();
																									int Today = calendar
																											.get(Calendar.DAY_OF_WEEK);
																									day++;
																									if (day == Today) {
																										Toast.makeText(
																												context,
																												"day is correct",
																												Toast.LENGTH_SHORT)
																												.show();
																										String ma_a = null;

																										try {
																											WifiManager wiman = (WifiManager) getSystemService(Context.WIFI_SERVICE);
																											ma_a = wiman
																													.getConnectionInfo()
																													.getMacAddress();
																											Log.i(t,
																													""
																															+ ma_a);
																										} catch (Exception e1) {

																											e1.printStackTrace();
																											Log.i(t,
																													" "
																															+ e1);
																										}
																										MultipartEntity entity = new MultipartEntity();
																										try {
																											entity.addPart(
																													"action",
																													new StringBody(
																															"device_authentication"));
																											entity.addPart(
																													"mac_address",
																													new StringBody(
																															ma_a));
																											new AsyncWebServiceProcessingTask(
																													context,
																													entity,
																													"Checking License",
																													new AsynckCallback() {

																														@Override
																														public void run(
																																String result) {

																															if (UIUtils
																																	.checkJson(
																																			result,
																																			context)) {
																																try {
																																	JSONObject jsonObject = new JSONObject(
																																			result);
																																	if (jsonObject
																																			.optString(
																																					"satus")
																																			.equals("success")) {
																																		Toast.makeText(
																																				context,
																																				"LicenseCheck sucesses",
																																				Toast.LENGTH_SHORT)
																																				.show();
																																		startActivity(new Intent(
																																				context,
																																				Login.class));
																																		finish();

																																	} else if (jsonObject
																																			.optString(
																																					"satus")
																																			.equals("error")) {
																																		Util.showCustomDialog(
																																				context,
																																				"Error",
																																				jsonObject
																																						.optString("msg"));

																																	}

																																} catch (Exception e) {

																																	e.printStackTrace();

																																}

																															}
																														}
																													})
																													.execute(PdfInfo.newjobcard);

																										} catch (Exception e) {

																											e.printStackTrace();
																										}

																									} else {
																										Util.showCustomDialogWithoutButton(
																												context,
																												"Error",
																												messagecheckdayerror
																														+ " server day is "
																														+ day
																														+ " but your device is"
																														+ Today,
																												new Callback2() {

																													@Override
																													public void ok(
																															final Dialog dialog) {

																														final Handler handler = new Handler();
																														final Runnable runnable = new Runnable() {
																															@Override
																															public void run() {
																																dialog.dismiss();
																																finish();
																															}
																														};
																														handler.postDelayed(
																																runnable,
																																5000);

																													}
																												});
																									}

																								}
																							})
																							.execute(PdfInfo.dayaddress);
																				} else {

																					Util.showCustomDialogWithoutButton(
																							context,
																							"Error",
																							messagecheckdateerror
																									+ " server date is "
																									+ result
																									+ " but device date is "
																									+ sysdate,
																							new Callback2() {

																								@Override
																								public void ok(
																										final Dialog dialog) {

																									new Timer()
																											.schedule(
																													new TimerTask() {

																														@Override
																														public void run() {
																															dialog.dismiss();
																															finish();

																														}
																													},
																													5000);
																								}
																							});

																				}
																			}
																		})
																		.execute(PdfInfo.dateaddress);

															}
														};
														handler.postDelayed(
																runnable, 5000);

													}
												});

									} else if (jsonObject.optString("satus")
											.equals("error")) {
										Util.showCustomDialogWithoutButton(
												context, "Message",
												messagechecksucces,
												new Callback2() {

													@Override
													public void ok(
															final Dialog dialog) {

														new Timer()
																.schedule(
																		new TimerTask() {

																			@Override
																			public void run() {
																				dialog.dismiss();
																				finish();

																			}
																		}, 5000);
													}
												});

									}

								} catch (Exception e) {

									e.printStackTrace();

								}

							}
						}
					}).execute(PdfInfo.newjobcard);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

}
