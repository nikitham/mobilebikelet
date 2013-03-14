package com.sjsu.mobilebikelet.util;

public class ApplicationConstants {

	public static String USER_PREF = "USER_PREF";

	public static String APPLICATION_HOST = "http://10.0.2.2:8080";
	
	public static String AUTHENTICATION_JSON_URL = APPLICATION_HOST
			+ "/bikelet/identity/authenticate.json";

	public static String PETSITTER_SEARCH_JSON_URL = APPLICATION_HOST
			+ "/petsitter/search/simple.json";
	
	public static String CHECKIN_URL = APPLICATION_HOST
			+ "/bikelet/renttransactions/checkin";

	
	public static String PETSITTER_PETDETAIL_URL = APPLICATION_HOST
			+ "/petsitter/petdetails/simple.json";

	public static String SEND_REQUEST_JSON_URL = APPLICATION_HOST
			+ "/bikelet/renttransactions/gettransaction.json";
	
	public static String REQUESTS_URL = APPLICATION_HOST
			+ "/petsitter/requests/requestresponse.json";
	
	public static String REQUESTS_PICKER_URL = APPLICATION_HOST
			+ "/petsitter/requests/simple.json";	
	
	public static String UPLOAD_FILE_URL = APPLICATION_HOST
			+ "/petsitter/uploadfile";
	
	public static String DOWNLOAD_FILE_URL = APPLICATION_HOST
	+ "/petsitter/uploadfile";

	public static String DATE_FORMAT = "MM/dd/yyyy";
	
	public static String DATE_FORMAT_PRETTY = "EEE, MMM d, yyyy";
	
	public static String USERNAME = "userName";
	public static String PASSWORD = "password";
	public static String USER_ROLE = "userRole";
	public static String USER_EMAIL= "userEmail";
	public static String USER_TENANT_ID= "userTenantID";
	public static String USER_PROGRAM_ID= "userTenantID";
}
