package com.sjsu.mobilebikelet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sjsu.mobilebikelet.adapter.StationsByProgramAdapter;
import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Station;
import com.sjsu.mobilebikelet.dto.Transaction;
import com.sjsu.mobilebikelet.util.ApplicationConstants;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;

public class CheckinActivity extends Activity {

	public static String location;
	public static Long stationId;
	public final List<Station> STATIONS = new ArrayList<Station>();
	SharedPreferences prefs;
	public static Transaction UPDATEDTRANSACTION = new Transaction();
	
	String comments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		
		prefs = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
		Spinner s = (Spinner) findViewById(R.id.checkinstationspinner);
		
		final StationsByProgramAdapter adapter1 = new StationsByProgramAdapter(this, prefs, STATIONS);
		s.setAdapter(adapter1);
		
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

	        @Override
	        public void onItemSelected(AdapterView<?> parent,
	                View view, int pos, long id) {
	        	location = parent.getItemAtPosition(pos).toString();
	        	for (int i = 0; i < STATIONS.size() ;i++){
	    			if(STATIONS.get(i).getLocation().equalsIgnoreCase(location)){
	    				stationId = STATIONS.get(i).getId();
	    				break;
	    			}
	    		}
	        	System.out.println("Printing stationId ...... "+stationId);
	        	System.out.println("Printing location of the selected station :" +location);
	        	CheckStationTask gtask = new CheckStationTask();
	    		gtask.execute((Void) null);
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
		Station selectedStationInfo = null;
		//String[] stationinfo = new String[STATIONS.size()];
		for (int i = 0; i < STATIONS.size() ;i++){
			if(STATIONS.get(i).getLocation() == location){
				selectedStationInfo = STATIONS.get(i);
				break;
			}
			
		}
		System.out.println("selected station info is: "+selectedStationInfo);
		
		Button checkin = (Button) findViewById(R.id.checkinginbutton);
		checkin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText commentsEdited = (EditText) findViewById(R.id.inputcomments);
				comments = commentsEdited.getText().toString();
				RentTransaction rentTransaction = new RentTransaction();
				rentTransaction.setId(UPDATEDTRANSACTION.getTransaction().getId());
				rentTransaction.setBike(UPDATEDTRANSACTION.getTransaction().getBike());
				rentTransaction.setAccessKey(UPDATEDTRANSACTION.getTransaction().getAccessKey());
				rentTransaction.setComments(comments);
				rentTransaction.setFromStation(UPDATEDTRANSACTION.getTransaction().getFromStation());
				rentTransaction.setToStation(location);
				rentTransaction.setRentStartDate(UPDATEDTRANSACTION.getTransaction().getRentStartDate());
				rentTransaction.setStatus("Completed");
				rentTransaction.setRentEndDate(UPDATEDTRANSACTION.getTransaction().getRentEndDate());
				Transaction updtrans = new Transaction();
				updtrans.setTransaction(rentTransaction);
				
				System.out.println("Updated Transaction is :" +updtrans.getTransaction().getComments());
				//ViewTransactionActivity.STATUS = "Complete";

				UPDATEDTRANSACTION = updtrans;
				checkinUpdatedInfo(v);
				
			}
		});
		
		
		
		
	}
	
	class DataThread extends Thread {

		private static final String INNER_TAG = "getCheckinDetails";

		public void run() {

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory
					.checkinBikeClient(prefs);
			
			//client.addParam("transaction", UPDATEDTRANSACTION.toString());
			client.addParam("toStationId",location );
			client.addParam("comments",comments);
			client.addParam("fromJson", "From Json");
			

			try {
				client.execute(RequestMethod.PUT);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					
					//checkinUpdatedInfo(v);
				}
				else {
					//ViewTransactionActivity.STATUS = "Complete";
				}
				// return valid data
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}
		}
	}

	public void checkinUpdatedInfo(View v){
		new DataThread().start();
		Intent i = new Intent(CheckinActivity.this, HomeScreenActivity.class);
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin, menu);
		return true;
	}

	
	public class CheckStationTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {

			String isStationFull = "true";
			Boolean stationFull = true;
			final String INNER_TAG = "checkstationfull";

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);

			RestClient client = RestClientFactory
					.checkStationClient(prefs);
			client.addParam("stationId", stationId.toString());
			
			try {
				client.execute(RequestMethod.GET);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					return false;
				}

				isStationFull = client.getResponse();
				Log.i(INNER_TAG, isStationFull);
				
				JSONObject json = new JSONObject(isStationFull);
				isStationFull = (String) json.get("isStationFull");
				System.out.println("isStationFull is .......... "+isStationFull);

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			if(isStationFull.equalsIgnoreCase("true"))
				return true;
			else
				return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			Button checkin = (Button) findViewById(R.id.checkinginbutton);
			if (success) {
				Toast.makeText(CheckinActivity.this, "Station is full", Toast.LENGTH_LONG).show();
	        	checkin.setClickable(false);
//				finish();
			} else {
				Toast.makeText(CheckinActivity.this, "Station is not full", Toast.LENGTH_LONG).show();
	        	checkin.setClickable(true);
//				finish();
			}
		}

		@Override
		protected void onCancelled() {

		}
	}	
}
