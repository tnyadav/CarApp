package com.carapp.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.carapp.util.IgnoreCaseComparator;
import com.carapp.util.JobDataDetail;
import com.example.carappnew.R;

public class CustResonForVisitListActivity extends BaseActivity {

	private Intent i;
	private ListView listView;
	/*private Button selectall;
	private TextView headertext;*/
	List<String> dataList;
	private  String []selectedItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custresonforvisitlist);
		i = getIntent();

		listView = (ListView) findViewById(R.id.list_layout_list);
		//selectall = (Button) findViewById(R.id.list_layout_selectall);
		//selectall.setVisibility(View.GONE);
		//headertext = (TextView) findViewById(R.id.list_layout_headertext);

		if (i.getIntExtra("listname", 0) == 0) {
			
			/*selectall.setVisibility(View.INVISIBLE);
			headertext.setVisibility(View.INVISIBLE);*/
			dataList = JobDataDetail.getResonForVisit();
			
		} else if (i.getIntExtra("listname", 0) == 1) {

			/*selectall.setVisibility(View.INVISIBLE);
			headertext.setVisibility(View.INVISIBLE);*/
            dataList = JobDataDetail.getDealerRecomendation();
		} else if (i.getIntExtra("listname", 0) == 2) {
			
			dataList= JobDataDetail.getCustApprovedWork();

		}
		IgnoreCaseComparator icc = new IgnoreCaseComparator();
		java.util.Collections.sort(dataList,icc);
		
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_item, dataList));
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 SparseBooleanArray a = listView.getCheckedItemPositions();
				  selectedItem=new String[a.size()];
	                for (int i = 0; i < a.size(); i++) {
	                    if (a.valueAt(i)) {
	                    	selectedItem[i]=(String) listView.getAdapter().getItem(i);
	                    }
	                }
	
			
			}

		});
		
	/*	selectall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				i.putExtra("position", selectedItem);
				setResult(RESULT_OK, i);
				finish();
			}
		});*/
		 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.visit, menu);
	   if (i.getIntExtra("listname", 2) == 0) {
		getActionBar().setTitle("Customer Approved Work");
	      }else {
	    		getActionBar().setTitle("");
	    }
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		
		case R.id.action_done:
			
			i.putExtra("position", selectedItem);
			setResult(RESULT_OK, i);
			finish();
		
			break;
		}
		return true;
	}
	@Override
	public void onBackPressed() {
		i.putExtra("position", selectedItem);
		setResult(RESULT_OK, i);
		super.onBackPressed();
	}
@Override
protected void setTitleBar() {
//getActionBar().setTitle("Customer Approved Work");
	
}
}
