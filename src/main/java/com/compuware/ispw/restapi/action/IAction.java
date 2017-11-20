package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public interface IAction {
	
	public static final String assignmentId = "assignmentId";
	public static final String level = "level";
	public static final String taskId = "taskId";
	public static final String releaseId = "releaseId";
	public static final String setId = "setId";
	
	public static final String runtimeConfiguration = "runtimeConfiguration";
	public static final String autoDeploy = "autoDeploy";
	public static final String changeType = "changeType";
	public static final String executionStatus = "executionStatus";
	public static final String dpenvlst = "dpenvlst";
	public static final String system = "system";
	public static final String httpHeaders = "httpHeaders";
	public static final String credentials = "credentials";
	public static final String eventsName = "events.name";
	public static final String eventsUrl = "events.url";
	public static final String eventsMethod = "events.method";
	public static final String eventsBody = "events.body";
	public static final String eventsHttpHeaders = "events.httpHeaders";
	public static final String eventsCredentials = "events.credentials";
	
	public static final String stream = "stream";
	public static final String application = "application";
	public static final String defaultPath = "defaultPath";
	public static final String description = "description";
	public static final String owner = "owner";
	public static final String assignmentPrefix = "assignmentPrefix";
	public static final String referenceNumber = "referenceNumber";
	public static final String userTag = "userTag";
	public static final String releasePrefix = "releasePrefix";
	
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken);
}
