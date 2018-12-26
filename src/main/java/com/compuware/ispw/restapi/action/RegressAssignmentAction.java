package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import com.compuware.ispw.model.rest.TaskResponse;
import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.RestApiUtils;

/**
 * Action to regress an assignment
 * 
 * @author Sam Zhou
 *
 */
public class RegressAssignmentAction extends SetInfoPostAction {

	private static final String[] defaultProps =
			new String[] { assignmentId, level, runtimeConfiguration };

	private static final String contextPath =
			"/ispw/{srid}/assignments/{assignmentId}/tasks/regress?level={level}&mname={mname}&mtype={mtype}";

	public static final String getDefaultProps() {
		return RestApiUtils.join(Constants.LINE_SEPARATOR, defaultProps, true);
	}

	public RegressAssignmentAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		logger.println("Regressing Assignment " + ispwContextPathBean.getAssignmentId()
		+ " at level " + ispwContextPathBean.getLevel());
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		TaskResponse taskResp = new JsonProcessor().parse(responseJson, TaskResponse.class);
		logger.println("Set "+taskResp.getSetId()+" created to regress Assignment "+ispwRequestBean.getIspwContextPathBean().getAssignmentId());
		
		return taskResp;
	}

}
