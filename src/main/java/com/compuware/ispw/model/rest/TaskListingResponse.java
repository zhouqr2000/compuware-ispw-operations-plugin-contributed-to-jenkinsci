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
* Copyright (c) 2017 Compuware Corporation. All rights reserved.
******************************************************************************/

package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="generateListing")
public class TaskListingResponse
{
	private String listing; 
	private String message;
		
	public TaskListingResponse()
	{
	}
	
	public String getListing()
	{
		return listing;
	}

	public void setListing(String listing)
	{
		this.listing = listing;
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