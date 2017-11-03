package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="release")
public class ReleaseResponse
{
	private String releaseId; 
	private String message;
	private String url;
		
	public ReleaseResponse()
	{
	}
	
	public ReleaseResponse(String releaseId, String message, String url)
	{
		this.message	=message;
		this.releaseId	=releaseId;
		this.url=url;
	}

	public String getReleaseId()
	{
		return releaseId;
	}

	public void setReleaseId(String releaseId)
	{
		this.releaseId = releaseId;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	
}
