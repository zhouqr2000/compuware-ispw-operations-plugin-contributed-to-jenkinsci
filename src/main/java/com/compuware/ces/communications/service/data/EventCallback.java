package com.compuware.ces.communications.service.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public class EventCallback extends HttpRequest
{
	private static final long serialVersionUID = 862712578600685320L;
	
	@XmlElement(name = "name")
	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
