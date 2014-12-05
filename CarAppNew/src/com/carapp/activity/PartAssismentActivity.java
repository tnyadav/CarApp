package com.carapp.activity;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.PartAssisment;
import com.carapp.util.PdfInfo;
import com.example.carappnew.R;

public class PartAssismentActivity extends BaseBroadcastReceiverActivity {

	private LinearLayout container_partassisment;
	private LayoutInflater layoutInflater;
	private int index=0;
	private File file;
	private boolean imageTeken=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_parts_assessment);
		layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    container_partassisment=(LinearLayout)findViewById(R.id.container_partassisment);
		addNewView(index,null);
		
	}



	public void takePicture(String filename) {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			File path = new File(PdfInfo.path);
			if (!path.exists()) {
				path.mkdir();
			}
			String sysdate = android.text.format.DateFormat.format("ddMMyyyy",
					new java.util.Date()).toString();
			
			   file = new File(PdfInfo.path +
			  ((CarAppSession)getApplication
			  ()).getCustomerData().getRegistration() + "_" + filename + ".jpg");
			 
		//	file = new File(PdfInfo.path + filename + "_" + sysdate + ".jpg");
			Uri outputFileUri = Uri.fromFile(file);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			if (outputFileUri != null) {
				startActivityForResult(cameraIntent, 1111);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1111&& resultCode==RESULT_OK) {
			final View rowView=container_partassisment.getChildAt(index);
			final ImageView imageView =(ImageView)rowView.findViewById(R.id.imageView);
		    Button newPhoto=(Button)rowView.findViewById(R.id.newPhoto);
			newPhoto.setVisibility(View.VISIBLE);
			if (file!=null) {
				imageView.setImageBitmap(getPreview(file));
				imageTeken=true;
			}
			final RadioGroup radioGroup=(RadioGroup)rowView.findViewById(R.id.radioGroupWorkAssisment);
			
			newPhoto.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					imageView.setOnClickListener(null);
					for (int i = 0; i < radioGroup.getChildCount(); i++) {
						radioGroup.getChildAt(i).setEnabled(false);
						}
					CarAppSession carAppSession=(CarAppSession) getApplication();
					List<PartAssisment> partAssisments=carAppSession.getPartAssisments();
					if (partAssisments==null) {
						partAssisments=new ArrayList<PartAssisment>();
					}
					
				    RadioButton radioButton=(RadioButton)rowView.findViewById(radioGroup.getCheckedRadioButtonId());
					String radiovalue=radioButton.getText().toString();
					
					PartAssisment partAssisment=new PartAssisment();
					partAssisment.setValue(radiovalue);
					partAssisment.setOption("Option "+index);
					partAssisment.setImagePath(file.getAbsolutePath());
					partAssisments.add(partAssisment);
					carAppSession.setPartAssisments(partAssisments);
					Toast.makeText(context, partAssisment.toString(), Toast.LENGTH_LONG).show();
					index++;
					addNewView(index,v);
				}
			});
				}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.visit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		
		case R.id.action_done:
			if (imageTeken) {
				CarAppSession carAppSession=(CarAppSession) getApplication();
				List<PartAssisment> partAssisments=carAppSession.getPartAssisments();
				if (partAssisments==null) {
					partAssisments=new ArrayList<PartAssisment>();
				}
				
				final View rowView=container_partassisment.getChildAt(index);
				final RadioGroup radioGroup=(RadioGroup)rowView.findViewById(R.id.radioGroupWorkAssisment);
				RadioButton radioButton=(RadioButton)rowView.findViewById(radioGroup.getCheckedRadioButtonId());
				String radiovalue=radioButton.getText().toString();
			
				PartAssisment partAssisment=new PartAssisment();
				partAssisment.setValue(radiovalue);
				partAssisment.setOption("Option "+index);
				partAssisment.setImagePath(file.getAbsolutePath());
				partAssisments.add(partAssisment);
				carAppSession.setPartAssisments(partAssisments);
			}
		
			
			
			if (PdfInfo.mode==PdfInfo.EXIT_MODE) {
			
			Intent i = new Intent(context,JobDataExitActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
			
		}else if (PdfInfo.mode==PdfInfo.EDIT_MODE) {
			
			Intent i = new Intent(context,JobDataEditActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
			
		} else {

			Intent i = new Intent(context,JobDataCreateActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
		}
			break;
		}
		return true;
	}

	@Override
	protected void setTitleBar() {
		getActionBar().setTitle("Part Assessment");
	}
	private void addNewView(final int index,View addNew) {
		if (addNew!=null) {
			addNew.setVisibility(View.GONE);	
		}
		
		final View rowView = layoutInflater.inflate(R.layout.item_partassesment, null);
		ImageView imageView =(ImageView)rowView.findViewById(R.id.imageView);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				takePicture("PartAssessment"+(index+1));
			}
		});
		Button newPhoto=(Button)rowView.findViewById(R.id.newPhoto);
		
		newPhoto.setVisibility(View.GONE);
		container_partassisment.addView(rowView);
		
	}
	Bitmap getPreview(File file) {
	   
	    BitmapFactory.Options bounds = new BitmapFactory.Options();
	    bounds.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(file.getPath(), bounds);
	    if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
	        return null;

	    int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
	            : bounds.outWidth;

	    BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inSampleSize = originalSize / 100;
	    return BitmapFactory.decodeFile(file.getPath(), opts);     
	}
}