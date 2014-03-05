package com.carapp.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.JsonParser;
import com.carapp.bean.Ragistration;
import com.carapp.server.AsyncWebServiceProcessingTask;
import com.carapp.util.AsynckCallback;
import com.carapp.util.PdfInfo;
import com.carapp.util.UIUtils;
import com.example.carappnew.R;
import com.example.tnutil.Util;

public class NewJob extends Activity {

	private Context context;
	private EditText carnoplate;
	private Button check;
	private LinearLayout buttonContainer;
	private String CarNoPlate;
	private ListView listView;
	private String selectedListItem = "";
	private List<Ragistration> list;
	private List<Ragistration> checkoutList;
	private ListViewAdapter adapter;
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobcard);
		context = this;
        check = (Button) findViewById(R.id.check);
		buttonContainer = (LinearLayout) findViewById(R.id.button_Container);
		buttonContainer.setVisibility(View.GONE);
		

		listView = (ListView) findViewById(R.id.listView);
		carnoplate = (EditText) findViewById(R.id.car_no_plate);

		carnoplate = (EditText) findViewById(R.id.car_no_plate);
		carnoplate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				String text = carnoplate.getText().toString()
						.toLowerCase(Locale.getDefault());
				if (adapter!=null) {
					adapter.filter(text);
				}
				
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				

			}

			@Override
			public void afterTextChanged(Editable s) {
				
				if (checkRegistrationNo(carnoplate.getText().toString(),list)!=null) {
					
					check.setVisibility(View.GONE);
					buttonContainer.setVisibility(View.VISIBLE);
				
				}else {
					check.setVisibility(View.VISIBLE);
					buttonContainer.setVisibility(View.GONE);
				}
			
			}
		});

		listView = (ListView) findViewById(R.id.listView);
		
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

	public void check(View v) {
		// com.carapp.util.PdfInfo.isUpdate = false;
		CarNoPlate = carnoplate.getText().toString().trim();
		if (CarNoPlate.length()==0) {
			blink(carnoplate);
			return;
		}
		CarNoPlate = CarNoPlate.replaceAll("\\s", "");
		CarNoPlate = CarNoPlate.toUpperCase();
		carnoplate.setText(CarNoPlate);
		String csId=checkRegistrationNo(CarNoPlate,checkoutList);
        if (csId!=null) {
			getDetail(csId,3);
		}else {
			carnoplate.setSelection(carnoplate.getText().toString().length());
			Util.showCustomDialog(context, "Message", "Entry "
					+ CarNoPlate + " not found!", "Retype entry",
					"Create New Job Card", new com.example.tnutil.Callback() {

						@Override
						public void ok() {}

						@Override
						public void cancel() {

							Intent intent = new Intent(context,
									CustomerDataActivity.class);
							intent.putExtra("CarNoPlatefinal", CarNoPlate);
							intent.putExtra("for", 0);
							startActivity(intent);
							PdfInfo.mode = 0;
							PdfInfo.csdId = "";
							overridePendingTransition(R.anim.slide_in_up,
									R.anim.slide_out_up);
							carnoplate.setText("");
						}
					});
		}
		

	}

	public void editExistingJob(View v) {
		String CarNoPlate = carnoplate.getText().toString().trim();
		CarNoPlate = CarNoPlate.replaceAll("\\s", "");
		CarNoPlate = CarNoPlate.toUpperCase();
		carnoplate.setText(CarNoPlate);
		String csId=checkRegistrationNo(CarNoPlate,list);
		getDetail(csId, 1);
	}

	public void proceedToCheckout(View v) {
		String CarNoPlate = carnoplate.getText().toString().trim();
		CarNoPlate = CarNoPlate.replaceAll("\\s", "");
		CarNoPlate = CarNoPlate.toUpperCase();
		carnoplate.setText(CarNoPlate);
		String csId=checkRegistrationNo(CarNoPlate,list);
		getDetail(csId, 2);
	}

	public void exit(View v) {

		Util.showCustomDialog(context, "Message", "Do you want to exit", "Yes",
				"No", new com.example.tnutil.Callback() {

					@Override
					public void ok() {
						
						finish();
					}

					@Override
					public void cancel() {
						

					}
				});
	}

@Override
protected void onResume() {
	
	super.onResume();
	getRegPlateNoList();
}


	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		System.exit(0);

	}

	private void getRegPlateNoList() {
		result = "";

		MultipartEntity entity = new MultipartEntity();
		try {
			entity.addPart("action", new StringBody("getlist_reg_plate_no"));
			new AsyncWebServiceProcessingTask(context, entity,
					"Getting List of registration Plate", new AsynckCallback() {

						@Override
						public void run(String result) {
							
							if (UIUtils.checkJson(result, context)) {
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									list = new ArrayList<Ragistration>();
									JSONArray jsonArray = jsonObject
											.optJSONArray("list");

									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jObject = jsonArray
												.optJSONObject(i);
										Ragistration ragistration = new Ragistration(
												jObject.optString("reg_plate_no"), jObject
														.optString("csd_id"),jObject
														.optString("display"));
										list.add(ragistration);
										
										
									}
									checkoutList = new ArrayList<Ragistration>();
									jsonArray= jsonObject
											.optJSONArray("checkedout_list");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jObject = jsonArray
												.optJSONObject(i);
										Ragistration ragistration = new Ragistration(
												jObject.optString("reg_plate_no"), jObject
														.optString("csd_id"),jObject
														.optString("display"));
										checkoutList.add(ragistration);
										
										
									}
									
									adapter = new ListViewAdapter(context, getDisplayList(list));
									

								} catch (Exception e) {
									
									e.printStackTrace();
									list = new ArrayList<Ragistration>();
									Ragistration ragistration = new Ragistration(
											"No Record Found", "","true");
									list.add(ragistration);
									adapter = new ListViewAdapter(context, list);
									
								}
								listView.setAdapter(adapter);
								listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
							}
						}
					}).execute(PdfInfo.newjobcard, "registration Plate");
			Log.e("AsyncWebServiceProcessingTask", result);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	@SuppressLint("DefaultLocale")
	private void getDetail(final String csId, final int identifire) {
		
		result = "";

		MultipartEntity entity = new MultipartEntity();
		try {
			entity.addPart("action", new StringBody("edit_vc"));
			entity.addPart("csd_id", new StringBody(csId));
			new AsyncWebServiceProcessingTask(context, entity,
					"Checking if entry already exists…", new AsynckCallback() {

						@Override
						public void run(final String result) {

							if (UIUtils.checkJson(result, context)) {
								JSONObject jsonObject;
								String status = null;
								try {
									jsonObject = new JSONObject(result);
									status = jsonObject.optString("status");
									if (status.equalsIgnoreCase("success"))

									{
										((CarAppSession) getApplication())
												.removeSavedData();
										JsonParser jsonParser = new JsonParser(
												context, new JSONObject(result));
										((CarAppSession) getApplication())
												.setCustomerData(jsonParser
														.getCustomerData());
										((CarAppSession) getApplication())
												.setWorkAssissment(jsonParser
														.getWorkAssissment());
										((CarAppSession) getApplication())
												.setJobData(jsonParser
														.getJobdata());
										Intent intent1 = new Intent(context,
												CustomerDataActivity.class);
                                        PdfInfo.csdId=csId;  
										if (identifire == 1) {
											PdfInfo.mode = PdfInfo.EDIT_MODE;
											startActivity(new Intent(context,
													LoginTemp.class));
											overridePendingTransition(
													R.anim.slide_in_up,
													R.anim.slide_out_up);
											carnoplate.setText("");
										}
									else if (identifire == 2) {
											PdfInfo.mode = PdfInfo.EXIT_MODE;
											startActivity(intent1);
											overridePendingTransition(
													R.anim.slide_in_up,
													R.anim.slide_out_up);
											carnoplate.setText("");
										}
                                  else if (identifire == 3) {
                                	  PdfInfo.mode = PdfInfo.CHECKOUT_MODE;
										startActivity(intent1);
										overridePendingTransition(
												R.anim.slide_in_up,
												R.anim.slide_out_up);
										carnoplate.setText("");
                                       }
									}
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}
						}
					}).execute(PdfInfo.newjobcard);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public class ListViewAdapter extends BaseAdapter {

		// Declare Variables
		Context mContext;
		LayoutInflater inflater;
		private List<Ragistration> adapteRagistration = null;
		private List<Ragistration> tempAdapteRagistration;

		public ListViewAdapter(Context context,
				List<Ragistration> adapteRagistration) {
			mContext = context;
			this.adapteRagistration = adapteRagistration;
			inflater = LayoutInflater.from(mContext);
			this.tempAdapteRagistration = new ArrayList<Ragistration>();
			this.tempAdapteRagistration.addAll(adapteRagistration);
		}

		public class ViewHolder {
			TextView plateNo;

		}

		@Override
		public int getCount() {
			return adapteRagistration.size();
		}

		@Override
		public Ragistration getItem(int position) {
			return adapteRagistration.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View view, ViewGroup parent) {
			final ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(R.layout.listview_item, null);
				holder.plateNo = (TextView) view.findViewById(R.id.plateno);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			// Set the results into TextViews
			holder.plateNo.setText(adapteRagistration.get(position)
					.getRegistrationPlateNumber());

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					selectedListItem =adapteRagistration.get(position)
							.getRegistrationPlateNumber();
					PdfInfo.csdId=adapteRagistration.get(position).getCsdId();
					
					carnoplate.setText(selectedListItem);
					carnoplate.setSelection(carnoplate
								.getText().length());
						
				}
			});

			return view; 
		}

		// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			adapteRagistration.clear();
			if (charText.length() == 0) {
				adapteRagistration.addAll(tempAdapteRagistration);
			} else {
				for (Ragistration wp : tempAdapteRagistration) {
					if (wp.getRegistrationPlateNumber()
							.toLowerCase(Locale.getDefault())
							.contains(charText)) {
						adapteRagistration.add(wp);
					}
				}
			}
			notifyDataSetChanged();
		}

	}
	private List<Ragistration> getDisplayList(List<Ragistration> reList) {
		List<Ragistration> reList2=new ArrayList<Ragistration>();
		for (Ragistration wp : reList) {
			if (wp.getDisplay().equals("yes")) {
				reList2.add(wp);
			}
		}
		
		return reList2;
		
	}
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		Util.showCustomDialog(context, "Message", "Do you want to exit", "Yes",
				"No", new com.example.tnutil.Callback() {

					@Override
					public void ok() {
						
						finish();
					}

					@Override
					public void cancel() {
						

					}
				});

	}
	private String checkRegistrationNo(String noPlate,List<Ragistration> list) {
		String  csId=null;
		for (Ragistration wp : list) {
			if (wp.getRegistrationPlateNumber().equalsIgnoreCase(noPlate)) {
				csId=wp.getCsdId();
				break;
			}
		}
	return csId;
	}
	
	
	
}