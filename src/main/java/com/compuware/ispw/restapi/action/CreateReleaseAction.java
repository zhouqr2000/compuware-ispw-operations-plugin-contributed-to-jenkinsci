package com.compuware.ispw.restapi.action;

import com.compuware.ispw.model.rest.ReleaseInfo;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public class CreateReleaseAction extends AbstractPostAction {

	private static final String[] defaultProps = new String[] { application, stream, description,
			releaseId };

	private static final String contextPath = "/ispw/{srid}/releases";

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		ReleaseInfo releaseInfo = new ReleaseInfo();
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, releaseInfo);
	}

}
