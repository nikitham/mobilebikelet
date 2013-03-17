package com.sjsu.mobilebikelet.adapter;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
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


public class StationsByProgramAdapter extends BaseAdapter implements SpinnerAdapter {
	private final Context context;
	private List<Station> stations;
	private SharedPreferences preference;
	private Handler threadHandler;
	
	public StationsByProgramAdapter(Context ctx, SharedPreferences prefs, List<Station> stats) {
		this.context = ctx;
		preference = prefs;
		this.stations = stats;
	
		//this.stations = new ArrayList<Station>();
		
		// the thread handler for asynchronous fetching of data
		threadHandler = new Handler() {

			public void handleMessage(Message msg) {
				stations = (List<Station>) msg.obj;
				if (stations.size() == 0) {
					Toast.makeText(context,
							"No stations found, please try again.",
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
	     label.setText(stations.get(position).getLocation());

	     return label;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
	     label.setText(stations.get(position).getLocation());

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
		return stations.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return stations.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class DataThread extends Thread {

		private static final String INNER_TAG = "DataThread";

		public void run() {

			Log.i(INNER_TAG, "Start parsing items");

			RestClient client = RestClientFactory.getListStationsByProgramClient(preference);

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
				StationListREST restResponse = gson.fromJson(
						jsonResult, StationListREST.class);
				System.out.println("After fromJson");
				System.out
						.println("restResponse..............." + restResponse);
				if (restResponse.getStations() != null) {
					stations.addAll(restResponse.getStations());
					System.out.println("Request Response output size .... "
							+ stations.size());
				}

			} catch (Exception e) {
				Log.e(INNER_TAG, e.toString());
			}

			Log.i(INNER_TAG,
					"Done parsing items, send a message to the handler");

			// Send the parsing result to the handler.
			Message dataMsg = threadHandler.obtainMessage();
			dataMsg.obj = stations;
			threadHandler.sendMessage(dataMsg);
		}
	}
	

}
