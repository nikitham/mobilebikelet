package com.sjsu.mobilebikelet;

import com.google.gson.Gson;
import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Transaction;
import com.sjsu.mobilebikelet.dto.UserREST;
import com.sjsu.mobilebikelet.util.ApplicationConstants;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class HomeScreenActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
	}

	// some more code
	public void checkOutBike(View v) {
	    // does something very interesting
		ValidateCheckoutTask validateTask = new ValidateCheckoutTask();
		validateTask.execute((Void) null);
	}
	
	
	public void checkInBike(View v) {
	    // does something very interesting
		GetTransactionTask gtask = new GetTransactionTask();
		gtask.execute((Void) null);
	}
	
	public void clickViewTransaction(View v) {
	    // does something very interesting
		ViewTransactionTask gtask = new ViewTransactionTask();
		gtask.execute((Void) null);
		
	}
	
	public void clickViewStatistics(View v) {
		// does something very interesting
		Intent i = new Intent(HomeScreenActivity.this, ViewStatsFormActivity.class);
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
	
	public class GetTransactionTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			RentTransaction rtrans = new RentTransaction();
			Transaction trans = new Transaction();
			final String INNER_TAG = "gettransaction";

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);

			RestClient client = RestClientFactory
					.getTransactionClient(prefs);

			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					return false;
				}

				String jsonResult = client.getResponse();
				Log.i(INNER_TAG, jsonResult);
				Gson gson = new Gson();
				trans = gson.fromJson(jsonResult, Transaction.class);
				rtrans = trans.getTransaction();
				CheckinActivity.UPDATEDTRANSACTION = trans;

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			if(rtrans != null)
				return true;
			else
				return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				Intent i = new Intent(HomeScreenActivity.this, CheckinActivity.class);
				startActivity(i);
				
				finish();
			} else {
				Toast.makeText(HomeScreenActivity.this, "No Bike Checked out", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onCancelled() {

		}
	}
	
	public class ValidateCheckoutTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			RentTransaction rtrans = new RentTransaction();
			Transaction trans = new Transaction();
			final String INNER_TAG = "gettransaction";

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);

			RestClient client = RestClientFactory
					.getTransactionClient(prefs);

			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					return false;
				}

				String jsonResult = client.getResponse();
				Log.i(INNER_TAG, jsonResult);
				Gson gson = new Gson();
				trans = gson.fromJson(jsonResult, Transaction.class);
				rtrans = trans.getTransaction();

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			if(rtrans == null)
				return true;
			else
				return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				Intent i = new Intent(HomeScreenActivity.this, CheckoutActivity.class);
				startActivity(i);

				
				finish();
			} else {
				Toast.makeText(HomeScreenActivity.this, "Please checkin the already checked out bike before checking out new bike.", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onCancelled() {

		}
	}
	
	public class ViewTransactionTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			RentTransaction rtrans = new RentTransaction();
			Transaction trans = new Transaction();
			final String INNER_TAG = "viewtransaction";

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);

			RestClient client = RestClientFactory
					.getViewTransactionClient(prefs);

			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					return false;
				}

				String jsonResult = client.getResponse();
				Log.i(INNER_TAG, jsonResult);
				Gson gson = new Gson();
				trans = gson.fromJson(jsonResult, Transaction.class);
				rtrans = trans.getTransaction();
				System.out.println("Detecting Problem   ...... 1 ");
				CheckinActivity.UPDATEDTRANSACTION = trans;
				ViewTransactionActivity.viewTransaction = rtrans;

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			if(rtrans != null){
				System.out.println("Detecting Problem   ...... 121212 ");
				return true;
			}
			else
				return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				System.out.println("Detecting Problem   ...... 131313 ");
				Intent i = new Intent(HomeScreenActivity.this, ViewTransactionActivity.class);
				System.out.println("Detecting Problem   ...... 14141414 ");
				startActivity(i);
			}

		}

		@Override
		protected void onCancelled() {

		}
	}

}
