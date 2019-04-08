package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import org.apache.commons.lang.StringUtils;
import com.compuware.ispw.model.rest.TaskResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Set operation - support restart, terminate, hold, release, lock, unlock, delete; approve, deny, reset with approver provided
 * 
 * @author pmisvz0
 *
 */
public class SetOperationAction extends SetInfoPostAction
{
	public static final String SET_ACTION_HOLD = "hold";
	public static final String SET_ACTION_RELEASE = "release";
	public static final String SET_ACTION_LOCK = "lock";
	public static final String SET_ACTION_UNLOCK = "unlock";
	public static final String SET_ACTION_DELETE = "delete";
	public static final String SET_ACTION_RESTART = "restart";
	public static final String SET_ACTION_TERMINATE = "terminate";

	public static final String SET_ACTION_APPROVE = "approve";
	public static final String SET_ACTION_DENY = "deny";
	public static final String SET_ACTION_RESET = "reset";

	private static final String[] defaultProps = new String[]{runtimeConfiguration};

	private static final String contextPath = "/ispw/{srid}/sets/{setId}/{action}?approver={approver}";

	public SetOperationAction(PrintStream logger)
	{
		super(logger);
	}

	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, WebhookToken webhookToken)
	{
		return getIspwRequestBean(srid, ispwRequestBody, webhookToken, contextPath);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		String action = ispwContextPathBean.getAction();
		String setId = ispwContextPathBean.getSetId();
		String approver = ispwContextPathBean.getApprover();

		String message = String.format("Performing operation %s on set %s", action, setId);
		if (StringUtils.isNotBlank(approver))
		{
			message += " with approver as " + ispwContextPathBean.getApprover();
		}

		logger.println(message);
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		TaskResponse taskResp = new JsonProcessor().parse(responseJson, TaskResponse.class);
		logger.println("Completed operation on set " + taskResp.getSetId());

		return taskResp;
	}

}
