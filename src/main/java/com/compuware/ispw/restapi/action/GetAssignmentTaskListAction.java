package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Action to list all tasks in the specified assignment
 * 
 * @author Sam Zhou
 *
 */
public class GetAssignmentTaskListAction extends AbstractGetAction {

	private static final String[] defaultProps = new String[] { assignmentId, level };
	private static final String contextPath =
			"/ispw/{srid}/assignments/{assignmentId}/tasks?level={level}";

	public GetAssignmentTaskListAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		List<String> pathTokens = Arrays.asList(defaultProps);
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, pathTokens);
	}

}
