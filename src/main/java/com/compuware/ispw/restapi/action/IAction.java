package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import com.compuware.ispw.restapi.HttpMode;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Common interface for all actions
 * 
 * @author Sam Zhou
 *
 */
@SuppressWarnings("nls")
public interface IAction {
	
	public static final String application = "application";
	public static final String assignmentId = "assignmentId";
	public static final String assignmentPrefix = "assignmentPrefix";
	public static final String autoDeploy = "autoDeploy";
	public static final String changeType = "changeType";
	public static final String containerId = "containerId";
	public static final String containerType = "containerType";
	public static final String credentials = "credentials";
	public static final String defaultPath = "defaultPath";
	public static final String description = "description";
	public static final String dpenvlst = "dpenvlst";
	public static final String endDate = "endDate";
	public static final String environment = "environment";
	public static final String eventsBody = "events.body";
	public static final String eventsCredentials = "events.credentials";
	public static final String eventsHttpHeaders = "events.httpHeaders";
	public static final String eventsMethod = "events.method";
	public static final String eventsName = "events.name";
	public static final String eventsUrl = "events.url";
	public static final String executionStatus = "executionStatus";
	public static final String group = "group";
	public static final String historical = "historical";
	public static final String httpHeaders = "httpHeaders";
	public static final String includeClosedContainers = "includeClosedContainers";
	public static final String inProgress = "inProgress";
	public static final String lastUpdatedBy = "lastUpdatedBy";
	public static final String level = "level";
	public static final String mname = "mname";
	public static final String mtype = "mtype";
	public static final String name = "name";
	public static final String operation = "operation";
	public static final String owner = "owner";
	public static final String path = "path";
	public static final String production = "production";
	public static final String referenceNumber = "referenceNumber";
	public static final String refNumber = "refNumber";
	public static final String releaseId = "releaseId";
	public static final String releasePrefix = "releasePrefix";
	public static final String requestId = "requestId";
	public static final String runtimeConfiguration = "runtimeConfiguration";
	public static final String setId = "setId";
	public static final String startDate = "startDate";
	public static final String stream = "stream";
	public static final String system = "system";
	public static final String tag = "tag";
	public static final String taskId = "taskId";
	public static final String userId = "userId";
	public static final String userTag = "userTag";
	public static final String action = "action";
	public static final String checkoutFromLevel = "checkoutFromLevel";
	public static final String checkout = "checkout";
	public static final String taskName = "taskName"; //TODO: need to update to mname/mtype to be consistent on CES side
	public static final String type = "type";
	
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken);
	
	public PrintStream getLogger();
	
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject);
	
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson);
	
	public HttpMode getHttpMode();
}
