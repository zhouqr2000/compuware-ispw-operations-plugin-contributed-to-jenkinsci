package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import com.compuware.ispw.model.rest.TaskInfo;
import com.compuware.ispw.model.rest.TaskLoadResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Loads a task to an assignment.
 */
@SuppressWarnings("nls")
public class TaskLoadAction extends GenericPostAction<TaskInfo>
{
	private static final String[] defaultProps = new String[]{assignmentId, stream, application, currentLevel, moduleName,
			moduleType, startingLevel, userId};

	private static final String contextPath = "/ispw/{srid}/assignments/{assignmentId}/tasks";

	public TaskLoadAction(PrintStream logger)
	{
		super(logger);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken)
	{
		return super.getIspwRequestBean(TaskInfo.class, srid, ispwRequestBody, webhookToken, contextPath);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		TaskInfo addTaskInfo = (TaskInfo) jsonObject;
		String msg = String.format("loading task %s.%s to assignment %s", addTaskInfo.getModuleName(),
				addTaskInfo.getModuleType(), ispwContextPathBean.getAssignmentId());

		logger.println(msg);
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		TaskLoadResponse taskLoadResp = new JsonProcessor().parse(responseJson, TaskLoadResponse.class);

		String msg = String.format("Task with ID %s loaded to assignment %s", taskLoadResp.getTaskId(),
				ispwRequestBean.getIspwContextPathBean().getAssignmentId());
		logger.println(msg);

		return taskLoadResp;
	}

}
