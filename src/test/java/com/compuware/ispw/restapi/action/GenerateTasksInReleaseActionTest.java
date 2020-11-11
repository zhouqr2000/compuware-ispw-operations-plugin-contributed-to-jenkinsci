/**
 * (c) Copyright 2020 BMC Software, Inc.
 */
package com.compuware.ispw.restapi.action;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.compuware.ispw.restapi.Constants;
import hudson.FilePath;

/**
 * 
 */
public class GenerateTasksInReleaseActionTest
{
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	private static Logger log = Logger.getLogger(GenerateTasksInReleaseActionTest.class);
	PrintStream logger = System.out;
	
	@Before
	public void setUp() throws GitAPIException
	{
		System.out.println("GenerateTasksInReleaseActionTest folder: " + tempFolder.getRoot().getAbsolutePath());
	}
	
	@After
	public void tearDown()
	{
		try
		{
			FileUtils.deleteDirectory(tempFolder.getRoot());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testPreprocess_generateAutomaticallyNoAccess() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		String inputRequestBody = "generateautomatically = true\n###";
		GenerateAction generateAction = new GenerateAction();
		try {
			String outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(buildDirectory), logger);
			assert(false);
		} catch (IOException | InterruptedException e) {
			assert(true);
		}
	}
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	public void testPreprocess_generateAutomaticallyNoFile() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		File parmsFile = new File(buildDirectory, Constants.BUILD_PARAM_FILE_NAME);
		parmsFile.delete();
		String inputRequestBody = "GENERATEautomatically true\n###";
		GenerateAction generateAction = new GenerateAction();
		String outputRequestBody = generateAction.preprocess(inputRequestBody, null, logger);
		String expectedOutput = "";
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
		
		outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmsFile), logger);
		expectedOutput = "";
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
	}
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws InterruptedException 
	 */
	
	@Test
	public void testPreprocess_generateAutomaticallyHasFile() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		String inputRequestBody = "GenerateAutomatically = true";
		GenerateAction generateAction = new GenerateAction();
		File parmFile = new File(buildDirectory.getAbsoluteFile(), Constants.BUILD_PARAM_FILE_NAME);
		// file is empty
		FileUtils.writeStringToFile(parmFile, "");
		String outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", StringUtils.EMPTY, outputRequestBody);
		
		// file contains build parms
		FileUtils.writeStringToFile(parmFile, "{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
		outputRequestBody = generateAction.preprocess(inputRequestBody,  new FilePath(parmFile), logger);
		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2,7E3A5D04D3A3\n";
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
		
		FileUtils.writeStringToFile(parmFile, "{\"containerId\":\"PLAY002455\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
		outputRequestBody = generateAction.preprocess(inputRequestBody,  new FilePath(parmFile), logger);
		expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\ntaskId = 7E3A5D04D0C2,7E3A5D04D3A3\n";
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
	}
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	public void testPreprocess_notGenerateAutomatically() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		File parmFile = new File(buildDirectory.getAbsoluteFile(), Constants.BUILD_PARAM_FILE_NAME);
		parmFile.delete();
		String inputRequestBody = "# comment";
		GenerateAction generateAction = new GenerateAction();
		String outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", inputRequestBody, outputRequestBody);
		
		inputRequestBody = "# generateautomatically = true \n# comment";
		outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", inputRequestBody, outputRequestBody);
		
		inputRequestBody = "generateautomatically = false \n# comment";
		outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", inputRequestBody, outputRequestBody);
		
		inputRequestBody = null;
		outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", inputRequestBody, outputRequestBody);
		
		inputRequestBody = "";
		outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", inputRequestBody, outputRequestBody);
		
	}
	
	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPreprocess_replacingGivenParms() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		String inputRequestBody = "generateAutomatically=true\nevents.name=Completed\r\napplication = x\nevents.body=Generated\r\n  assignmentID = y\n releaseid = z\n"
				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nTASKID=xyz\n\nmname=f\nmtype =hij\r\nlevel = qwerty\nevents.credentials=admin:library";
		GenerateAction generateAction = new GenerateAction();
		
		File parmFile = new File(buildDirectory.getAbsoluteFile(), Constants.BUILD_PARAM_FILE_NAME);
		FileUtils.writeStringToFile(parmFile,
				"{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2,7E3A5D04D3A3\n\nevents.name=Completed\r\n\nevents.body=Generated\r\n\n"
				+ "\nevents.httpHeaders=Jenkins-Crumb:no-crumb\r\n\n\n\n\r\n\nevents.credentials=admin:library";
		
		String outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
	}

	/**
	 * Test method for {@link com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction#preprocess(String, String, FilePath, PrintStream, String, String)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPreprocess_containsEventData() throws IOException, InterruptedException
	{
		File buildDirectory = tempFolder.getRoot();
		String inputRequestBody = "generateAutomatically=true\nevents.name=Completed\r\nevents.body=Generated\r\n"
				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nevents.credentials=admin:library";
		GenerateAction generateAction = new GenerateAction();
		
		File parmFile = new File(buildDirectory.getAbsoluteFile(), Constants.BUILD_PARAM_FILE_NAME);
		FileUtils.writeStringToFile(parmFile,
				"{\"containerId\":\"PLAY002455\",\"releaseId\":\"RELEASE666\",\"taskLevel\":\"DEV2\",\"taskIds\":[\"7E3A5D04D0C2\",\"7E3A5D04D3A3\"]}");
		String expectedOutput = "assignmentId = PLAY002455\nlevel = DEV2\nreleaseId = RELEASE666\ntaskId = 7E3A5D04D0C2,7E3A5D04D3A3\n\nevents.name=Completed\r\nevents.body=Generated\r\n"
				+ "events.httpHeaders=Jenkins-Crumb:no-crumb\r\nevents.credentials=admin:library";
		String outputRequestBody = generateAction.preprocess(inputRequestBody, new FilePath(parmFile), logger);
		assertEquals("Failure of this test indicates a change in behavior", expectedOutput, outputRequestBody);
	}
	
	private class GenerateAction extends GenerateTasksInReleaseAction
	{
		public GenerateAction() {
			super(logger);
		}		
	}
}
