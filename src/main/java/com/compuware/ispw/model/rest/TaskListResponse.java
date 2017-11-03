/******************************************************************************
* These materials contain confidential information and trade secrets of 
* Compuware Corporation. You shall maintain the materials as confidential 
* and shall not disclose its contents to any third party except as may be 
* required by law or regulation. Use, disclosure, or reproduction is 
* prohibited without the prior express written permission of Compuware 
* Corporation.
* 
* All Compuware products listed within the materials are trademarks of 
* Compuware Corporation. All other company or product names are trademarks
* of their respective owners.
* 
* Copyright (c) 2017 Compuware Corporation. All rights reserved.
******************************************************************************/

package com.compuware.ispw.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="taskList")
@XmlAccessorType(XmlAccessType.NONE)
public class TaskListResponse
{
	@XmlElement(name = "tasks")
	private List<TaskInfo> tasks = new ArrayList<TaskInfo>(); 
	@XmlElement(name = "message")
	private String message;
		
	public TaskListResponse()
	{
	}
	
	public void addTask(TaskInfo task)
	{
		tasks.add(task);
	}
	
	public List<TaskInfo> getTasks()
	{
		return tasks;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}