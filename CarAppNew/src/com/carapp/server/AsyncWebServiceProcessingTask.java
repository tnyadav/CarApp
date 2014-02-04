package com.carapp.server;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.carapp.util.AsynckCallback;
import com.example.carappnew.R;

public class AsyncWebServiceProcessingTask extends
		AsyncTask<String, Void, String> {

	//private ProgressDialog bar;
	private Dialog dialog ;
	private Context context;
	private MultipartEntity entity;
	private String message;
	private AsynckCallback callback;

	// private int key;

	public AsyncWebServiceProcessingTask(Context context,
			MultipartEntity entity, String message, AsynckCallback callback) {

		this.context = context;
		// this.key=key;
		this.entity = entity;
		this.message = message;
		this.callback = callback;
	}

	@Override
	protected String doInBackground(String... params) {
		String responseData = "";

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);

			httppost.setEntity(entity);
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity getresponse = response.getEntity();

			responseData = EntityUtils.toString(getresponse);
			Log.e("responseData", responseData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseData;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		callback.run(result);
		dialog.dismiss();
	
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new Dialog(context,
				android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
		window.getAttributes().windowAnimations = R.style.PauseDialogAnimation;
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		WindowManager.LayoutParams wlp = window.getAttributes();
    	wlp.gravity = Gravity.CENTER;
    	window.setAttributes(wlp);
    	
		dialog.setContentView(R.layout.custom);
		TextView text = (TextView) dialog.findViewById(R.id.title);
		text.setText(message);
		dialog.show();
		
	}

}
