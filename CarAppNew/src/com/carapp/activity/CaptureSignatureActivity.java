package com.carapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.util.PdfInfo;
import com.example.carappnew.R;

public class CaptureSignatureActivity extends Activity {
	signature mSignature;
	Paint paint;
	LinearLayout mContent;
	Button clear, save;
	TextView part3;
	boolean isdraw = false;
	public static byte[] byteSignature = new byte[0];
	String title = "SalePerson's Signature";
	String signaturepath;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.capturesignature);
		intent = getIntent();
		String For = intent.getStringExtra("For");
		if (For.equals("customer")) {
			title = "Customer's Signature";
			// signaturepath=PdfInfo.path+"PhotoCustSIG.jpg";
			signaturepath = PdfInfo.path + "PhotoCustSIG.jpeg";
		} else {
			// signaturepath=PdfInfo.path+"PhotoSaleSIG.jpg";
			signaturepath = PdfInfo.path + "PhotoSaleSIG.jpeg";

		}
		clear = (Button) findViewById(R.id.clear);
		save = (Button) findViewById(R.id.createpdf);
		part3 = (TextView) findViewById(R.id.title);
		part3.setText(title);
		mContent = (LinearLayout) findViewById(R.id.mysignature);

		mSignature = new signature(this, null);
		mContent.addView(mSignature);
		save.setOnClickListener(onButtonClick);
		clear.setOnClickListener(onButtonClick);
		part3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	Button.OnClickListener onButtonClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == clear) {
				mSignature.clear();
			} else if (v == save) {

				if (!isdraw) {
					Toast.makeText(getApplicationContext(),
							"Draw Signature First", Toast.LENGTH_SHORT).show();
				} else {
					mSignature.save();
					setResult(RESULT_OK, intent);
					finish();

				}

			}
		}
	};

	public class signature extends View {
		static final float STROKE_WIDTH = 10f;
		static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		Paint paint = new Paint();
		Path path = new Path();

		float lastTouchX;
		float lastTouchY;
		final RectF dirtyRect = new RectF();

		public signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public void clear() {
			path.reset();
			invalidate();
			isdraw = false;
			// save.setEnabled(false);
		}

		public void save() {
			Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
					mContent.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(returnedBitmap);
			Drawable bgDrawable = mContent.getBackground();
			if (bgDrawable != null)
				bgDrawable.draw(canvas);
			else
				canvas.drawColor(Color.WHITE);
			mContent.draw(canvas);

			File path = new File(PdfInfo.path);
			if (!path.exists()) {
				path.mkdir();
			}

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(signaturepath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			returnedBitmap.compress(Bitmap.CompressFormat.PNG, 95, fos);

		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			// save.setEnabled(true);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// isdraw=true;
				path.moveTo(eventX, eventY);
				lastTouchX = eventX;
				lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:
				isdraw = true;
			case MotionEvent.ACTION_UP:

				resetDirtyRect(eventX, eventY);
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					path.lineTo(historicalX, historicalY);
				}
				path.lineTo(eventX, eventY);
				break;
			}

			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		private void resetDirtyRect(float eventX, float eventY) {
			dirtyRect.left = Math.min(lastTouchX, eventX);
			dirtyRect.right = Math.max(lastTouchX, eventX);
			dirtyRect.top = Math.min(lastTouchY, eventY);
			dirtyRect.bottom = Math.max(lastTouchY, eventY);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1000 && (resultCode == RESULT_OK)) {

			String s = data.getStringExtra("selection");
			part3.setText(s);

		}
	}
}