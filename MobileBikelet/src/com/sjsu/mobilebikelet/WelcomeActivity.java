package com.sjsu.mobilebikelet;

import com.sjsu.mobilebikelet.util.ApplicationConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        
    	SharedPreferences prefs = getSharedPreferences(
				ApplicationConstants.USER_PREF, 0);
    	String username = prefs.getString(ApplicationConstants.USERNAME, "");
    	Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Georgia.TTF");
    	TextView welcome = (TextView) findViewById(R.id.welcome);
    	welcome.setTypeface(tf);
    	TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
    	welcomeUser.setTypeface(tf);
    	Button signIn = (Button) findViewById(R.id.welcomeUserButton);
    	Button okayButton = (Button) findViewById(R.id.welcomeOKButton);
    	if (username == null || username.length() ==0) {
    		welcomeUser.setText("PLEASE SIGN IN");
    		okayButton.setVisibility(View.INVISIBLE); 
    		
    	} else {
    		welcomeUser.setText("Welcome " +  username + "!");
    		signIn.setText("Sign in as Different User");
    	}
    }
	
	public void onClickWelcome(View v) {
		Intent i = new Intent(this, HomeScreenActivity.class);
		startActivity(i);
    }
	
	public void onClickSetUser(View v) {
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
    }
	
}
