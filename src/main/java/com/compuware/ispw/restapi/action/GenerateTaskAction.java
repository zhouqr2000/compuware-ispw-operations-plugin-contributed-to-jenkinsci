/**
 * (c) Copyright 2020 BMC Software, Inc.
 */
package com.compuware.ispw.restapi.action;

import java.io.IOException;
import java.io.PrintStream;
import com.compuware.ispw.model.rest.TaskResponse;
import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.Operation;
import com.compuware.ispw.restapi.util.RestApiUtils;
import hudson.FilePath;

public class GenerateTaskAction extends SetInfoPostAction
{
	private static final String[] defaultProps = new String[]{assignmentId, level, runtimeConfiguration};

	private static final String contextPath = "/ispw/{srid}/assignments/{assignmentId}/taskIds/generate?taskId={taskId}&level={level}";

	public static String getDefaultProps()
	{
		return RestApiUtils.join(Constants.LINE_SEPARATOR, defaultProps, true);
	}

	public GenerateTaskAction(PrintStream logger)
	{
		super(logger);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken)
	{
		IspwRequestBean bean = getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);

		String tempContextPath = bean.getContextPath();
		tempContextPath = tempContextPath.replaceAll(",", "&taskId=");
		bean.setContextPath(tempContextPath);

		return bean;
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		if (ispwContextPathBean.getTaskId() != null)
		{
			logger.println("ISPW: The generate process has started for task " + ispwContextPathBean.getTaskId());
		}
		else
		{
			logger.println(
					"ISPW: The generate process has started for tasks in assignment " + ispwContextPathBean.getAssignmentId());
		}
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		TaskResponse taskResp = new JsonProcessor().parse(responseJson, TaskResponse.class);
		if (taskResp.getSetId() == null && !taskResp.getMessage().trim().isEmpty())
		{
			logger.println("ISPW: " + taskResp.getMessage());
		}
		else
		{
			logger.println("ISPW: Set " + taskResp.getSetId() + " - created to generate tasks "
					+ ispwRequestBean.getIspwContextPathBean().getTaskId());
		}

		return taskResp;

	}

	@Override
	public String preprocess(String ispwRequestBody, FilePath pathToParmFile, PrintStream logger)
			throws IOException, InterruptedException
	{
		String automaticRegex = "(?i)(?m)(^(?!#)(.+)?generateautomatically.+true(.+)?$)";
		return super.preprocess(automaticRegex, ispwRequestBody, pathToParmFile, logger, getIspwOperation().getDescription(),
				getIspwOperation().getPastTenseDescription());
	}

	@Override
	public Operation getIspwOperation()
	{
		return Operation.GENERATE;
	}
}
