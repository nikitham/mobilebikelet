package com.sjsu.mobilebikelet;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.mobilebikelet.dto.Station;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CheckinActivity extends Activity {

	//public static String STATIONS = "STATIONS";
	//public static String COMMENTS = "SEARCH_TYPE";
	//public static String SEARCH_TYPE = "SEARCH_TYPE";
	public static List<Station> STATIONS = new ArrayList<Station>();
	
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin, menu);
		return true;
	}

}
