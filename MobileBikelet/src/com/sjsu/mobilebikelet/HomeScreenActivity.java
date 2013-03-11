package com.sjsu.mobilebikelet;

import com.sjsu.mobilebikelet.util.ApplicationConstants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	ImageButton btn2 = (ImageButton) findViewById(R.id.transactionbutton);

	btn.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	checkInBike(v);
	    }
	});
	
	btn2.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	clickViewTransaction(v);
	    }
	});
	}

	// some more code

	public void checkInBike(View v) {
	    // does something very interesting
		Intent i = new Intent(HomeScreenActivity.this, CheckinActivity.class);
		startActivity(i);
	}
	
	public void clickViewTransaction(View v) {
	    // does something very interesting
		Intent i = new Intent(HomeScreenActivity.this, ViewTransactionActivity.class);
		startActivity(i);
	}
	
	public void signOut(View v) {
		SharedPreferences settings = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.clear();
	    editor.commit();
		Intent i = new Intent(this, WelcomeActivity.class);
		startActivity(i);
	}
}
