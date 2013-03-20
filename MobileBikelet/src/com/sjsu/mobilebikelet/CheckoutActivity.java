package com.sjsu.mobilebikelet;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.sjsu.mobilebikelet.CheckinActivity.DataThread;
import com.sjsu.mobilebikelet.adapter.BikesByStationAdapter;
import com.sjsu.mobilebikelet.adapter.StationsByProgramAdapter;
import com.sjsu.mobilebikelet.dto.Bike;
import com.sjsu.mobilebikelet.dto.BikeListREST;
import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Station;
import com.sjsu.mobilebikelet.dto.Transaction;
import com.sjsu.mobilebikelet.util.ApplicationConstants;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;

public class CheckoutActivity extends Activity {

	private Spinner stationSpinner;
	private Spinner bikeSpinner;
	SharedPreferences prefs;
	final List<Station> stations = new ArrayList<Station>();
	final List<Bike> bikes = new ArrayList<Bike>();
	private RentTransaction transaction;
	private Handler threadHandler;
	public static Transaction CHECKEDOUTTRANSACTION = new Transaction();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final String INNER_TAG = "CheckoutActivity";
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		

		prefs = getSharedPreferences(ApplicationConstants.USER_PREF, 0);
	
		stationSpinner = (Spinner) findViewById(R.id.checkoutstationspinner);
		bikeSpinner = (Spinner) findViewById(R.id.checkoutbikespinner);
		
		final StationsByProgramAdapter adapter = new StationsByProgramAdapter(this, prefs, stations);
        stationSpinner.setAdapter(adapter);
        
        
     // on click go to the view item widget
        stationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
     			public void onItemSelected(AdapterView<?> parent, View view,
     					int position, long id) {
     				Station station = (Station) parent.getItemAtPosition(position);
    				
    				bikes.clear();
    				BikesByStationAdapter adapter = new BikesByStationAdapter(CheckoutActivity.this, prefs, bikes, station.getId());
    				bikeSpinner.setAdapter(adapter);

     			}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
        });
        
        
        Button checkout = (Button) findViewById(R.id.checkingoutbutton);
        checkout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				checkoutBike();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

	
	private void checkoutBike() {
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {
				transaction = (RentTransaction) msg.obj;
				CheckoutActivity.this.setProgressBarIndeterminateVisibility(false);
				
				DialogInterface.OnClickListener closeListener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(CheckoutActivity.this, HomeScreenActivity.class);
						startActivity(i);
					}
				};
				
				new AlertDialog.Builder(CheckoutActivity.this).setTitle("Bike checked-out.").setMessage("Access key: " + transaction.getAccessKey()).setNeutralButton("Close", closeListener).show(); 

			}
		};

		this.setProgressBarIndeterminateVisibility(true);
		new DataThread().start();
	}

	
	class DataThread extends Thread {

		private static final String INNER_TAG = "checkOutBike";

		public void run() {

			SharedPreferences prefs = getSharedPreferences(
					ApplicationConstants.USER_PREF, 0);
			
			RestClient client = RestClientFactory.checkoutBikeClient(prefs);
			
			int selectedStationIdx = stationSpinner.getSelectedItemPosition();
			System.out.println("Selected station index:" + selectedStationIdx);
			Station station = stations.get(selectedStationIdx);			
			System.out.println("Station selected:" + station.getLocation());
			
			int selectedBikeIdx = bikeSpinner.getSelectedItemPosition();
			System.out.println("Selected bike index:" + selectedBikeIdx);
			Bike bike = bikes.get(selectedBikeIdx);
			System.out.println("Bike selected:" + bike.getBikeType());
						
			EditText commentsEdited = (EditText) findViewById(R.id.checkoutcomments);
			String comments = commentsEdited.getText().toString();
			
			System.out.println("Checkout comment:" + comments);
			
			client.addParam("fromStationId", station.getId().toString() );
			client.addParam("bikeId", bike.getId().toString());
			client.addParam("comments", comments);

			try {
				client.execute(RequestMethod.POST);

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
				System.out
						.println("restResponse..............." + restResponse);
				if (restResponse.getTransaction() != null) {
					transaction = restResponse.getTransaction();
					//ViewTransactionActivity.STATUS = transaction.getStatus();
					
				}
			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}
			
			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = transaction;
			threadHandler.sendMessage(dataMsg);
		}
	}
}
