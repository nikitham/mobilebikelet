package com.sjsu.mobilebikelet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeScreenActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
	

	ImageButton btn = (ImageButton) findViewById(R.id.checkinbutton);

	btn.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	checkInBike(v);
	    }
	});
	}

	// some more code

	public void checkInBike(View v) {
	    // does something very interesting
		Intent i = new Intent(HomeScreenActivity.this, CheckinActivity.class);
		startActivity(i);
	}
}
