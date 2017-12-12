package com.compuware.ispw.model.request;

import com.compuware.ces.model.validation.Required;

public class Release
{
	private String releaseId;
	@Required
	private String defaultApplication;
	@Required
	private String defaultStream;
	@Required
	private String description;
	private String owner;
	private String implementationDate;
	private String implementationTime;
	private String workRefNumber;
	private String releasePrefix;
	private String userTag;

	public String getReleaseId()
	{
		return releaseId;
	}

	public void setReleaseId(String releaseId)
	{
		this.releaseId = releaseId;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getImplementationDate()
	{
		return implementationDate;
	}

	public void setImplementationDate(String implementationDate)
	{
		this.implementationDate = implementationDate;
	}

	public String getImplementationTime()
	{
		return implementationTime;
	}

	public void setImplementationTime(String implementationTime)
	{
		this.implementationTime = implementationTime;
	}

	public String getDefaultApplication()
	{
		return defaultApplication;
	}

	public void setDefaultApplication(String defaultApplication)
	{
		this.defaultApplication = defaultApplication;
	}

	public String getDefaultStream()
	{
		return defaultStream;
	}

	public void setDefaultStream(String defaultStream)
	{
		this.defaultStream = defaultStream;
	}

	public String getWorkRefNumber()
	{
		return workRefNumber;
	}

	public void setWorkRefNumber(String workRefNumber)
	{
		this.workRefNumber = workRefNumber;
	}

	public String getReleasePrefix()
	{
		return releasePrefix;
	}

	public void setReleasePreference(String releasePrefix)
	{
		this.releasePrefix = releasePrefix;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getUserTag()
	{
		return userTag;
	}

	public void setUserTag(String userTag)
	{
		this.userTag = userTag;
	}
	
}
