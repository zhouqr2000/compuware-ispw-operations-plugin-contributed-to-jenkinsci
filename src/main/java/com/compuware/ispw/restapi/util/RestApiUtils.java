package com.compuware.ispw.restapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;
import com.compuware.ispw.restapi.HttpMode;
import com.compuware.ispw.restapi.action.CreateAssignmentAction;
import com.compuware.ispw.restapi.action.CreateReleaseAction;
import com.compuware.ispw.restapi.action.DeployAssignmentAction;
import com.compuware.ispw.restapi.action.DeployReleaseAction;
import com.compuware.ispw.restapi.action.GenerateTasksInAssignmentAction;
import com.compuware.ispw.restapi.action.GenerateTasksInReleaseAction;
import com.compuware.ispw.restapi.action.GetAssignmentInfoAction;
import com.compuware.ispw.restapi.action.GetAssignmentTaskListAction;
import com.compuware.ispw.restapi.action.GetReleaseInfoAction;
import com.compuware.ispw.restapi.action.GetReleaseTaskGenerateListingAction;
import com.compuware.ispw.restapi.action.GetReleaseTaskInfoAction;
import com.compuware.ispw.restapi.action.GetReleaseTaskListAction;
import com.compuware.ispw.restapi.action.IAction;
import com.compuware.ispw.restapi.action.IspwCommand;
import com.compuware.ispw.restapi.action.PromoteAssignmentAction;
import com.compuware.ispw.restapi.action.PromoteReleaseAction;
import com.compuware.ispw.restapi.action.RegressAssignmentAction;
import com.compuware.ispw.restapi.action.RegressReleaseAction;
import com.compuware.jenkins.common.configuration.CESConnection;
import com.compuware.jenkins.common.configuration.CESToken;
import com.compuware.jenkins.common.configuration.CpwrGlobalConfiguration;

import hudson.util.ListBoxModel;

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

	public static void reflectSetter(Object object, String name, String value) {

		List<Field> fields = FieldUtils.getAllFieldsList(object.getClass());
		for (Field field : fields) {

			String fieldName = field.getName();
			String jsonName = fieldName; // default to field name
			if (field.isAnnotationPresent(XmlElement.class)) {
				XmlElement xmlElement = field.getAnnotation(XmlElement.class);
				jsonName = xmlElement.name(); // use annotation name if presented
			}

			logger.info("json.name=" + jsonName + ", type=" + field.getType().getName()
					+ ", value=" + value);
			if (jsonName.equals(name)) {
				try {
					if (field.getType().equals(String.class)) {
						BeanUtils.setProperty(object, fieldName, value);
					} else if (field.getType().equals(Boolean.class)) {
						BeanUtils.setProperty(object, fieldName, Boolean.valueOf(value));
					}
				} catch (IllegalAccessException | InvocationTargetException e) {
					logger.warn("Property key " + name + "(" + jsonName
							+ ") is invalid, cannot be set to class " + object.getClass().getName()
							+ "as value [" + value + "])");
				}
			}
		}

	}

	public static IAction createAction(String ispwAction) {
		IAction action = null;

		if (IspwCommand.GenerateTasksInAssignment.equals(ispwAction)) {
			action = new GenerateTasksInAssignmentAction();
		} else if (IspwCommand.GetAssignmentTaskList.equals(ispwAction)) {
			action = new GetAssignmentTaskListAction();
		} else if (IspwCommand.GetAssignmentInfo.equals(ispwAction)) {
			action = new GetAssignmentInfoAction();
		} else if (IspwCommand.CreateAssignment.equals(ispwAction)) {
			action = new CreateAssignmentAction();
		} else if (IspwCommand.PromoteAssignment.equals(ispwAction)) {
			action = new PromoteAssignmentAction();
		} else if (IspwCommand.DeployAssignment.equals(ispwAction)) {
			action = new DeployAssignmentAction();
		} else if (IspwCommand.RegressAssignment.equals(ispwAction)) {
			action = new RegressAssignmentAction();
		} else if (IspwCommand.GetReleaseInfo.equals(ispwAction)) {
			action = new GetReleaseInfoAction();
		} else if (IspwCommand.GetReleaseTaskList.equals(ispwAction)) {
			action = new GetReleaseTaskListAction();
		} else if (IspwCommand.CreateRelease.equals(ispwAction)) {
			action = new CreateReleaseAction();
		} else if (IspwCommand.GenerateTasksInRelease.equals(ispwAction)) {
			action = new GenerateTasksInReleaseAction();
		} else if (IspwCommand.GetReleaseTaskGenerateListing.equals(ispwAction)) {
			action = new GetReleaseTaskGenerateListingAction();
		} else if (IspwCommand.GetReleaseTaskInfo.equals(ispwAction)) {
			action = new GetReleaseTaskInfoAction();
		} else if (IspwCommand.DeployRelease.equals(ispwAction)) {
			action = new DeployReleaseAction();
		} else if (IspwCommand.PromoteRelease.equals(ispwAction)) {
			action = new PromoteReleaseAction();
		} else if (IspwCommand.RegressRelease.equals(ispwAction)) {
			action = new RegressReleaseAction();
		}

		return action;
	}

	public static HttpMode resetHttpMode(String ispwAction) {
		HttpMode httpMode = HttpMode.POST;

		if (IspwCommand.GetAssignmentInfo.equals(ispwAction)
				|| IspwCommand.GetAssignmentTaskList.equals(ispwAction)
				|| IspwCommand.GetReleaseInfo.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskList.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskGenerateListing.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskInfo.equals(ispwAction)) {
			httpMode = HttpMode.GET;
		}

		return httpMode;
	}

	/**
	 * Get CES Connection
	 * 
	 * @return the CES connection
	 */
	public static CESConnection getCESConnection() {
		CpwrGlobalConfiguration globalConfig = CpwrGlobalConfiguration.get();
		CESConnection cesConnection = globalConfig.getCESConnection();
		
		return cesConnection;
	}

	public static Map<String, CESToken> getIspwHostToCesToken() {
		Map<String, CESToken> ispwHostToCesToken = new HashMap<String, CESToken>();

		CESConnection cesConnection = getCESConnection();
		List<CESToken> hostTokens = cesConnection.getTokens();
		for (CESToken hostToken : hostTokens) {
			String hostName = hostToken.getHostName();
			String key = hostName;
			
			ispwHostToCesToken.put(key, hostToken);
		}

		return ispwHostToCesToken;
	}
	
	public static ListBoxModel buildIspwHostItems() {
		ListBoxModel items = new ListBoxModel();
		
		Iterator<String> keys = getIspwHostToCesToken().keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			items.add(key);
		}

		return items;
	}
	
	public static String getSystemProperty(String key) {
		String result = System.getProperty(key);
		if (StringUtils.isBlank(result)) {
			String errorMessage =
					"You must provide a system property: " + key
							+ " to use ISPW RestAPI Jenkins plugin";
			throw new RuntimeException(errorMessage);
		}

		return StringUtils.trimToEmpty(result);
	}

}
