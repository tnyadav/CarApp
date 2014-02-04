package com.carapp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.activity.CustomerDataActivity;
import com.carapp.activity.MainActivity;
import com.carapp.bean.CarAppSession;
import com.carapp.util.PdfInfo;
import com.example.carappnew.R;

public class UploadFile extends AsyncTask<String, Integer, String> {

	Context context;
	private Dialog dialog;
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	String serverResponseMessage = "";
	FileInputStream fileInputStream;
	String pathToOurFile;
	int totalsize;
	String[] filesname;
	private CarAppSession carAppSession;

	public UploadFile(Context context, String pathToOurFile,CarAppSession carAppSession) {
		this.context = context;
		this.pathToOurFile = pathToOurFile;
		this.carAppSession=carAppSession;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
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
		text.setText("Uploading pdf...\n Please wait...");
		dialog.show();
		
		
		File dir = new File(pathToOurFile);
		File[] dirname = dir.listFiles();
		totalsize = dirname.length;
		filesname = new String[totalsize];

		for (int i = 0; i < dirname.length; i++) {
			filesname[i] = dirname[i].getAbsolutePath();

			Log.i("upload file", "filepath" + filesname[i]);
			
		}
		Log.i("upload file", "length" + totalsize); 
		

	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				PdfInfo.UploadFiles);

		try {
			MultipartEntity entity = new MultipartEntity();

			entity.addPart("email", new StringBody(carAppSession.getCustomerData().getEmail()));
			entity.addPart("dirname", new StringBody(PdfInfo.client));
			entity.addPart("foldername", new StringBody(PdfInfo.branch));
			entity.addPart("subfoldername", new StringBody(
					carAppSession.getCustomerData().getRegistration()));
			entity.addPart("totalfile", new StringBody("" + totalsize));
			for (int i = 0; i < totalsize; i++) {
				entity.addPart("file" + i, new FileBody(new File(filesname[i])));
			}

			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity getresponse = response.getEntity();
			String responseData = EntityUtils.toString(getresponse);
			try {
				JSONObject json = new JSONObject(responseData);
				if (json.getString("status").equals("success")) {
					serverResponseMessage = json.getString("msg");
				} else {
					serverResponseMessage = "Problem in uploading file";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				serverResponseMessage = "Server not responds";
			}

			Log.i("upload response", responseData);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			serverResponseMessage = "Server not responds";
			
		} catch (IOException e) {
			e.printStackTrace();
			serverResponseMessage = "Server not responds";
			
		}catch (Exception e) {
			e.printStackTrace();
			serverResponseMessage = "Server not responds";
			
		}

		return serverResponseMessage;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.dismiss();
		if (serverResponseMessage.equals("file uploaded successfully.")) {
			Toast.makeText(context, serverResponseMessage, Toast.LENGTH_LONG)
					.show();
			((Activity) context).finish();
			MainActivity.mainactivity.finish();
			CustomerDataActivity.customerdata.finish();
		} else {
			Toast.makeText(context, serverResponseMessage, Toast.LENGTH_LONG)
					.show();
			//((Activity) context).finish();
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);

	}

}
