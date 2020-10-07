package com.compuware.ispw.restapi.action;

import java.io.PrintStream;

import org.apache.commons.lang3.StringUtils;

import com.compuware.ispw.model.rest.ReleaseInfo;
import com.compuware.ispw.model.rest.ReleaseResponse;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;

/**
 * Create release action
 * 
 * @author Sam Zhou
 *
 */
public class CreateReleaseAction extends AbstractPostAction {

	private static final String[] defaultProps = new String[] { application, stream, description,
			releaseId };

	private static final String contextPath = "/ispw/{srid}/releases";

	public CreateReleaseAction(PrintStream logger) {
		super(logger);
	}
	
	@Override
	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			WebhookToken webhookToken) {

		ReleaseInfo releaseInfo = new ReleaseInfo();
		return super.getIspwRequestBean(srid, ispwRequestBody, contextPath, releaseInfo);
	}

	@Override
	public void startLog(PrintStream logger, IspwContextPathBean ispwContextPathBean, Object jsonObject)
	{
		ReleaseInfo releaseInfo = (ReleaseInfo) jsonObject;

		String releaseMsg = StringUtils.trimToEmpty(
				StringUtils.isBlank(releaseInfo.getReleaseId()) ? "releasePrefix:" + releaseInfo.getReleasePrefix()
						: "releaseId:" + releaseInfo.getReleaseId());

		logger.println("Creating Release on " + releaseInfo.getStream() + "/" + releaseInfo.getApplication() + " as "
				+ releaseMsg + " - " + releaseInfo.getDescription());
	}

	@Override
	public Object endLog(PrintStream logger, IspwRequestBean ispwRequestBean, String responseJson)
	{
		ReleaseResponse releaseResp = new JsonProcessor().parse(responseJson, ReleaseResponse.class);
		logger.println("Created Release " + releaseResp.getReleaseId());
		
		return releaseResp;
	}

}
