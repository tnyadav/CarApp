package com.carapp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CaptureScreen {
	
	View screen;
	Context context;
	
	public CaptureScreen(View screen, Context context) {
		super();
		this.screen = screen;
		this.context = context;
	}

	public void save() {
		
		Bitmap returnedBitmap = Bitmap.createBitmap(screen.getDrawingCache());
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = screen.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		screen.draw(canvas);

		File path = new File(PdfInfo.path);
		if (!path.exists()) {
			path.mkdir();
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(PdfInfo.path+"cap.jpeg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		returnedBitmap.compress(Bitmap.CompressFormat.PNG, 95, fos);

	}
}
