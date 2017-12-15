package com.compuware.ispw.restapi;

public class Constants {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static final String ISPW_DEBUG_MODE = "ispwDebugMode";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String Assignment = "Assignment";
	public static final String Release = "Release";
	
	public static final String Action = "Action";
	
	public static String SET_STATE_DISPATCHED = "Dispatched";
	public static String SET_STATE_EXECUTING = "Executing";
	public static String SET_STATE_CLOSED = "Closed";

	public static int POLLING_COUNT = 60;
	public static int POLLING_INTERVAL = 1500;

}
