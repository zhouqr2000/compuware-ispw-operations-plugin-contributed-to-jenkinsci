package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import org.apache.commons.lang.StringUtils;
import com.compuware.ispw.model.rest.DeploymentResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Cancel a deployment based on request ID
 * 
 * @author Sam Zhou
 *
 */
public class CancelDeployment extends SetInfoPostAction
{

	private static final String[] defaultProps = new String[]{requestId, runtimeConfiguration};

	private static final String contextPath = "/ispw/{srid}/deployments/{requestId}/cancel";

	/**
	 * Constructor
	 * 
	 * @param logger
	 */
	public CancelDeployment(PrintStream logger)
	{
		super(logger);
	}

	/* (non-Javadoc)
	 * @see com.compuware.ispw.restapi.action.IAction#getIspwRequestBean(java.lang.String, java.lang.String, com.compuware.ispw.restapi.WebhookToken)
	 */
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken)
	{
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

	/* (non-Javadoc)
	 * @see com.compuware.ispw.restapi.action.IAction#startLog(java.io.PrintStream, com.compuware.ispw.restapi.IspwContextPathBean, java.lang.Object)
	 */
	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		String msg = String.format("Cancel the deployment with request ID %s", ispwContextPathBean.getRequestId());
		logger.println(msg);
	}

	/* (non-Javadoc)
	 * @see com.compuware.ispw.restapi.action.IAction#endLog(java.io.PrintStream, com.compuware.ispw.restapi.IspwRequestBean, java.lang.String)
	 */
	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		DeploymentResponse deployResp = new JsonProcessor().parse(responseJson, DeploymentResponse.class);
		logger.println("Job to cancel deployment with the request ID " + ispwRequestBean.getIspwContextPathBean().getRequestId()
				+ " is submitted. " + StringUtils.trimToEmpty(deployResp.getMessage()));

		return deployResp;
	}

}
