package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "taskLoad")
public class TaskLoadResponse
{
	private String taskId;
	private String message;
	private String url;

	public TaskLoadResponse()
	{
	}

	public TaskLoadResponse(String taskId, String message, String url)
	{
		this.message = message;
		this.taskId = taskId;
		this.url = url;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
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