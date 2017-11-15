package com.compuware.ispw.restapi.action;

import java.util.Arrays;
import java.util.List;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public class GetReleaseTaskListAction extends AbstractGetAction {

	public static String[] defaultProps = new String[] { releaseId, level };
	public static String contextPath = "/ispw/{srid}/releases/{releaseId}/tasks?level={level}";

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		List<String> pathTokens = Arrays.asList(defaultProps);
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, pathTokens);
	}

}
