package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.compuware.ces.model.validation.Required;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddTaskInfo
{
	@XmlElement(name = "runtimeConfiguration")
	private String runtimeConfiguration;
	@XmlElement(name="taskName")
	@Required
	private String taskName;
	@XmlElement(name="stream")
	@Required
	private String stream;
	@XmlElement(name="application")
	@Required
	private String application;
	@XmlElement(name="type")
	@Required
	private String type;
	@XmlElement(name="action")
	private String action;
	@XmlElement(name="path")
	@Required
	private String path;
	@XmlElement(name="owner")
	private String owner;
	@XmlElement(name="checkoutFromLevel")
	@Required
	private String checkoutFromLevel;
	@XmlElement(name="releaseId")
	private String releaseId;

	public String getRuntimeConfig()
	{
		return runtimeConfiguration;
	}

	public void setRuntimeConfig(String runtimeConfig)
	{
		this.runtimeConfiguration = runtimeConfig;
	}
	
	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}
	
	public String getStream()
	{
		return stream;
	}

	public void setStream(String stream)
	{
		this.stream = stream;
	}
	
	public String getApplication()
	{
		return application;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}
	
	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getCheckoutFromLevel()
	{
		return checkoutFromLevel;
	}

	public void setCheckoutFromLevel(String checkoutFromLevel)
	{
		this.checkoutFromLevel = checkoutFromLevel;
	}

	public String getRelease()
	{
		return releaseId;
	}

	public void setRelease(String releaseId)
	{
		this.releaseId = releaseId;
	}

}
