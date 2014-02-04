package com.carapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.carapp.util.JobDataDetail;
import com.example.carappnew.R;

public class CustResonForVisitList extends Activity {

	private Intent i;
	private ListView listView;
	private Button selectall;
	private TextView headertext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		i = getIntent();

		listView = (ListView) findViewById(R.id.list_layout_list);
		selectall = (Button) findViewById(R.id.list_layout_selectall);
		selectall.setVisibility(View.GONE);
		headertext = (TextView) findViewById(R.id.list_layout_headertext);

		if (i.getIntExtra("listname", 0) == 0) {
			selectall.setVisibility(View.INVISIBLE);
			headertext.setVisibility(View.INVISIBLE);

			// setListAdapter(new ArrayAdapter<String>(this,
			// R.layout.list_item,ThirdScreen.alcust_reson_for_visit));
			listView.setAdapter(new ArrayAdapter<String>(this,
					R.layout.list_item, JobDataDetail.getResonForVisit()));
		} else if (i.getIntExtra("listname", 0) == 1) {
			selectall.setVisibility(View.INVISIBLE);
			headertext.setVisibility(View.INVISIBLE);
			// setListAdapter(new ArrayAdapter<String>(this,
			// R.layout.list_item,ThirdScreen.aldealer_recomendation));
			listView.setAdapter(new ArrayAdapter<String>(this,
					R.layout.list_item, JobDataDetail.getDealerRecomendation()));
		} else if (i.getIntExtra("listname", 0) == 2) {
			// setListAdapter(new ArrayAdapter<String>(this,
			// R.layout.list_item,ThirdScreen.alcust_approved_work));
			listView.setAdapter(new ArrayAdapter<String>(this,
					R.layout.list_item, JobDataDetail.getCustApprovedWork()));

		}

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) view).getText().toString();
				i.putExtra("position", ((TextView) view).getText().toString());
				// i.putExtra("position", position);
				setResult(RESULT_OK, i);

				finish();
			}

		});
		/*
		 * selectall.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub i.putExtra("position", "all"); // i.putExtra("position",
		 * position); setResult(RESULT_OK, i);
		 * 
		 * finish(); } });
		 */
	}

}
