package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;

import com.compuware.ispw.model.rest.AssignmentResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

public class CloseAssignmentAction extends SetInfoPostAction {

	private static final String[] defaultProps = new String[] { assignmentId, runtimeConfiguration };
	private static final String contextPath = "/ispw/{srid}/assignments/{assignmentId}/close";

	/**
	 * Constructor
	 * 
	 * @param logger
	 */
	public CloseAssignmentAction(PrintStream logger) {
		super(logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.compuware.ispw.restapi.action.IAction#getIspwRequestBean(java.lang.
	 * String, java.lang.String, com.compuware.ispw.restapi.WebhookToken)
	 */
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken) {
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.compuware.ispw.restapi.action.IAction#startLog(java.io.PrintStream,
	 * com.compuware.ispw.restapi.IspwContextPathBean, java.lang.Object)
	 */
	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject) {
		String msg = String.format("Close assignment %s", ispwContextPathBean.getAssignmentId());
		logger.println(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.compuware.ispw.restapi.action.IAction#endLog(java.io.PrintStream,
	 * com.compuware.ispw.restapi.IspwRequestBean, java.lang.String)
	 */
	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson) {
		AssignmentResponse assignResp = new JsonProcessor().parse(responseJson, AssignmentResponse.class);
		logger.println("Close assignment " + ispwRequestBean.getIspwContextPathBean().getAssignmentId()
				+ " is submitted. " + StringUtils.trimToEmpty(assignResp.getMessage()));

		return assignResp;
	}

}
