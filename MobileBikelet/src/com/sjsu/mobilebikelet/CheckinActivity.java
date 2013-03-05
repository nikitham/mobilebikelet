package com.sjsu.mobilebikelet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CheckinActivity extends Activity {

	public static String STATIONS = "STATIONS";
	//public static String COMMENTS = "SEARCH_TYPE";
	//public static String SEARCH_TYPE = "SEARCH_TYPE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		Bundle extras = getIntent().getExtras();
		String searchType = extras.getString(STATIONS);
		System.out.println("Inside checkin activity"+searchType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin, menu);
		return true;
	}

}
