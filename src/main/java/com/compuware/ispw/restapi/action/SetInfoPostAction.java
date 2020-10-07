package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import com.compuware.ispw.model.rest.SetInfo;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * A generic SetInfo post action
 * 
 * @author Sam Zhou
 *
 */
public abstract class SetInfoPostAction extends GenericPostAction<SetInfo> {

	public SetInfoPostAction(PrintStream logger) {
		super(logger);
	}

	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken,
			String contextPath) {
		return super.getIspwRequestBean(SetInfo.class, srid, ispwRequestBody, webhookToken, contextPath);
	}
}
