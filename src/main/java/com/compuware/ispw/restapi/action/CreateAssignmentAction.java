package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Create assignment action
 * @author Sam Zhou
 *
 */
public class CreateAssignmentAction extends AbstractPostAction {

	private static final String[] defaultProps = new String[] { stream, application, defaultPath,
			description, owner, assignmentPrefix };

	private static final String contextPath = "/ispw/{srid}/assignments";

	public CreateAssignmentAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		AssignmentInfo assignmentInfo = new AssignmentInfo();
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, assignmentInfo);
	}

}
