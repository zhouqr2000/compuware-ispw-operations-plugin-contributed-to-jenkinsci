package com.compuware.ispw.restapi.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;

public class RestApiUtils {

	public static String CES_URL = "ces.url";
	public static String CES_ISPW_HOST = "ces.ispw.host";
	public static String CES_ISPW_TOKEN = "ces.ispw.token";
	
	private static Logger logger = Logger.getLogger(RestApiUtils.class);
	
	public static String join(String delimiter, String[] stringArray, boolean appendEqualSign) {
		String result = StringUtils.EMPTY;

		StringBuilder sb = new StringBuilder();
		if (stringArray != null) {
			for (String string : stringArray) {
				sb.append(string).append(appendEqualSign ? "=" : StringUtils.EMPTY)
						.append(delimiter);
			}
		}

		if (sb.length() > 0) {
			result = sb.toString();
			result = result.substring(0, result.length() - delimiter.length());
		}

		return result;
	}

	public static ArrayList<HttpHeader> toHttpHeaders(String flat) {
		ArrayList<HttpHeader> headers = new ArrayList<HttpHeader>();

		String[] nameValues = flat.split(";");
		for (String nameValue : nameValues) {
			nameValue = StringUtils.trimToEmpty(nameValue);
			if (StringUtils.isNotEmpty(nameValue)) {
				int indexOfColon = nameValue.indexOf(":");
				if (indexOfColon != -1) {
					String name = StringUtils.trimToEmpty(nameValue.substring(0, indexOfColon));
					String value =
							StringUtils.trimToEmpty(nameValue.substring(indexOfColon + 1,
									nameValue.length()));

					if (StringUtils.isNotBlank(value)) {
						HttpHeader header = new HttpHeader();
						header.setName(name);
						header.setValue(value);
						headers.add(header);
					}
				}
			}
		}

		return headers;
	}

	public static BasicAuthentication toBasicAuthentication(String flat) {
		BasicAuthentication auth = null;

		int indexOfColon = flat.indexOf(":");
		if (indexOfColon != -1) {
			String username = StringUtils.trimToEmpty(flat.substring(0, indexOfColon));
			String password =
					StringUtils.trimToEmpty(flat.substring(indexOfColon + 1, flat.length()));
			auth = new BasicAuthentication();
			auth.setUsername(username);
			auth.setPassword(password);
		}

		return auth;
	}

	public static boolean containsIgnoreCase(List<String> tokens, String anotherToken) {
		for (String token : tokens) {
			if (token.equalsIgnoreCase(anotherToken))
				return true;
		}

		return false;
	}
	
	//TODO, the following will be replaced by global settings in Jenkins in next Srpint.
	public static String getCesUrl() {
		return getSystemProperty(CES_URL);
	}
	
	public static String getCesIspwHost() {
		return getSystemProperty(CES_ISPW_HOST);
	}
	
	public static String getCesIspwToken() {
		return getSystemProperty(CES_ISPW_TOKEN);
	}
	
	public static String getSystemProperty(String key) {
		String result = System.getProperty(key);
		if(StringUtils.isBlank(result)) {
			String errorMessage = "You must provide a system property: "+key+" to use ISPW RestAPI Jenkins plugin";
			throw new RuntimeException(errorMessage);
		}
		
		return StringUtils.trimToEmpty(result);
	}
}
