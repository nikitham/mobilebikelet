package com.sjsu.mobilebikelet;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Station;
import com.sjsu.mobilebikelet.dto.Transaction;
import com.sjsu.mobilebikelet.util.ApplicationConstants;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CheckinActivity extends Activity {

	public static String location;

	public static List<Station> STATIONS = new ArrayList<Station>();
	
	public static Transaction UPDATEDTRANSACTION = new Transaction();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		
		String[] array_spinner=new String[STATIONS.size()];
		for (int i = 0; i < array_spinner.length;i++){
			array_spinner[i] = STATIONS.get(i).getLocation();
		}
		Spinner s = (Spinner) findViewById(R.id.checkinstationspinner);
		ArrayAdapter adapter = new ArrayAdapter(this,
		android.R.layout.simple_spinner_item, array_spinner);
		s.setAdapter(adapter);
		
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

	        @Override
	        public void onItemSelected(AdapterView<?> parent,
	                View view, int pos, long id) {
	        	location = parent.getItemAtPosition(pos).toString();
	        	System.out.println("Printing location of the selected station :" +location);
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
				String comments = commentsEdited.toString();
				
				RentTransaction rentTransaction = new RentTransaction();
				rentTransaction.setId(UPDATEDTRANSACTION.getTransaction().getId());
				//UPDATEDTRANSACTION.getTransaction().getBike();
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
					.getRequestClient(prefs);
			
			client.addParam("transaction", UPDATEDTRANSACTION.toString());
			client.addParam("fromJson", "From Json");
			
//			Log.i(INNER_TAG, user_name);
			
//			client.addParam("petType", petType.getText().toString());
//			System.out.println("User Name is ... "+userName);
//			client.addParam("userName", userName);
//			client.addParam("message", message.getText().toString());
//			client.addParam("startDate", start_date.getText().toString());
//			client.addParam("endDate", end_date.getText().toString());
//			client.addParam("fromJson", "From Json");
			
			try {
				client.execute(RequestMethod.POST);

				if (client.getResponseCode() != 200) {
					// return server error
					Log.e(INNER_TAG, client.getErrorMessage());
					
					//checkinUpdatedInfo(v);
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

}
