package com.compuware.ispw.restapi.action;

import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public class CreateAssignmentAction extends AbstractPostAction {

	public static String[] defaultProps = new String[] { stream, application, defaultPath,
			description, owner, assignmentPrefix };

	public static String contextPath = "/ispw/{srid}/assignments";

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		AssignmentInfo assignmentInfo = new AssignmentInfo();
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, assignmentInfo);
	}

}
