package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="release")
@XmlAccessorType(XmlAccessType.NONE)
public class ReleaseInfo
{
	@XmlElement(name = "application")
	private String application;
	@XmlElement(name = "subAppl")
	private String subAppl;
	@XmlElement(name = "stream")
	private String stream;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "owner")
	private String owner;
	@XmlElement(name = "releaseId")
	private String releaseId;
	@XmlElement(name = "releasePrefix")
	private String releasePrefix;
	@XmlElement(name = "referenceNumber")
	private String workRefNumber;
	@XmlElement(name = "userTag")
	private String userTag;	
	@XmlElement(name = "message")
	private String message;

	public String getApplication()
	{
		return application;
	}

	public void setApplication(String defaultApplication)
	{
		this.application = defaultApplication;
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

	public String getReleaseId()
	{
		return releaseId;
	}

	public void setReleaseId(String releaseId)
	{
		this.releaseId = releaseId;
	}

	public String getReleasePrefix()
	{
		return releasePrefix;
	}

	public void setReleasePrefix(String releasePrefix)
	{
		this.releasePrefix = releasePrefix;
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

	public String getSubAppl()
	{
		return subAppl;
	}

	public void setSubAppl(String subAppl)
	{
		this.subAppl = subAppl;
	}
}
