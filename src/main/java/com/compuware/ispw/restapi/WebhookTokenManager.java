package com.compuware.ispw.restapi;

import java.util.HashMap;

public class WebhookTokenManager {
	private static WebhookTokenManager instance;

	private HashMap<String, WebhookToken> tagToToken;

	private WebhookTokenManager() {
		tagToToken = new HashMap<String, WebhookToken>();
	}

	public synchronized static WebhookTokenManager getInstance() {
		if (instance == null) {
			instance = new WebhookTokenManager();
		}

		return instance;
	}

	public synchronized void put(String buildTag, WebhookToken token) {
		tagToToken.put(buildTag, token);
	}

	public WebhookToken get(String buildTag) {
		return tagToToken.get(buildTag);
	}

	public synchronized WebhookToken remove(String buildTag) {
		return tagToToken.remove(buildTag);
	}
}
