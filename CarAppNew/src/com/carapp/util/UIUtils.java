package com.carapp.util;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tnutil.Util;

public class UIUtils {
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState() == NetworkInfo.State.CONNECTING) {
			return true;
		} else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState() == NetworkInfo.State.CONNECTING) {
			return true;
		} else
			return false;
	}

	

	public static boolean checkJson(String result, Context context) {

		if (result == null || result.equals("")) {
			//showNetworkErrorMessage(context);
			Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry");
			return false;
		} else {
			try {
				new JSONObject(result);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Util.showCustomDialog(context, "Error", "Server not responds");
				
				return false;

			}

		}
	}
/*	public static void deleteFiles() {

		File file = new File(PdfInfo.path);

		if (file.exists()) {
			String deleteCmd = "rm -r " + PdfInfo.path;
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd);
			} catch (IOException e) {
			}
		}
	}*/
/*	public void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists() && !targetLocation.mkdirs()) {
				throw new IOException("Cannot create dir "
						+ targetLocation.getAbsolutePath());
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			// make sure the directory we plan to store the recording in exists
			File directory = targetLocation.getParentFile();
			if (directory != null && !directory.exists() && !directory.mkdirs()) {
				throw new IOException("Cannot create dir "
						+ directory.getAbsolutePath());
			}

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}*/
}
