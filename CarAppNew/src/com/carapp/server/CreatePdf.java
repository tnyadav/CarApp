package com.carapp.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.carapp.bean.CarAppSession;
import com.carapp.bean.CustomerData;
import com.carapp.bean.JobData;
import com.carapp.bean.WorkAssissment;
import com.carapp.util.PdfInfo;
import com.example.carappnew.R;
import com.example.tnutil.Callback;
import com.example.tnutil.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf extends AsyncTask<String, Void, String> {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
			Font.BOLD);

	private static String SECONT_PAGE_TITLE = "Car Assessment";

	Context context;
	private Dialog dialog;
	String filename, filepath, childfolder;
	static Image image1;
	String action;
	private CarAppSession carAppSession;
	private CustomerData customerData;
	private WorkAssissment workAssissment;
	private JobData jobData;

	public CreatePdf(Context context, String action,CarAppSession carAppSession) {
		this.context = context;
		this.action = action;
		this.carAppSession=carAppSession;
		customerData=this.carAppSession.getCustomerData();
		workAssissment=this.carAppSession.getWorkAssissment();
		jobData=this.carAppSession.getJobData();
		if (PdfInfo.mode == PdfInfo.EXIT_MODE) {
			SECONT_PAGE_TITLE = "Work Assessment";
		}
	}

	private static void addMetaData(Document document) {
		document.addTitle("CAR APP PDF");
		document.addSubject("Information");
		document.addKeywords("Java, PDF, Android");

	}

	private  void addContent(Document document) throws DocumentException {

		// New Page***********************************************************
		document.newPage();
		Paragraph subPara = new Paragraph("Customer Data", catFont);
		addEmptyLine(subPara, 2);

		subPara.add(new Paragraph("          Branch- "+ customerData.getBranch() 
				              + "\n          Salesperson- "+ customerData.getSaleperson() 
				              + "\n          Company- "
				+ customerData.getCompany() + "\n          Customer- "
				+ customerData.getCustomer() + "\n          Contact number- "
				+ customerData.getContactNo() + "\n          Email- "
				+ customerData.getEmail() + "\n          Address- "
				+ customerData.getAddress() + "\n          Make- "
				+ customerData.getMake() + "\n          Model- "
				+ customerData.getModel() + "\n          Year- "
				+ customerData.getYear() + "\n          Odometer- "
				+ customerData.getOdomstrer()
				+ "\n          Registration plate no.- "
				+ customerData.getRegistration() + "\n          Date- "
				+ customerData.getDate() + "\n          Time- "
				+ customerData.getTime()+ "\n          Customer resion for visit- " 
				+customerData.getCust_resonfor_visit(),smallBold));

		subPara.setAlignment(Element.ALIGN_CENTER);
		document.add(subPara);

		// ***************************************************************************************************************************************
		// New Page***********************************************************
		document.newPage();
		subPara = new Paragraph(SECONT_PAGE_TITLE, catFont);
		addEmptyLine(subPara, 2);

		subPara.add(new Paragraph("          Tyre Condition:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getTyer_condition_lf()
				+ "\n            Left Back- "
				+ workAssissment.getTyer_condition_lb()
				+ "\n            Right Front- "
				+ workAssissment.getTyer_condition_rf()
				+ "\n            Right Back- "
				+ workAssissment.getTyer_condition_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Tyre size:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getTyre_size_lf() + "\n            Left Back- "
				+ workAssissment.getTyre_size_lb() + "\n            Right Front- "
				+ workAssissment.getTyre_size_rf() + "\n            Right Back- "
				+ workAssissment.getTyre_size_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Tyre depth:- ", subFont));
		subPara.add(new Paragraph("            Left Front-  "
				+ workAssissment.getTyre_depth_lf()
				+ "\n            Left Back-  " + workAssissment.getTyre_depth_lb()
				+ "\n            Right Front-  "
				+ workAssissment.getTyre_depth_rf()
				+ "\n            Right Back-  " + workAssissment.getTyre_depth_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Brake Pads:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getBrake_pad_lf() + "\n            Left Back- "
				+ workAssissment.getBrake_pad_lb() + "\n            Right Front- "
				+ workAssissment.getBrake_pad_rf() + "\n            Right Back- "
				+ workAssissment.getBrake_pad_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Brake Disks:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getBrake_disk_lf() + "\n            Left Back- "
				+ workAssissment.getBrake_disk_lb() + "\n            Right Front- "
				+ workAssissment.getBrake_disk_rf() + "\n            Right Back- "
				+ workAssissment.getBrake_disk_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Shockers:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getShocker_lf() + "\n            Left Back- "
				+ workAssissment.getShocker_lb() + "\n            Right Front- "
				+ workAssissment.getShocker_rf() + "\n            Right Back- "
				+ workAssissment.getShocker_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Wheels:- ", subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getWheel_lf() + "\n            Left Back- "
				+ workAssissment.getWheel_lb() + "\n            Right Front- "
				+ workAssissment.getWheel_rf() + "\n            Right Back- "
				+ workAssissment.getWheel_rb(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("          Physical Damage (Tyers):- ",
				subFont));
		subPara.add(new Paragraph("            Left Front- "
				+ workAssissment.getPhysical_damage_lf()
				+ "\n            Left Back- "
				+ workAssissment.getPhysical_damage_lf()
				+ "\n            Right Front- "
				+ workAssissment.getPhysical_damage_rf()
				+ "\n            Right Back- "
				+ workAssissment.getPhysical_damage_rb(), smallBold));
		addEmptyLine(subPara, 1);

		subPara.add(new Paragraph("            Immobilizer:- "
				+ workAssissment.getImmoblizer_f() + "\n            Battery- "
				+ workAssissment.getBattery_f() + "\n            Physical Damage:- "
				+ workAssissment.getPhysical_damage_f(), smallBold));
		addEmptyLine(subPara, 1);
		subPara.add(new Paragraph("            Spare Wheel:- "
				+ workAssissment.getSpare_wheel_b() + "\n             Lock Nut:- "
				+ workAssissment.getLock_nut_b()
				+ "\n            Physical Damage:- "
				+ workAssissment.getPhysical_damage_b(), smallBold));
		addEmptyLine(subPara, 1);

		subPara.setAlignment(Element.ALIGN_CENTER);
		document.add(subPara);

		// ******************************************************************************************************************************************
		document.newPage();
		subPara = new Paragraph("Job Data", catFont);
		addEmptyLine(subPara, 2);

		if (PdfInfo.mode != PdfInfo.EXIT_MODE) {


			subPara.add(new Paragraph("Dealer Recommendations:", catFont));
			subPara.add(new Paragraph("\n"
					+ jobData.getDealer_recommendations(), smallBold));

			subPara.add(new Paragraph("Customer Approved Work:", catFont));
			subPara.add(new Paragraph("\n" + jobData.getCust_approved_work(),
					smallBold));

			subPara.add(new Paragraph("Dealer Quotation R "
					+ jobData.getQuotation1() + ".00", smallBold));
			subPara.add(new Paragraph("Customer Approved Quotation R "
					+ jobData.getQuotation2() + ".00", smallBold));
			subPara.add(new Paragraph("Radio Data - "
					+ jobData.getRadiodata(), smallBold));
			subPara.add(new Paragraph("Valuables removed from vehicle - "
					+ jobData.getValuablesRemovedFromVehicle(), smallBold));
		} else {

			subPara.add(new Paragraph("Job Details:", catFont));
			subPara.add(new Paragraph("\n" + jobData.getCust_approved_work(),
					smallBold));

			subPara.add(new Paragraph("Customer Invoice Amoun R "
					+ jobData.getQuotation2() + ".00", smallBold));
			subPara.add(new Paragraph(
					"Radio Data -" + jobData.getRadiodata(), smallBold));

			// ******************new data******************************
			subPara.add(new Paragraph("Observations "
					+ jobData.getObservations(), smallBold));

			subPara.add(new Paragraph("Value-Added Services:", catFont));

			subPara.add(new Paragraph("Tyre Pressure Front "
					+ jobData.getTyre_pressure_front(), smallBold));
			subPara.add(new Paragraph("Tyre Pressure Fack "
					+ jobData.getTyre_pressure_back(), smallBold));
			subPara.add(new Paragraph("Tyres Polished "
					+ jobData.getTyres_polished(), smallBold));
			subPara.add(new Paragraph("Wheel Nuts Torqued "
					+ jobData.getWheel_nuts_torqued(), smallBold));
			subPara.add(new Paragraph("Wheels Cleaned "
					+ jobData.getWheels_cleaned(), smallBold));
			subPara.add(new Paragraph("Wheels Balanced "
					+ jobData.getWheels_balanced(), smallBold));
			subPara.add(new Paragraph("Alignment Done "
					+ jobData.getAlignment_done(), smallBold));
			subPara.add(new Paragraph("Lock Nut Returned "
					+ jobData.getLock_nut_returned(), smallBold));
			subPara.add(new Paragraph("Car Tested By Salesperson "
					+ jobData.getCar_tested_by_salesperson(), smallBold));
			subPara.add(new Paragraph("Work Inspected By Salesperson "
					+ jobData.getWork_inspected_by_salesperson(), smallBold));
			subPara.add(new Paragraph("Work Approved By Salesperson "
					+ jobData.getWork_approved_by_salesperson(), smallBold));
			subPara.add(new Paragraph("Customer Satisfied "
					+ jobData.getCustomer_satisfied(), smallBold));
		}

		// ******************new data******************************

		subPara.setAlignment(Element.ALIGN_CENTER);
		document.add(subPara);

		// ***********************************************************************************************************************************

		// New Page***********************************************************
		if (PdfInfo.mode != PdfInfo.EDIT_MODE) {

			document.newPage();
			subPara = new Paragraph("Signatures", catFont);
			addEmptyLine(subPara, 2);
			subPara.add(new Paragraph("Customer Signature", smallBold));
			addEmptyLine(subPara, 1);
			try {
				image1 = Image.getInstance(PdfInfo.path + "PhotoCustSIG.jpeg");
				image1.scaleAbsolute(400, 400);
				image1.setAlignment(Element.ALIGN_CENTER);
				subPara.add(image1);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			addEmptyLine(subPara, 1);
			subPara.add(new Paragraph("SalePerson Signature", catFont));
			addEmptyLine(subPara, 1);
			try {
				image1 = Image.getInstance(PdfInfo.path + "PhotoSaleSIG.jpeg");
				image1.scaleAbsolute(400, 400);
				image1.setAlignment(Element.ALIGN_CENTER);
				subPara.add(image1);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// *******************************************************************************************************************************

		File dir = new File(PdfInfo.path);
		File[] dirname = dir.listFiles();

		// ArrayList<String> filesname = new ArrayList<String>();
		for (int i = 0; i < dirname.length; i++) {
			if (dirname[i].getAbsolutePath().endsWith(".jpg")) {

				// filesname.add(dirname[i].getAbsolutePath());
				Log.d("filepath", dirname[i].getAbsolutePath());
				try {
					addEmptyLine(subPara, 1);
					subPara.add(new Paragraph("" + dirname[i].getName(),
							catFont));
					addEmptyLine(subPara, 1);
					image1 = Image.getInstance(dirname[i].getAbsolutePath());
					image1.scaleAbsolute(350, 350);
					image1.setAlignment(Element.ALIGN_CENTER);
					subPara.add(image1);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		document.add(subPara);

		// **************************************************************************************************************************************
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
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
		text.setText("Creating pdf...\n Please wait...");
		dialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		try {

			Document document = new Document();
			filepath = PdfInfo.path;
			File fp = new File(filepath);
			if (!fp.exists()) {
				fp.mkdirs();
			}
			if (PdfInfo.mode == PdfInfo.EXIT_MODE) {
				filename = filepath
						+ customerData.getRegistration()
						+ "_"
						+ android.text.format.DateFormat.format("ddMMyyyy",
								new java.util.Date()).toString() + "_OUT.pdf";
			} else if (PdfInfo.mode == PdfInfo.EDIT_MODE) {
				filename = filepath
						+ customerData.getRegistration()
						+ "_"
						+ android.text.format.DateFormat.format("ddMMyyyy",
								new java.util.Date()).toString() + "_EDIT.pdf";
			} else {
				filename = filepath
						+ customerData.getRegistration()
						+ "_"
						+ android.text.format.DateFormat.format("ddMMyyyy",
								new java.util.Date()).toString() + "_IN.pdf";
			}

			PdfWriter.getInstance(document, new FileOutputStream(filename));
			document.open();
			addMetaData(document);

			addContent(document);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("pdf", "" + e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);
		dialog.dismiss();
		Util.showCustomDialog(context, "Pdf Created",
				"Upload Data to server ? ", "YES", "NO", new Callback() {

					@Override
					public void ok() {

						new UpdateDataBase(context, action,carAppSession).execute("");
					}

					@Override
					public void cancel() {

					}
				});

	}

}