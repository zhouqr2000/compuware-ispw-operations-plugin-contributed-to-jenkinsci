package com.compuware.ispw.restapi.action;

/**
 * All supported ISPW actions
 * 
 * @author Sam Zhou
 *
 */
public class IspwCommand {
	public static final String CreateAssignment = "CreateAssignment"; // POST
	public static final String GetAssignmentInfo = "GetAssignmentInfo"; // GET
	public static final String GetAssignmentTaskList = "GetAssignmentTaskList"; // GET
	public static final String GenerateTasksInAssignment = "GenerateTasksInAssignment"; // POST
	public static final String PromoteAssignment = "PromoteAssignment"; // POST
	public static final String DeployAssignment = "DeployAssignment"; // POST
	public static final String RegressAssignment = "RegressAssignment"; // POST
	public static final String GetReleaseInfo = "GetReleaseInfo"; // GET
	public static final String GetReleaseTaskList = "GetReleaseTaskList"; // GET
	public static final String CreateRelease = "CreateRelease"; // POST
	public static final String GenerateTasksInRelease = "GenerateTasksInRelease"; // POST
	public static final String GetReleaseTaskGenerateListing = "GetReleaseTaskGenerateListing"; // GET
	public static final String GetReleaseTaskInfo = "GetReleaseTaskInfo"; // GET
	public static final String PromoteRelease = "PromoteRelease"; // POST
	public static final String DeployRelease = "DeployRelease"; // POST
	public static final String RegressRelease = "RegressRelease"; // POST
	public static final String GetSetInfoAction = "GetSetInfoAction"; //GET

	public static final String[] publishedActions = new String[] { CreateAssignment, GetAssignmentInfo,
			GetAssignmentTaskList, GenerateTasksInAssignment, PromoteAssignment, DeployAssignment,
			RegressAssignment, GetReleaseInfo, GetReleaseTaskList, CreateRelease,
			GenerateTasksInRelease, GetReleaseTaskGenerateListing, GetReleaseTaskInfo,
			PromoteRelease, DeployRelease, RegressRelease, GetSetInfoAction };

}
