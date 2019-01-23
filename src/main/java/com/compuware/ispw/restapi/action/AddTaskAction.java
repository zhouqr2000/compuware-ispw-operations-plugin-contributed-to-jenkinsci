package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import com.compuware.ispw.model.rest.AddTaskInfo;
import com.compuware.ispw.model.rest.AddTaskResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Add task to assignment with checkout action
 * 
 * @author Sam Zhou
 *
 */
public class AddTaskAction extends GenericPostAction<AddTaskInfo> {
	private static final String[] defaultProps = new String[] { stream, application, checkoutFromLevel, owner, path,
			releaseId, runtimeConfiguration, taskName, type };

	private static final String contextPath = "/ispw/{srid}/assignments/{assignmentId}/task/add?checkout={checkout}";

	public AddTaskAction(PrintStream logger) {
		super(logger);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken) {
		return super.getIspwRequestBean(AddTaskInfo.class, srid, ispwRequestBody, webhookToken, contextPath);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject) {
		AddTaskInfo addTaskInfo = (AddTaskInfo) jsonObject;
		String msg = String.format("adding task %s.%s to assignment %s (checkout:%s)", addTaskInfo.getTaskName(),
				addTaskInfo.getType(), ispwContextPathBean.getAssignmentId(), ispwContextPathBean.getCheckout());

		logger.println(msg);
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson) {
		AddTaskResponse addResp = new JsonProcessor().parse(responseJson, AddTaskResponse.class);

		String msg = String.format("Set %s created to add task to assignment %s", addResp.getSetId(),
				ispwRequestBean.getIspwContextPathBean().getAssignmentId());
		logger.println(msg);

		return addResp;
	}

}
