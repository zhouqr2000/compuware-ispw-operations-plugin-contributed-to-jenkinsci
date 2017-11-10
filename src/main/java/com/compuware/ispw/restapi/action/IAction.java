package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public interface IAction {
	
	public static String assignmentId = "assignmentId";
	public static String level = "level";
	public static String taskId = "taskId";
	public static String releaseId = "releaseId";
	public static String setId = "setId";
	
	public static String runtimeConfiguration = "runtimeConfiguration";
	public static String autoDeploy = "autoDeploy";
	public static String changeType = "changeType";
	public static String executionStatus = "executionStatus";
	public static String httpHeaders = "httpHeaders";
	public static String credentials = "credentials";
	public static String eventsName = "events.name";
	public static String eventsUrl = "events.url";
	public static String eventsMethod = "events.method";
	public static String eventsBody = "events.body";
	public static String eventsHttpHeaders = "events.httpHeaders";
	public static String eventsCredentials = "events.credentials";
	
	public static String stream = "stream";
	public static String application = "application";
	public static String defaultPath = "defaultPath";
	public static String description = "description";
	public static String owner = "owner";
	public static String assignmentPrefix = "assignmentPrefix";
	public static String referenceNumber = "referenceNumber";
	public static String userTag = "userTag";
	
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken);
}
