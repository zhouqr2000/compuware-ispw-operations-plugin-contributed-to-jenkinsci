package com.compuware.ispw.model.rest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.compuware.ces.communications.service.data.EventCallback;
import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;
import com.compuware.ces.model.validation.Default;

@XmlRootElement(name = "set")
@XmlAccessorType(XmlAccessType.NONE)
public class SetInfo
{
	@XmlElement(name = "runtimeConfiguration")
	private String runtimeConfig;
	@XmlElement(name = "changeType")
	@Default(defaultValue="S")
	private String changeType;
	@XmlElement(name = "executionStatus")
	@Default(defaultValue="I")
	private String execStat;
	@XmlElement(name = "dpenvlst")
	@Default(defaultValue="")
	private String dpenvlst;
	@XmlElement(name = "system")
	@Default(defaultValue="")
	private String system;
	@XmlElement(name = "autoDeploy")
	@Default(defaultValue="false")
	private String autoDeploy;	
	@XmlElement(name = "httpHeaders")
	private ArrayList<HttpHeader> httpHeaders;
	@XmlElement(name = "credentials")
	private BasicAuthentication credentials;
	@XmlElement(name = "events")
	private ArrayList<EventCallback> eventCallbacks;
	@XmlElement(name = "deployActiveDate")
	@Default(defaultValue="")
	private String deployActiveDate;
	@XmlElement(name = "deployActiveTime")
	@Default(defaultValue="")
	private String deployActiveTime;
	@XmlElement(name = "deployImplementationDate")
	@Default(defaultValue="")
	private String deployImplementationDate;
	@XmlElement(name = "deployImplementationTime")
	@Default(defaultValue="")
	private String deployImplementationTime;
	
	public SetInfo()
	{
	}
		
	public ArrayList<HttpHeader> getHttpHeaders()
	{
		return httpHeaders;
	}

	public void setHttpHeaders(ArrayList<HttpHeader> httpHeaders)
	{
		this.httpHeaders = httpHeaders;
	}

	public BasicAuthentication getCredentials()
	{
		return credentials;
	}

	public void setCredentials(BasicAuthentication credentials)
	{
		this.credentials = credentials;
	}

	public ArrayList<EventCallback> getEventCallbacks()
	{
		return eventCallbacks;
	}

	public void setEventCallbacks(ArrayList<EventCallback> eventCallbacks)
	{
		this.eventCallbacks = eventCallbacks;
	}

	public String getChangeType()
	{
		return changeType;
	}

	public void setChangeType(String changeType)
	{
		this.changeType = changeType;
	}

	public String getExecStat()
	{
		return execStat;
	}

	public void setExecStat(String execStat)
	{
		this.execStat = execStat;
	}

	public String getRuntimeConfig()
	{
		return runtimeConfig;
	}

	public void setRuntimeConfig(String runtimeConfig)
	{
		this.runtimeConfig = runtimeConfig;
	}

	public String getDpenvlst()
	{
		return dpenvlst;
	}

	public void setDpenvlst(String dpenvlst)
	{
		this.dpenvlst = dpenvlst;
	}

	public String getSystem()
	{
		return system;
	}

	public void setSystem(String system)
	{
		this.system = system;
	}

	public String getAutoDeploy()
	{
		return autoDeploy;
	}

	public void setAutoDeploy(String autoDeploy)
	{
		this.autoDeploy = autoDeploy;
	}
	
	public String getDeployActiveDate()
	{
		return deployActiveDate;
	}

	public void setDeployActiveDate(String deployActiveDate)
	{
		this.deployActiveDate = deployActiveDate;
	}
	
	public String getDeployActiveTime()
	{
		return deployActiveTime;
	}

	public void setDeployActiveTime(String deployActiveTime)
	{
		this.deployActiveTime = deployActiveTime;
	}
	
	public String getDeployImplementationDate()
	{
		return deployImplementationDate;
	}

	public void setDeployImplementationDate(String deployImplementationDate)
	{
		this.deployImplementationDate = deployImplementationDate;
	}
	
	public String getDeployImplementationTime()
	{
		return deployImplementationTime;
	}

	public void setDeployImplementationTime(String deployImplementationTime)
	{
		this.deployImplementationTime = deployImplementationTime;
	}
}
