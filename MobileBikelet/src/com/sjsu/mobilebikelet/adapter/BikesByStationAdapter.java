package com.sjsu.mobilebikelet.adapter;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sjsu.mobilebikelet.dto.Bike;
import com.sjsu.mobilebikelet.dto.BikeListREST;
import com.sjsu.mobilebikelet.dto.Station;
import com.sjsu.mobilebikelet.dto.StationListREST;
import com.sjsu.mobilebikelet.util.RequestMethod;
import com.sjsu.mobilebikelet.util.RestClient;
import com.sjsu.mobilebikelet.util.RestClientFactory;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class BikesByStationAdapter extends BaseAdapter implements SpinnerAdapter {
	private final Context context;
	private List<Bike> bikes;
	private SharedPreferences preference;
	private Handler threadHandler;
	private Long stationId;
	
	public BikesByStationAdapter(Context ctx, SharedPreferences prefs, List<Bike> biks, Long statId) {
		this.context = ctx;
		preference = prefs;
		this.bikes = biks;
		this.stationId = statId;
	
		//this.stations = new ArrayList<Station>();
		
		// the thread handler for asynchronous fetching of data
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {
				bikes = (List<Bike>) msg.obj;
				if (bikes.size() == 0) {
					Toast.makeText(context,
							"No bikes found, please try again.",
							Toast.LENGTH_LONG).show();
				}

				notifyDataSetChanged();
				// activity.setProgressBarIndeterminateVisibility(false);
			}
		};

		new DataThread().start();

	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 Bike bike = bikes.get(position);
	     label.setText(bike.toString());

	     return label;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 Bike bike = bikes.get(position);
	     label.setText(bike.toString());

	     return label;
	}
	
	/*
	private void addTextView(ViewGroup viewGroup, String text){

		TextView textView = new TextView(viewGroup.getContext());
		textView.setTextSize(10);
		textView.setTextAppearance(context, R.style.TextFieldSmallStyle);
		textView.setText(text);
		textView.setTextColor(Color.WHITE);
		viewGroup.addView(textView);

	}
	*/
	public int getCount() {
		// TODO Auto-generated method stub
		return bikes.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bikes.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class DataThread extends Thread {

		private static final String INNER_TAG = "DataThread";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			RestClient client = RestClientFactory.getListBikesByStationClient(preference);

			try {
				client.addParam("stationId", stationId.toString());
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
				BikeListREST restResponse = gson.fromJson(
						jsonResult, BikeListREST.class);
				System.out.println("After fromJson");
				System.out
						.println("restResponse..............." + restResponse);
				if (restResponse.getBikes() != null) {
					bikes.addAll(restResponse.getBikes());
					System.out.println("Request Response output size .... "
							+ bikes.size());
				}

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = bikes;
			threadHandler.sendMessage(dataMsg);
		}
	}
	

}
