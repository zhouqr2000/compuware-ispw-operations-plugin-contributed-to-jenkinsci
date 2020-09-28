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
package com.compuware.ispw.restapi;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 */
@SuppressWarnings("nls")
public class BuildParmsTest
{

	private static String outputJson = "{\"containerId\":\"PLAY123456\",\"releaseId\":\"RELEASE123\",\"taskLevel\":\"LEVEL\",\"taskIds\":[\"ABCDEF123\",\"123456\",\"932795063\"]}";
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.BuildParms#toString()}.
	 */
	@Test
	public void testToString()
	{
		BuildParms inputParms = new BuildParms();
		inputParms.setContainerId("PLAY123456");
		inputParms.setReleaseId("RELEASE123");
		inputParms.setTaskLevel("LEVEL");
		inputParms.addTaskId("ABCDEF123");
		inputParms.addTaskId("123456");
		inputParms.addTaskId("932795063");

		assertEquals(outputJson, inputParms.toString());
	}

	/**
	 * Test method for {@link com.compuware.ispw.restapi.BuildParms#parse(java.lang.String)}.
	 */
	@Test
	public void testParse()
	{
		assertNull(BuildParms.parse(null));
		
		BuildParms outputParms = BuildParms.parse(outputJson);
		assertEquals("PLAY123456", outputParms.getContainerId());
		assertEquals("RELEASE123", outputParms.getReleaseId());
		assertEquals("LEVEL", outputParms.getTaskLevel());
		assertEquals(3, outputParms.getTaskIds().size());
		assertEquals("ABCDEF123", outputParms.getTaskIds().get(0));
		assertEquals("123456", outputParms.getTaskIds().get(1));
		assertEquals("932795063", outputParms.getTaskIds().get(2));
	}

}
