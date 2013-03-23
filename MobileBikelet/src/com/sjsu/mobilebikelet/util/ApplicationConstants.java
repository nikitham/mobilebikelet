package com.sjsu.mobilebikelet.util;

public class ApplicationConstants {

	public static String USER_PREF = "USER_PREF";

//	public static String APPLICATION_HOST = "http://bikelet.cloudfoundry.com/";
	public static String APPLICATION_HOST = "http://bikeletsaas.cloudfoundry.com/";
//	public static String APPLICATION_HOST = "http://192.168.1.111:8080/bikelet/"; 
	
	public static String AUTHENTICATION_JSON_URL = APPLICATION_HOST
			+ "identity/authenticate.json";
	
	public static String GET_STATIONS_BY_PROGRAM_URL = APPLICATION_HOST
			+ "stations/listStationByProgram.json";
	
	public static String GET_LAST_TRANSACTION_URL = APPLICATION_HOST
			+ "renttransactions/viewtransaction.json";
	
	public static String GET_BIKES_BY_STATION_URL = APPLICATION_HOST
			+ "stations/listBikeByStation.json";

	public static String CHECKOUT_URL = APPLICATION_HOST
			+ "renttransactions/checkoutBike.json";
	
	public static String CHECKIN_URL = APPLICATION_HOST
			+ "renttransactions/checkinBike.json";

	public static String GET_TRANSACTION_URL = APPLICATION_HOST
			+ "renttransactions/gettransaction.json";
	
	public static String CHECK_STATIONFULL_URL = APPLICATION_HOST
			+ "stations/checkstationfull.json";
	
	public static String DATE_FORMAT = "MM/dd/yyyy";
	
	public static String DATE_FORMAT_PRETTY = "EEE, MMM d, yyyy";
	
	public static String USERNAME = "userName";
	public static String PASSWORD = "password";
	public static String USER_ROLE = "userRole";
	public static String USER_EMAIL= "userEmail";
	public static String USER_TENANT_ID= "userTenantID";
	public static String USER_PROGRAM_ID= "userTenantID";
}
