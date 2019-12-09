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
package com.compuware.ispw.restapi.action.test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.action.IBuildAction;

/**
 * 
 */
@SuppressWarnings("nls")
public class IBuildActionTest
{
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	PrintStream logger = System.out;
	
	@Before
	public void setUp() throws GitAPIException
	{
//		System.out.println("IBuildActionTest folder: " + tempFolder.getRoot().getAbsolutePath());
	}
//	
//	@After
//	public void tearDown()
//	{
//		try
//		{
//			FileUtils.deleteDirectory(tempFolder.getRoot());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Test method for {@link com.compuware.ispw.restapi.action.IBuildAction#getRequestBody(java.lang.String, java.io.File, java.io.PrintStream)}.
//	 */
//	@Test
//	public void testGetRequestBody_buildAutomaticallyNoFile()
//	{
//		File buildDirectory = tempFolder.getRoot();
//		String inputRequestBody = "BUILDautomatically true\n###";
//		BuildAction buildAction = new BuildAction();
//		String outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		String expectedOutput = "\n###";
//		assertEquals(expectedOutput, outputRequestBody);
//	}
//	
//	/**
//	 * Test method for {@link com.compuware.ispw.restapi.action.IBuildAction#getRequestBody(java.lang.String, java.io.File, java.io.PrintStream)}.
//	 */
////	@Test
////	public void testGetRequestBody_buildAutomaticallyHasFile() throws IOException
////	{
////		File buildDirectory = tempFolder.getRoot();
////		String inputRequestBody = "BuildAutomatically = true";
////		BuildAction buildAction = new BuildAction();
////		File parmFile = new File(buildDirectory.getAbsoluteFile(), IBuildAction.BUILD_PARAM_FILE_NAME);
////		// file is empty
////		FileUtils.writeStringToFile(parmFile, "");
////		String outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
////		assertEquals(StringUtils.EMPTY, outputRequestBody);
////		
////		// file contains build parms
////		FileUtils.writeStringToFile(parmFile, "{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
////		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
////		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2";
////		assertEquals(expectedOutput, outputRequestBody);
////		
////		FileUtils.writeStringToFile(parmFile, "{\"containerId\":\"PLAY002455\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
////		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
////		expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\ntaskId = 7E3A5D04D0C2";
////		assertEquals(expectedOutput, outputRequestBody);
////	}
//	
//	/**
//	 * Test method for {@link com.compuware.ispw.restapi.action.IBuildAction#getRequestBody(java.lang.String, java.io.File, java.io.PrintStream)}.
//	 */
//	@Test
//	public void testGetRequestBody_notBuildAutomatically()
//	{
//		File buildDirectory = tempFolder.getRoot();
//		String inputRequestBody = "# comment";
//		BuildAction buildAction = new BuildAction();
//		String outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(inputRequestBody, outputRequestBody);
//		
//		inputRequestBody = "# buildautomatically = true \n# comment";
//		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(inputRequestBody, outputRequestBody);
//		
//		inputRequestBody = "buildautomatically = false \n# comment";
//		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(inputRequestBody, outputRequestBody);
//		
//		inputRequestBody = null;
//		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(inputRequestBody, outputRequestBody);
//		
//		inputRequestBody = "";
//		outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(inputRequestBody, outputRequestBody);
//		
//	}
//	
//	/**
//	 * Test method for {@link com.compuware.ispw.restapi.action.IBuildAction#getRequestBody(java.lang.String, java.io.File, java.io.PrintStream)}.
//	 */
//	@Test
//	public void testGetRequestBody_replacingGivenParms() throws IOException
//	{
//		File buildDirectory = tempFolder.getRoot();
//		String inputRequestBody = "buildAutomatically=true\nevents.name=Completed\r\napplication = x\nevents.body=Deployed\r\n  assignmentID = y\n releaseid = z\n"
//				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nTASKID=xyz\n\nmname=f\nmtype =hij\r\nlevel = qwerty\nevents.credentials=admin:library";
//		BuildAction buildAction = new BuildAction();
//		
//		File parmFile = new File(buildDirectory.getAbsoluteFile(), IBuildAction.BUILD_PARAM_FILE_NAME);
//		FileUtils.writeStringToFile(parmFile,
//				"{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
//		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2\nevents.name=Completed\r\n\nevents.body=Deployed\r\n\n"
//				+ "\nevents.httpHeaders=Jenkins-Crumb:no-crumb\r\n\n\n\n\r\n\nevents.credentials=admin:library";
//		
//		String outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(expectedOutput, outputRequestBody);
//	}
//
//	/**
//	 * Test method for {@link com.compuware.ispw.restapi.action.IBuildAction#getRequestBody(java.lang.String, java.io.File, java.io.PrintStream)}.
//	 */
//	@Test
//	public void testGetRequestBody_containsEventData() throws IOException
//	{
//		File buildDirectory = tempFolder.getRoot();
//		String inputRequestBody = "buildAutomatically=true\nevents.name=Completed\r\nevents.body=Deployed\r\n"
//				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nevents.credentials=admin:library";
//		BuildAction buildAction = new BuildAction();
//		
//		File parmFile = new File(buildDirectory.getAbsoluteFile(), IBuildAction.BUILD_PARAM_FILE_NAME);
//		FileUtils.writeStringToFile(parmFile,
//				"{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
//		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2\nevents.name=Completed\r\nevents.body=Deployed\r\n"
//				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nevents.credentials=admin:library";
//		String outputRequestBody = buildAction.getRequestBody(inputRequestBody, buildDirectory, logger);
//		assertEquals(expectedOutput, outputRequestBody);
//	}
//	
//	private class BuildAction implements IBuildAction
//	{
//
//		/* (non-Javadoc)
//		 * @see com.compuware.ispw.restapi.action.IBuildAction#getIspwRequestBean(java.lang.String, java.lang.String, com.compuware.ispw.restapi.WebhookToken, java.io.File)
//		 */
//		@Override
//		public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken,
//				File buildDirectory)
//		{
//			return null;
//		}
//		
//	}
}
