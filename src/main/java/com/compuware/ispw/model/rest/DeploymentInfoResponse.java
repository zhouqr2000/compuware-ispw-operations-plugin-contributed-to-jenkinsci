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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="deploymentResponse")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DeploymentInfoResponse
{
	private List<DeploymentInfo> deployments = new ArrayList<DeploymentInfo>();

	public List<DeploymentInfo> getDeployments()
	{
		return deployments;
	}

	public void setDeployments(List<DeploymentInfo> deployments)
	{
		this.deployments = deployments;
	}
	
	public void addDeployment(DeploymentInfo deployment)
	{
		this.deployments.add(deployment);
	}
	
	
}
