package com.carapp.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.carappnew.R;

public abstract class BaseActivity extends Activity{

	protected Activity activity;
	
	
   @Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		getActionBar().setIcon(R.drawable.ic_launcher);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.black));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity = this;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
			this.onBackPressed();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
