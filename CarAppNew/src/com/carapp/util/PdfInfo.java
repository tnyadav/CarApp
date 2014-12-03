package com.carapp.util;

import android.os.Environment;

public class PdfInfo {

	public static String path = Environment.getExternalStorageDirectory()
			+ "/CarTemp/";

	public static String name;
	public static String client = "client";
	public static String branch = "branch";
	public static String carnoplate = "unknowne";
	public static String csdId = "";
	public static final int EDIT_MODE = 1;
	public static final int EXIT_MODE = 2;
	public static final int CHECKOUT_MODE = 3;

	public static String baseUrl = "http://techsoftlabs.com/carapp/mobile_webservices/";
	/*
	 * public static String dateaddress =
	 * "http://carapp.dyndns.org/carapp/mobile_webservices/date.php"; public
	 * static String dayaddress =
	 * "http://carapp.dyndns.org/carapp/mobile_webservices/day.php";
	 */
	public static String dateaddress = baseUrl + "date.php";
	public static String dayaddress = baseUrl + "day.php";
	public static String newjobcard = baseUrl + "mobile_webservice.php";
	public static final String RegistrationList = baseUrl + "getlist.php";
	public static final String getfleet = baseUrl + "get_fleets.php";
	// public static final String UploadFiles =
	// "http://techsoftlabs.com/carapp/mobile_webservices/carapp_uploadfiles.php";
	public static final String UploadFiles = "http://techsoftlabs.com/taskdev/carapp/carapp_uploadfiles.php";

	public static int mode = 0;
	public static boolean display = true;

	public enum status {
		PDFCREATED, DATABASEUPDATED, FILESUPLOADED
	}

}
