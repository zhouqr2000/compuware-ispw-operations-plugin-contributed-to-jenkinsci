package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "addTaskResponse")
public class AddTaskResponse
{
	private String taskName;
	private String message;
	private String url;
	private String setId;

	public AddTaskResponse()
	{
	}

	/**
	 * Alternate constructor initializing fields.
	 * 
	 * @param taskName
	 *            The task name
	 * @param message
	 *            The message
	 * @param url
	 *            The URL
	 * @param setId
	 *            The set ID
	 */
	public AddTaskResponse(String taskName, String message, String url, String setId)
	{
		this.message = message;
		this.taskName = taskName;
		this.url = url;
		this.setId = setId;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
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

	/**
	 * Gets the set ID
	 * 
	 * @return the setId
	 */
	public String getSetId()
	{
		return setId;
	}

	/**
	 * Sets the set ID
	 * 
	 * @param setId
	 *            the setId to set
	 */
	public void setSetId(String setId)
	{
		this.setId = setId;
	}
}
