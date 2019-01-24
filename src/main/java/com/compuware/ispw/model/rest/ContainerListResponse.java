package com.compuware.ispw.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="containerList")
@XmlAccessorType(XmlAccessType.NONE)
public class ContainerListResponse
{
	@XmlElement(name = "containers")
	private List<ContainerListInfo> containerList = new ArrayList<ContainerListInfo>(); 
	@XmlElement(name = "message")
	private String message;
		
	public ContainerListResponse()
	{
	}
	
	public void addContainer(ContainerListInfo containerListInfo)
	{
		containerList.add(containerListInfo);
	}
	
	public List<ContainerListInfo> getContainerList()
	{
		return containerList;
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