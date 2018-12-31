package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response model for a task operation.
 */
@XmlRootElement(name = "TaskFailure")
public class MultiTaskFailure
{
	private String mname;
	private String mtype;
	private String taskId;
	private String errorMessage;

	/**
	 * Default constructor.
	 */
	public MultiTaskFailure()
	{

	}

	/**
	 * Alternate constructor initializing all fields.
	 * 
	 * @param mname
	 *            The mname
	 * @param mtype
	 *            The mtype
	 * @param taskId
	 *            The task ID (Hex value)
	 * @param errorMessage
	 *            The error message as to why task failed
	 */
	public MultiTaskFailure(String mname, String mtype, String taskId, String errorMessage)
	{
		super();
		this.mname = mname;
		this.mtype = mtype;
		this.taskId = taskId;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the mname
	 * 
	 * @return the mname
	 */
	public String getMname()
	{
		return mname;
	}

	/**
	 * Sets the mname
	 * 
	 * @param mname
	 *            the mname to set
	 */
	public void setMname(String mname)
	{
		this.mname = mname;
	}

	/**
	 * Gets the mtype
	 * 
	 * @return the mtype
	 */
	public String getMtype()
	{
		return mtype;
	}

	/**
	 * Sets the mtype
	 * 
	 * @param mtype
	 *            the mtype to set
	 */
	public void setMtype(String mtype)
	{
		this.mtype = mtype;
	}

	/**
	 * Gets the task ID
	 * 
	 * @return the taskId
	 */
	public String getTaskId()
	{
		return taskId;
	}

	/**
	 * Sets the task IDa
	 * 
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}
	
	/**
	 * Gets the error message
	 * 
	 * @return the error message
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * Sets the error message
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}