package com.compuware.ispw.restapi.action;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public interface IAction {
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken);
}
