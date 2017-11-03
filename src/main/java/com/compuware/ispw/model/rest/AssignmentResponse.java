package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "assignment")
public class AssignmentResponse
{
	private String assignmentId;
	private String message;
	private String url;

	public AssignmentResponse()
	{
	}

	public AssignmentResponse(String assignmentId, String message, String url)
	{
		this.message = message;
		this.assignmentId = assignmentId;
		this.url = url;
	}

	public String getAssignmentId()
	{
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId)
	{
		this.assignmentId = assignmentId;
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
