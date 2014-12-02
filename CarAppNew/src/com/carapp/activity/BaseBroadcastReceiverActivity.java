package com.carapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public abstract class BaseBroadcastReceiverActivity extends BaseActivity{

public static final String FINISH="finish";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(FINISH);
		registerReceiver(myBroadcastReceiver, filter);
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myBroadcastReceiver);
	}
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			finish();
		}
	};
}
