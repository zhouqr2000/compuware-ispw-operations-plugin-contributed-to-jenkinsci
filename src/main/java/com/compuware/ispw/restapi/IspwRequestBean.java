package com.compuware.ispw.restapi;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ISPW request bean to pass object/JSON back and forth
 * 
 * @author Sam Zhou
 *
 */
public class IspwRequestBean {
	
	private IspwContextPathBean ispwContextPathBean = null;
	private Object jsonObject = null;
	private String contextPath = StringUtils.EMPTY;
	private String jsonRequest = "{" + Constants.LINE_SEPARATOR + "}"; // empty JSON

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
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

	public Object getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(Object jsonObject) {
		this.jsonObject = jsonObject;
	}

	public IspwContextPathBean getIspwContextPathBean() {
		return ispwContextPathBean;
	}

	public void setIspwContextPathBean(IspwContextPathBean ispwContextPathBean) {
		this.ispwContextPathBean = ispwContextPathBean;
	}

}
