package com.compuware.ispw.restapi.action;

import java.util.Arrays;

import hudson.util.ListBoxModel;

public class IspwCommand {
	public static String CreateAssignment = "CreateAssignment"; // POST
	public static String GetAssignmentInfo = "GetAssignmentInfo"; // GET
	public static String GetAssignmentTaskList = "GetAssignmentTaskList"; // GET
	public static String GenerateTasksInAssignment = "GenerateTasksInAssignment"; // POST
	public static String PromoteAssignment = "PromoteAssignment"; // POST
	public static String DeployAssignment = "DeployAssignment"; // POST
	public static String RegressAssignment = "RegressAssignment"; // POST
	public static String GetReleaseInfo = "GetReleaseInfo"; // GET
	public static String GetReleaseTaskList = "GetReleaseTaskList"; // GET
	public static String CreateRelease = "CreateRelease"; // POST
	public static String GenerateTasksInRelease = "GenerateTasksInRelease"; // POST
	public static String GetReleaseTaskGenerateListing = "GetReleaseTaskGenerateListing"; // GET
	public static String GetReleaseTaskInfo = "GetReleaseTaskInfo"; // GET
	public static String PromoteRelease = "PromoteRelease"; // POST
	public static String DeployRelease = "DeployRelease"; // POST
	public static String RegressRelease = "RegressRelease"; // POST

	private static String[] publishedActions = new String[] { CreateAssignment, GetAssignmentInfo,
			GetAssignmentTaskList, GenerateTasksInAssignment, PromoteAssignment, DeployAssignment,
			RegressAssignment, GetReleaseInfo, GetReleaseTaskList, CreateRelease,
			GenerateTasksInRelease, GetReleaseTaskGenerateListing, GetReleaseTaskInfo,
			PromoteRelease, DeployRelease, RegressRelease };

	public static ListBoxModel getFillItems() {
		ListBoxModel items = new ListBoxModel();

		Arrays.sort(publishedActions);
		
		for (String action : publishedActions) {
			items.add(action);
		}

		return items;
	}
}
