package com.compuware.ispw.restapi;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IspwRequestBean {
	private String contextPath = StringUtils.EMPTY;
	private String jsonRequest = "{}"; //empty JSON

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
