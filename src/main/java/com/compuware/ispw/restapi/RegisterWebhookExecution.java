package com.compuware.ispw.restapi;

import org.apache.log4j.Logger;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import hudson.EnvVars;
import hudson.model.Hudson;

public class RegisterWebhookExecution extends AbstractSynchronousStepExecution<WebhookToken> {

    private static final long serialVersionUID = -6718328636399912927L;
    private static Logger logger = Logger.getLogger(RegisterWebhookExecution.class);
    
    public RegisterWebhookExecution(StepContext context) {
        super(context);
    }

	@Override
	public WebhookToken run() throws Exception {
		EnvVars envVars = getContext().get(hudson.EnvVars.class);
		String token = java.util.UUID.randomUUID().toString();

		String jenkinsUrl = envVars.get("JENKINS_URL");
		if (jenkinsUrl == null || jenkinsUrl.isEmpty()) {
			throw new RuntimeException("JENKINS_URL must be set in the Manage Jenkins console");
		}

		java.net.URI baseUri = new java.net.URI(jenkinsUrl);
		java.net.URI relative = new java.net.URI("ispw-webhook-step/" + token);
		java.net.URI path = baseUri.resolve(relative);

		WebhookToken webhookToken = new WebhookToken(token, path.toString());

		String buildTag = envVars.get("BUILD_TAG");
		WebhookTokenManager.getInstance().put(buildTag, webhookToken);

		logger.info("...registering buildTag=" + buildTag + ", webhookToken=" + webhookToken.toString());

		return webhookToken;
	}
}
