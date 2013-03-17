package com.sjsu.mobilebikelet.util;

public class ApplicationConstants {

	public static String USER_PREF = "USER_PREF";

	public static String APPLICATION_HOST = "http://10.0.2.2:8080";
	
	public static String AUTHENTICATION_JSON_URL = APPLICATION_HOST
			+ "/bikelet/identity/authenticate.json";
	
	public static String GET_STATIONS_BY_PROGRAM_URL = APPLICATION_HOST
			+ "/bikelet/stations/listStationByProgram.json";
	
	public static String GET_BIKES_BY_STATION_URL = APPLICATION_HOST
			+ "/bikelet/stations/listBikeByStation.json";

	public static String CHECKOUT_URL = APPLICATION_HOST
			+ "/bikelet/renttransactions/checkoutBike.json";
	
	public static String CHECKIN_URL = APPLICATION_HOST
			+ "/bikelet/renttransactions/checkin";

	public static String SEND_REQUEST_JSON_URL = APPLICATION_HOST
			+ "/bikelet/renttransactions/gettransaction.json";
	
	public static String DATE_FORMAT = "MM/dd/yyyy";
	
	public static String DATE_FORMAT_PRETTY = "EEE, MMM d, yyyy";
	
	public static String USERNAME = "userName";
	public static String PASSWORD = "password";
	public static String USER_ROLE = "userRole";
	public static String USER_EMAIL= "userEmail";
	public static String USER_TENANT_ID= "userTenantID";
	public static String USER_PROGRAM_ID= "userTenantID";
}
