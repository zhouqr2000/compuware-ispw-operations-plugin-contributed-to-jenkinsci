/**
 * These materials contain confidential information and trade secrets of Compuware Corporation. You shall maintain the materials
 * as confidential and shall not disclose its contents to any third party except as may be required by law or regulation. Use,
 * disclosure, or reproduction is prohibited without the prior express written permission of Compuware Corporation.
 * 
 * All Compuware products listed within the materials are trademarks of Compuware Corporation. All other company or product
 * names are trademarks of their respective owners.
 * 
 * Copyright (c) 2019 Compuware Corporation. All rights reserved.
 */
package com.compuware.ispw.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for an ISPW work list
 */
@XmlRootElement(name="workList")
@XmlAccessorType(XmlAccessType.NONE)
public class WorkListResponse
{
	@XmlElement(name = "workListItems")
	private List<WorkListInfo> workListItems = new ArrayList<>();
	@XmlElement(name = "message")
	private String message;

	/**
	 * Gets the work list items
	 * 
	 * @return the work list items
	 */
	public List<WorkListInfo> getWorkListItems()
	{
		return workListItems;
	}

	/**
	 * Sets the work list items
	 * 
	 * @param workListItems
	 *            the  work list items to set
	 */
	public void setContainers(List<WorkListInfo> workListItems)
	{
		this.workListItems = workListItems;
	}

	/**
	 * Gets the message
	 * 
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Sets the message
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Adds a new work list item
	 * 
	 * @param workListItem
	 *            The work list item to add
	 */
	public void addWorkListItem(WorkListInfo workListItem)
	{
		if (workListItems == null)
		{
			workListItems = new ArrayList<>();
		}
		workListItems.add(workListItem);
	}
}
