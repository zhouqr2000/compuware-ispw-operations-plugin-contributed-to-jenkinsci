package com.compuware.ispw.restapi.action;

import org.apache.commons.lang.StringUtils;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.WebhookToken;

public class GetAssignmentTaskListAction implements IAction {

	public static String assignmentId = "assignmentId";
	public static String level = "level";

	public static String[] defaultProps = new String[] { assignmentId, level };

	public static String contextPath = "/ispw/{srid}/assignments/{assignmentId}/tasks?level={level}";

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {
		IspwRequestBean bean = new IspwRequestBean();

		String path = contextPath.replace("{srid}", srid);

		String[] lines = ispwRequestBody.split("\n");
		for (String line : lines) {
			line = StringUtils.trimToEmpty(line);
			int indexOfEqualSign = line.indexOf("=");
			if (indexOfEqualSign != -1) {
				String name = StringUtils.trimToEmpty(line.substring(0, indexOfEqualSign));
				String value =
						StringUtils
								.trimToEmpty(line.substring(indexOfEqualSign + 1, line.length()));

				if (StringUtils.isNotBlank(value)) {
					if (name.equalsIgnoreCase(assignmentId)) {
						path = path.replace("{assignmentId}", value);
					} else if (name.equalsIgnoreCase(level)) {
						path = path.replace("{level}", value);
					}
				}
			}
		}

		bean.setContextPath(path);

		return bean;
	}

}
