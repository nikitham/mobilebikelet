package com.sjsu.mobilebikelet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		
		Button b = (Button) findViewById(R.id.gohome);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ViewStatsActivity.this, HomeScreenActivity.class);
				startActivity(i);
			}
		});
	}
}
