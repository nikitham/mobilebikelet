package com.sjsu.mobilebikelet;

import com.sjsu.mobilebikelet.dto.RentTransaction;
import com.sjsu.mobilebikelet.dto.Transaction;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTransactionActivity extends Activity {

	public static RentTransaction viewTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("Detecting Problem   ...... 2 ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_transaction);
		Transaction transaction = new Transaction();
		transaction.setTransaction(viewTransaction);
		System.out.println("Detecting Problem   ...... 3 ");
		TextView bikeinfo = (TextView) findViewById(R.id.bikeinfo);
		bikeinfo.setText(transaction.getTransaction().getBike());
		
		TextView checkoutfrom = (TextView) findViewById(R.id.checkoutfrom);
		checkoutfrom.setText(transaction.getTransaction().getFromStation());
		
		TextView checkouttime = (TextView) findViewById(R.id.checkoutat);
		checkouttime.setText(transaction.getTransaction().getRentStartDate());
		
		TextView checkinto = (TextView) findViewById(R.id.checkinto);
		checkinto.setText(transaction.getTransaction().getToStation());
		
		TextView checkintime = (TextView) findViewById(R.id.checkinat);
		checkintime.setText(transaction.getTransaction().getRentEndDate());
		
		
		
		System.out.println("Detecting Problem   ...... 4 "+transaction.getTransaction().getRentEndDate());
		Button button = (Button) findViewById(R.id.okbutton);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				okTransaction(v);
				
			}

			
		});
			
	}
	
	private void okTransaction(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(ViewTransactionActivity.this, HomeScreenActivity.class);
		startActivity(i);
		
	}

}
