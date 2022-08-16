package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import org.apache.commons.lang3.StringUtils;
import com.compuware.ispw.git.GitToIspwUtils;
import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.model.rest.AssignmentResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Create assignment action
 * @author Sam Zhou
 *
 */
public class CreateAssignmentAction extends AbstractPostAction {

	private static final String[] defaultProps = new String[] { stream, application, defaultPath,
			description, owner, assignmentPrefix };

	private static final String contextPath = "/ispw/{srid}/assignments";

	public CreateAssignmentAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		AssignmentInfo assignmentInfo = new AssignmentInfo();
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, assignmentInfo);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		AssignmentInfo assignmentInfo = (AssignmentInfo) jsonObject;
		if (StringUtils.isNoneBlank(assignmentInfo.getSubAppl()))
		{
			logger.println("Creating assignment " + assignmentInfo.getStream() + "/" + assignmentInfo.getApplication() + "/"
					+ "/" + assignmentInfo.getSubAppl() + "/"	+ assignmentInfo.getDefaultPath() + " with description - " + assignmentInfo.getDescription());
		}
		else
		{
			logger.println("Creating assignment " + assignmentInfo.getStream() + "/" + assignmentInfo.getApplication() + "/"
					+ assignmentInfo.getDefaultPath() + " with description - " + assignmentInfo.getDescription());

		}
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		AssignmentResponse assignResp = new JsonProcessor().parse(responseJson, AssignmentResponse.class);
		logger.println("Created Assignment " + assignResp.getAssignmentId());
		return assignResp;
	}

}
