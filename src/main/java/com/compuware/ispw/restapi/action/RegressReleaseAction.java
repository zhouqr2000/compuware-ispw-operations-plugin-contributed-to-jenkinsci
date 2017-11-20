package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.RestApiUtils;

public class RegressReleaseAction extends SetInfoPostAction {

	private static final String[] defaultProps =
			new String[] { releaseId, level };

	private static final String contextPath =
			"/ispw/{srid}/releases/{releaseId}/tasks/regress?level={level}";

	public static final String getDefaultProps() {
		return RestApiUtils.join(Constants.LINE_SEPARATOR, defaultProps, true);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

}
