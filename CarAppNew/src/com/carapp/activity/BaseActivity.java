package com.carapp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.carappnew.R;

public abstract class BaseActivity extends Activity{
	
	protected Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		getActionBar().setIcon(R.drawable.ic_launcher);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		setTitleBar();
		
		
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
protected abstract void setTitleBar();


}
