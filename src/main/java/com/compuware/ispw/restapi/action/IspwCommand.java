package com.compuware.ispw.restapi.action;

import hudson.util.ListBoxModel;

public class IspwCommand {
	public static String CreateAssignment = "CreateAssignment"; // POST
	public static String GetAssignmentInfo = "GetAssignmentInfo"; // GET
	public static String GetAssignmentTaskList = "GetAssignmentTaskList"; // GET
	public static String GenerateTasksInAssignment = "GenerateTasksInAssignment"; // POST
	public static String PromoteAssignment = "PromoteAssignment"; // POST
	public static String DeployAssignment = "DeployAssignment"; // POST
	public static String RegressAssignment = "RegressAssignment"; // POST

	private static String[] publishedActions = new String[] { CreateAssignment, GetAssignmentInfo,
			GetAssignmentTaskList, GenerateTasksInAssignment, PromoteAssignment, DeployAssignment,
			RegressAssignment };

	public static ListBoxModel getFillItems() {
		ListBoxModel items = new ListBoxModel();
		for (String action : publishedActions) {
			items.add(action);
		}

		return items;
	}
}
