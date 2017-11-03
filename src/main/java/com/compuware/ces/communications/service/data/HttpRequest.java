package com.compuware.ces.communications.service.data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.NONE)
public class HttpRequest implements Serializable
{
	private static final long serialVersionUID = 7590599238471318174L;
	
	@XmlElement(name = "url")
	private String url;
	@XmlElement(name = "body")
	private String body;
	@XmlElement(name = "method")
	private String method = "POST";
	@XmlElement(name = "httpHeaders")
	private List<HttpHeader> httpHeaders;
	@XmlElement(name = "credentials")
	private BasicAuthentication credentials;

	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		this.method = method;
	}
	public List<HttpHeader> getHttpHeaders()
	{
		return httpHeaders;
	}
	public void setHttpHeaders(List<HttpHeader> httpHeaders)
	{
		this.httpHeaders = httpHeaders;
	}
	public BasicAuthentication getCredentials()
	{
		return credentials;
	}
	public void setCredentials(BasicAuthentication credentials)
	{
		this.credentials = credentials;
	}	
}
