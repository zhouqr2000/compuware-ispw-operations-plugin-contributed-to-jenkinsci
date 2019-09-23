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
* Copyright (c) 2017-2019 Compuware Corporation. All rights reserved.
******************************************************************************/

package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deploymentItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeploymentPackageItem
{
	private String item;
	private String part;
	private String name;
	private String deploymentType;
	private String application;
	private String type;
	private String clazz;
	private String action;
	private String system;
	private String status;
	private String storage;
	
	public String getItem()
	{
		return item;
	}
	
	public void setItem(String item)
	{
		this.item = item;
	}
	
	public String getPart()
	{
		return part;
	}
	
	public void setPart(String part)
	{
		this.part = part;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDeploymentType()
	{
		return deploymentType;
	}
	
	public void setDeploymentType(String deploymentType)
	{
		this.deploymentType = deploymentType;
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
	
	public String getClazz()
	{
		return clazz;
	}
	
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public String getSystem()
	{
		return system;
	}
	
	public void setSystem(String system)
	{
		this.system = system;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStorage()
	{
		return storage;
	}
	
	public void setStorage(String storage)
	{
		this.storage = storage;
	}
}
