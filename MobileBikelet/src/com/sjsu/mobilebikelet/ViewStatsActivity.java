package com.sjsu.mobilebikelet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewStatsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_statistics);
		
		
		TextView caloriesTextView = (TextView) findViewById(R.id.calorieValueId);
		TextView co2Saved = (TextView) findViewById(R.id.co2ValueId);
		caloriesTextView.setText(getIntent().getExtras().get("caloriesBurned").toString());
		co2Saved.setText(getIntent().getExtras().get("co2Saved").toString());
		
	}
}
