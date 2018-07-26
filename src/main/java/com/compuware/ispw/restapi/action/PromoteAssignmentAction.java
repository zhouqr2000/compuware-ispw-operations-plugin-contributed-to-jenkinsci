package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.RestApiUtils;

/**
 * Action to promote an assignment
 * 
 * @author Sam Zhou
 *
 */
public class PromoteAssignmentAction extends SetInfoPostAction {

	private static final String[] defaultProps =
			new String[] { assignmentId, level, runtimeConfiguration };

	private static final String contextPath =
			"/ispw/{srid}/assignments/{assignmentId}/tasks/promote?level={level}&mname={mname}&mtype={mtype}";

	public static final String getDefaultProps() {
		return RestApiUtils.join(Constants.LINE_SEPARATOR, defaultProps, true);
	}

	public PromoteAssignmentAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

}
