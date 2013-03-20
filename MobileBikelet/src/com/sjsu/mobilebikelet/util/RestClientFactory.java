package com.sjsu.mobilebikelet.util;

import android.content.SharedPreferences;


public class RestClientFactory {

	public static RestClient getAuthenticationClient(SharedPreferences pref) {
		return new RestClient (pref, ApplicationConstants.AUTHENTICATION_JSON_URL);
	}

	public static RestClient getListStationsByProgramClient(SharedPreferences pref) {
		return new RestClient (pref, ApplicationConstants.GET_STATIONS_BY_PROGRAM_URL);
	}
	
	public static RestClient getListBikesByStationClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.GET_BIKES_BY_STATION_URL);
	}
	
	public static RestClient getViewTransactionClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.GET_LAST_TRANSACTION_URL);
	}
	
	
	public static RestClient getTransactionClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.GET_TRANSACTION_URL);
	}
	
	public static RestClient checkinBikeClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.CHECKIN_URL);
	}

	public static RestClient checkoutBikeClient(SharedPreferences pref) {
        return new RestClient (pref, ApplicationConstants.CHECKOUT_URL);
	}

}
