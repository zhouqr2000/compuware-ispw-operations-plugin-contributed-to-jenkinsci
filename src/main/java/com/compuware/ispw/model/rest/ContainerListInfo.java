package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="containerList")
@XmlAccessorType(XmlAccessType.NONE)
public class ContainerListInfo
{
	@XmlElement(name = "application")
	private String application;
	@XmlElement(name = "containerId")
	private String containerId;
	@XmlElement(name = "containerType")
	private String containerType;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "includeClosedContainers")
	private String includeClosedContainers;
	@XmlElement(name = "owner")
	private String owner;
	@XmlElement(name = "path")
	private String path;
	@XmlElement(name = "refNumber")
	private String workRefNumber;
	@XmlElement(name = "releaseId")
	private String releaseId;
	@XmlElement(name = "stream")
	private String stream;
	@XmlElement(name = "tag")
	private String userTag;	
	@XmlElement(name = "userId")
	private String userId;	
	@XmlElement(name = "message")
	private String message;

	public String getApplication()
	{
		return application;
	}

	public void setApplicationId(String application)
	{
		this.application = application;
	}
	
	public String getContainerId()
	{
		return containerId;
	}

	public void setContainerId(String containerId)
	{
		this.containerId = containerId;
	}
	
	public String getContainerType()
	{
		return containerType;
	}

	public void setContainerType(String containerType)
	{
		this.containerType = containerType;
	}
	
	public String getIncludeClosedContainers()
	{
		return includeClosedContainers;
	}

	public void setIncludeClosedContainers(String includeClosedContainers)
	{
		this.includeClosedContainers = includeClosedContainers;
	}

	public String getStream()
	{
		return stream;
	}

	public void setStream(String stream)
	{
		this.stream = stream;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getReleaseId()
	{
		return releaseId;
	}

	public void setReleaseId(String releaseId)
	{
		this.releaseId = releaseId;
	}

	public String getWorkRefNumber()
	{
		return workRefNumber;
	}

	public void setWorkRefNumber(String workRefNumber)
	{
		this.workRefNumber = workRefNumber;
	}
	
	public String getUserTag()
	{
		return userTag;
	}

	public void setUserTag(String userTag)
	{
		this.userTag = userTag;
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
}
