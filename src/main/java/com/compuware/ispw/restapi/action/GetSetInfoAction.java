package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Action to get a set status
 * 
 * @author Sam Zhou
 *
 */
public class GetSetInfoAction extends AbstractGetAction {

	private static final String[] defaultProps = new String[] { setId };
	private static final String contextPath = "/ispw/{srid}/sets/{setId}";
	
	public GetSetInfoAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		List<String> pathTokens = Arrays.asList(defaultProps);
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, pathTokens);
	}

}
