package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for an ISPW work list item
 */
@XmlRootElement(name = "workListItem")
@XmlAccessorType(XmlAccessType.NONE)
public class WorkListInfo
{
	@XmlElement(name = "action")
	private String action;
	@XmlElement(name = "alternateName")
	private String alternateName;
	@XmlElement(name = "application")
	private String application;
	@XmlElement(name = "assignmentId")
	private String assignmentId;
	@XmlElement(name = "clazz")
	private String clazz;
	@XmlElement(name = "dateTime")
	private String dateTime;
	@XmlElement(name = "environment")
	private String environment;
	@XmlElement(name = "group")
	private String group;
	@XmlElement(name = "level")
	private String level;
	@XmlElement(name = "message")
	private String message;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "operation")
	private String operation;
	@XmlElement(name = "owner")
	private String owner;
	@XmlElement(name = "path")
	private String path;
	@XmlElement(name = "refNumber")
	private String refNumber;
	@XmlElement(name = "relativePath")
	private String relativePath;
	@XmlElement(name = "releaseId")
	private String releaseId;
	@XmlElement(name = "stream")
	private String stream;
	@XmlElement(name = "taskId")
	private String taskId;
	@XmlElement(name = "technology")
	private String technology;
	@XmlElement(name = "type")
	private String type;
	@XmlElement(name = "user")
	private String user;
	@XmlElement(name = "version")
	private String version;

	/**
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return the alternateName
	 */
	public String getAlternateName()
	{
		return alternateName;
	}

	/**
	 * @param alternateName
	 *            the alternateName to set
	 */
	public void setAlternateName(String alternateName)
	{
		this.alternateName = alternateName;
	}

	/**
	 * @return the application
	 */
	public String getApplication()
	{
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application)
	{
		this.application = application;
	}

	/**
	 * @return the assignmentId
	 */
	public String getAssignmentId()
	{
		return assignmentId;
	}

	/**
	 * @param assignmentId
	 *            the assignmentId to set
	 */
	public void setAssignmentId(String assignmentId)
	{
		this.assignmentId = assignmentId;
	}

	/**
	 * @return the clazz
	 */
	public String getClazz()
	{
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime()
	{
		return dateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment()
	{
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(String environment)
	{
		this.environment = environment;
	}

	/**
	 * @return the group
	 */
	public String getGroup()
	{
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group)
	{
		this.group = group;
	}

	/**
	 * @return the level
	 */
	public String getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the operation
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	/**
	 * @return the refNumber
	 */
	public String getRefNumber()
	{
		return refNumber;
	}

	/**
	 * @param refNumber
	 *            the refNumber to set
	 */
	public void setRefNumber(String refNumber)
	{
		this.refNumber = refNumber;
	}

	/**
	 * @return the relativePath
	 */
	public String getRelativePath()
	{
		return relativePath;
	}

	/**
	 * @param relativePath
	 *            the relativePath to set
	 */
	public void setRelativePath(String relativePath)
	{
		this.relativePath = relativePath;
	}

	/**
	 * @return the releaseId
	 */
	public String getReleaseId()
	{
		return releaseId;
	}

	/**
	 * @param releaseId
	 *            the releaseId to set
	 */
	public void setReleaseId(String releaseId)
	{
		this.releaseId = releaseId;
	}

	/**
	 * @return the stream
	 */
	public String getStream()
	{
		return stream;
	}

	/**
	 * @param stream
	 *            the stream to set
	 */
	public void setStream(String stream)
	{
		this.stream = stream;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId()
	{
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	/**
	 * @return the technology
	 */
	public String getTechnology()
	{
		return technology;
	}

	/**
	 * @param technology
	 *            the technology to set
	 */
	public void setTechnology(String technology)
	{
		this.technology = technology;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}

}
