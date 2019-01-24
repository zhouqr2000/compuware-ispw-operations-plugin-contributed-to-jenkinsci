/**
 * These materials contain confidential information and trade secrets of Compuware Corporation. You shall maintain the materials
 * as confidential and shall not disclose its contents to any third party except as may be required by law or regulation. Use,
 * disclosure, or reproduction is prohibited without the prior express written permission of Compuware Corporation.
 * 
 * All Compuware products listed within the materials are trademarks of Compuware Corporation. All other company or product
 * names are trademarks of their respective owners.
 * 
 * Copyright (c) 2018 Compuware Corporation. All rights reserved.
 */
package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for a deployment action response, i.e. cancel a deployment.
 */
@XmlRootElement(name = "deployment")
public class DeploymentResponse
{
	private long requestId;
	private String message;
	private String url;

	/**
	 * Default constructor.
	 */
	public DeploymentResponse()
	{

	}

	/**
	 * Alternate constructor specifying all fields.
	 * 
	 * @param requestId
	 *            The deployment request ID
	 * @param message
	 *            The deployment response message
	 * @param url
	 *            The deployment response URL
	 */
	public DeploymentResponse(long requestId, String message, String url)
	{
		super();
		this.requestId = requestId;
		this.message = message;
		this.url = url;
	}

	/**
	 * Gets the deployment request ID
	 * 
	 * @return the requestId
	 */
	public long getRequestId()
	{
		return requestId;
	}

	/**
	 * Sets the deployment request ID
	 * 
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(long requestId)
	{
		this.requestId = requestId;
	}

	/**
	 * Gets the deployment response message
	 * 
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Sets the deployment response message
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Gets the deployment response URL
	 * 
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * Sets the deployment response URL
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}
}
