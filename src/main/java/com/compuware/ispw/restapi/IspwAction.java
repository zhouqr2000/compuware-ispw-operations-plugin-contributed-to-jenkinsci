package com.compuware.ispw.restapi;

import hudson.util.ListBoxModel;

public class IspwAction {
	public static String CreateAssignment = "Create Assignment"; // POST
	public static String GetAssignmentInfo = "Get Assignment Information"; // GET
	public static String GetAssignmentTaskList = "Get Assignment Task List"; // GET
	public static String GenerateTasksInAssignment = "Generate Tasks in Assignment";

	private static String[] publishedActions = new String[] { CreateAssignment, GetAssignmentInfo,
			GetAssignmentTaskList, GenerateTasksInAssignment };

	public static ListBoxModel getFillItems() {
		ListBoxModel items = new ListBoxModel();
		for (String action : publishedActions) {
			items.add(action);
		}

		return items;
	}
}
