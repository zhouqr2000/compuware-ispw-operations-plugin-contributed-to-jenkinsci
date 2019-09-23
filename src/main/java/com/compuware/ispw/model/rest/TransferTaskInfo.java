/**
 * These materials contain confidential information and trade secrets of Compuware Corporation. You shall maintain the materials
 * as confidential and shall not disclose its contents to any third party except as may be required by law or regulation. Use,
 * disclosure, or reproduction is prohibited without the prior express written permission of Compuware Corporation.
 * 
 * All Compuware products listed within the materials are trademarks of Compuware Corporation. All other company or product
 * names are trademarks of their respective owners.
 * 
 * Copyright (c) 2018-2019 Compuware Corporation. All rights reserved.
 */
package com.compuware.ispw.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.compuware.ces.model.validation.Required;

/**
 * Model class for a transfer task
 */
@XmlRootElement(name = "transferTask")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransferTaskInfo 
{
	@Required
	private String runtimeConfiguration;

	@Required
	private String containerId;
	
	@Required
	private String containerType;

	/**
	 * Gets the runtimeConfiguration
	 * 
	 * @return the runtimeConfiguration
	 */
	public String getRuntimeConfiguration()
	{
		return runtimeConfiguration;
	}

	/**
	 * Sets the runtime configuration
	 * 
	 * @param runtimeConfiguration
	 *            the runtimeConfiguration to set
	 */
	public void setRuntimeConfiguration(String runtimeConfiguration)
	{
		this.runtimeConfiguration = runtimeConfiguration;
	}

	/**
	 * Gets the container ID.
	 * 
	 * @return the containerId
	 */
	public String getContainerId()
	{
		return containerId;
	}

	/**
	 * Sets the container ID.
	 * 
	 * @param containerId
	 *            the containerId to set
	 */
	public void setContainerId(String containerId)
	{
		this.containerId = containerId;
	}

	/**
	 * Gets the container type.
	 * 
	 * @return the containerType
	 */
	public String getContainerType()
	{
		return containerType;
	}

	/**
	 * Sets the container type.
	 * 
	 * @param containerType the containerType to set
	 */
	public void setContainerType(String containerType)
	{
		this.containerType = containerType;
	}

}
