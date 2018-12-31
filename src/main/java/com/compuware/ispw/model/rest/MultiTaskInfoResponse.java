package com.compuware.ispw.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response model for a multi task operation.
 */
@XmlRootElement(name="taskAction")
public class MultiTaskInfoResponse
{
	private String message;
	private String url;
	@XmlElement(name = "failedTasks")
	private List<MultiTaskFailure> failedTasks = new ArrayList<>();

	/**
	 * Gets the message.
	 * 
	 * @return The message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Sets the message
	 * 	
	 * @param message The message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Gets the URL
	 * 
	 * @return The URL
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * Sets the URL
	 * 
	 * @param url The URL to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	/**
	 * Adds a failed task.
	 * 
	 * @param failure The failed task
	 */
	public void addFailedTask(MultiTaskFailure failure)
	{
		failedTasks.add(failure);
	}

	/**
	 * Gets the failed tasks list
	 * 
	 * @return the failedTasks
	 */
	public List<MultiTaskFailure> getFailedTasks()
	{
		return failedTasks;
	}
}