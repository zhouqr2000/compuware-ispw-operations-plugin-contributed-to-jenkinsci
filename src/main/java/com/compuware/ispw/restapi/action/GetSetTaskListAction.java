package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Action to get task list in the specified Set
 * 
 * @author Steven Kansa
 *
 */
public class GetSetTaskListAction extends AbstractGetAction {

	private static final String[] defaultProps = new String[] { setId };
	private static final String contextPath = "/ispw/{srid}/sets/{setId}/tasks";

	public GetSetTaskListAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		List<String> pathTokens = Arrays.asList(defaultProps);
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, pathTokens);
	}

}
