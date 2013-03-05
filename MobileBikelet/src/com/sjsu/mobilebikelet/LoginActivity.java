package com.sjsu.mobilebikelet;

import java.util.List;

import com.google.gson.Gson;
import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Station;
import com.sjsu.mobilebikelet.dto.Transaction;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;
import com.sjsu.mobilebikelet.util.ApplicationConstants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	Transaction transactions = new Transaction();
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String bikeuser;
	private String mPassword;

	// UI references.
	private EditText username;
	private EditText password;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		bikeuser = getIntent().getStringExtra(EXTRA_EMAIL);
		username = (EditText) findViewById(R.id.email);
		username.setText(bikeuser);

		password = (EditText) findViewById(R.id.password);
		password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		SharedPreferences settings = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(ApplicationConstants.USERNAME, username.getText().toString());
	    editor.putString(ApplicationConstants.PASSWORD, password.getText().toString());
	        
	     editor.commit();
		    
//		Intent i = new Intent(this, MainActivity.class);
//		startActivity(i);
//		if (mAuthTask != null) {
//			return;
//		}

		// Reset errors.
		username.setError(null);
		password.setError(null);

		// Store values at the time of the login attempt.
		bikeuser = username.getText().toString();
		mPassword = password.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			password.setError(getString(R.string.error_field_required));
			focusView = password;
			cancel = true;
		} else if (mPassword.length() < 4) {
			password.setError(getString(R.string.error_invalid_password));
			focusView = password;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(bikeuser)) {
			username.setError(getString(R.string.error_field_required));
			focusView = username;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			System.out.println("Check!!!!!!!!!!!!!!!!!!!");

			
			mAuthTask = new UserLoginTask();
//			
			mAuthTask.execute((Void) null);
			
			
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}


			final String INNER_TAG = "getBikeDetails";

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory
					.getRequestClient(prefs);
			
//			Log.i(INNER_TAG, user_name);

			client.addParam("username", username.getText().toString());
			client.addParam("password", password.getText().toString());

			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					
					Log.e(INNER_TAG, client.getErrorMessage());
				}

				// return valid data
				String jsonResult = client.getResponse();
				Log.i(INNER_TAG, jsonResult);
				Gson gson = new Gson();
				System.out.println("Before fromJson");
				Transaction restResponse = gson.fromJson(
					      jsonResult, Transaction.class);
					    System.out.println("After fromJson");
					    System.out.println("restResponse..............." + restResponse.getTransaction().getAccessKey());
					    List<Station> listofstations = restResponse.getTransaction().getStationlist();
					    System.out.println("The Stations are : "+listofstations.get(0).getLocation());
					    if (restResponse!=null){
					    	transactions = restResponse;
					    	
					    }
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			System.out.println("Transaction Info "+transactions.getTransaction().getAccessKey());
			Intent i = new Intent(LoginActivity.this, CheckinActivity.class);
			i.putExtra(CheckinActivity.STATIONS,transactions.getTransaction().getStatus());
			//Bundle b = new Bundle();
			//i.putExtra("transactions", transactions);
			startActivity(i); 
			if (success) {
				finish();
			} else {
				password.setError(getString(R.string.error_incorrect_password));
				password.requestFocus();
			}
			
			
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
