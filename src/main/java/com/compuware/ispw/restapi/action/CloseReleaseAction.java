package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;

import com.compuware.ispw.model.rest.ReleaseResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

public class CloseReleaseAction extends SetInfoPostAction {

	private static final String[] defaultProps = new String[] { releaseId, runtimeConfiguration };
	private static final String contextPath = "/ispw/{srid}/releases/{releaseId}/close";

	/**
	 * Constructor
	 * 
	 * @param logger
	 */
	public CloseReleaseAction(PrintStream logger) {
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
		String msg = String.format("Close release %s", ispwContextPathBean.getReleaseId());
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
		ReleaseResponse releaseResp = new JsonProcessor().parse(responseJson, ReleaseResponse.class);
		logger.println("Close release " + ispwRequestBean.getIspwContextPathBean().getReleaseId() + " is submitted. "
				+ StringUtils.trimToEmpty(releaseResp.getMessage()));

		return releaseResp;
	}

}
