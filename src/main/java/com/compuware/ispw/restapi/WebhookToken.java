package com.compuware.ispw.restapi;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted;

public class WebhookToken implements Serializable {
	private static final long serialVersionUID = 1;

	private final String token;
	private final String url;

	public WebhookToken(String token, String url) {
		this.token = token;
		this.url = url;
	}

	@Whitelisted
	public String getToken() {
		return token;
	}

	@Whitelisted
	public String getURL() {
		return url;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
