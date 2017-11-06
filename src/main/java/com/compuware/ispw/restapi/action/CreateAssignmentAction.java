package com.compuware.ispw.restapi.action;

import org.apache.commons.lang.StringUtils;

import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonGenerator;
import com.compuware.ispw.restapi.WebhookToken;

public class CreateAssignmentAction implements IAction {

	public static String stream = "stream";
	public static String application = "application";
	public static String defaultPath = "defaultPath";
	public static String description = "description";
	public static String owner = "owner";
	public static String assignmentPrefix = "assignmentPrefix";
	public static String referenceNumber = "referenceNumber";
	public static String releaseId = "releaseId";
	public static String userTag = "userTag";

	public static String[] defaultProps = new String[] { stream, application, defaultPath,
			description, owner, assignmentPrefix };

	public static String contextPath = "/ispw/{srid}/assignments";

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		IspwRequestBean bean = new IspwRequestBean();

		String path = contextPath.replace("{srid}", srid);

		AssignmentInfo assignmentInfo = new AssignmentInfo();
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

					if (name.equals(stream)) {
						assignmentInfo.setStream(value);
					} else if (name.equals(application)) {
						assignmentInfo.setApplication(value);
					} else if (name.equals(defaultPath)) {
						assignmentInfo.setDefaultPath(value);
					} else if (name.equals(description)) {
						assignmentInfo.setDescription(value);
					} else if (name.equals(owner)) {
						assignmentInfo.setOwner(value);
					} else if (name.equals(assignmentPrefix)) {
						assignmentInfo.setAssignmentPrefix(value);
					} else if (name.equals(referenceNumber)) {
						assignmentInfo.setRefNumber(value);
					} else if (name.equals(releaseId)) {
						assignmentInfo.setRelease(value);
					} else if (name.equals(userTag)) {
						assignmentInfo.setUserTag(value);
					}
				}
			}
		}

		bean.setContextPath(path);

		JsonGenerator jsonGenerator = new JsonGenerator();
		String jsonRequest = jsonGenerator.generate(assignmentInfo);
		bean.setJsonRequest(jsonRequest);

		return bean;
	}

}
