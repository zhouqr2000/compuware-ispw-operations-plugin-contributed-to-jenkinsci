package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public interface IAction {
	
	public static String assignmentId = "assignmentId";
	public static String level = "level";
	public static String taskId = "taskId";
	public static String releaseId = "releaseId";
	public static String setId = "setId";
	
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken);
}
