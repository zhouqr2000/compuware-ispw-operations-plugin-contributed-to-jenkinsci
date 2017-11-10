package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.RestApiUtils;

public class PromoteAssignmentAction extends SetInfoPostAction {

	public static String[] defaultProps =
			new String[] { assignmentId, level, runtimeConfiguration };

	public static String contextPath =
			"/ispw/{srid}/assignments/{assignmentId}/tasks/promote?level={level}";

	public static String getDefaultProps() {
		return RestApiUtils.join(Constants.LINE_SEPARATOR, defaultProps, true);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

}
