package com.compuware.ces.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name = "httpHeader")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HttpHeader extends NameValuePair
{
	public String toDisplayString()
	{
		String hiddenValue = "";
		if (value != null && value.length() > 0)
		{
			hiddenValue = StringUtils.repeat("x", value.length());
		}
		return name + " : " + hiddenValue;	
	}
}
