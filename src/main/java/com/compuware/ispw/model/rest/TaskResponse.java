package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="task")
public class TaskResponse
{
	private String setId; 
	private String message;
	private String url;
		
	public TaskResponse()
	{
	}
	
	public TaskResponse(String setId, String message, String url)
	{
		this.message	=message;
		this.setId	=setId;
		this.url=url;
	}

	public String getSetId()
	{
		return setId;
	}

	public void setSetId(String setId)
	{
		this.setId = setId;
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