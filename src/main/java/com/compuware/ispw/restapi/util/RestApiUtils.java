package com.compuware.ispw.restapi.util;

import static com.cloudbees.plugins.credentials.CredentialsMatchers.filter;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.withId;
import static com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.QueryParameter;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
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
import com.compuware.jenkins.common.configuration.CpwrGlobalConfiguration;
import com.compuware.jenkins.common.configuration.HostConnection;

import hudson.model.Item;
import hudson.security.ACL;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import jenkins.model.Jenkins;

public class RestApiUtils {

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
	
	public static ListBoxModel buildConnectionIdItems(@AncestorInPath Jenkins context, @QueryParameter String connectionId,
			@AncestorInPath Item project) {
		CpwrGlobalConfiguration globalConfig = CpwrGlobalConfiguration.get();
		HostConnection[] hostConnections = globalConfig.getHostConnections();

		ListBoxModel model = new ListBoxModel();
		model.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY, false));

		for (HostConnection connection : hostConnections)
		{
			boolean isSelected = false;
			if (connectionId != null)
			{
				isSelected = connectionId.matches(connection.getConnectionId());
			}

			model.add(new Option(connection.getDescription() + " [" + connection.getHostPort() + ']', //$NON-NLS-1$
					connection.getConnectionId(), isSelected));
		}

		return model;
	}
	
	public static HostConnection getCesUrl(String connectionId) {
		CpwrGlobalConfiguration globalConfig = CpwrGlobalConfiguration.get();
		HostConnection hostConnection = globalConfig.getHostConnection(connectionId);
		return hostConnection;
	}

	public static String getCesToken(String credentialsId) {
		List<StringCredentials> creds =
				filter(lookupCredentials(StringCredentials.class, Jenkins.getInstance(),
						ACL.SYSTEM, Collections.<DomainRequirement> emptyList()),
						withId(StringUtils.trimToEmpty(credentialsId)));

		String token = StringUtils.EMPTY;
		if (creds != null && creds.size() > 0) {
			StringCredentials cred = creds.get(0);
			token = cred.getSecret().getPlainText();
		}

		return token;
	}
	
	public static ListBoxModel buildCredentialsIdItems(@AncestorInPath Jenkins context, @QueryParameter String credentialsId,
			@AncestorInPath Item project)
	{
		List<StringCredentials> creds = CredentialsProvider.lookupCredentials(
				StringCredentials.class, project, ACL.SYSTEM,
				Collections.<DomainRequirement> emptyList());

		StandardListBoxModel model = new StandardListBoxModel();

		model.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY, false));

		for (StringCredentials c : creds) {
			boolean isSelected = false;

			if (credentialsId != null) {
				isSelected = credentialsId.matches(c.getId());
			}

			String description = StringUtils.trimToEmpty(c.getDescription());
			model.add(new Option(description, c.getId(), isSelected));
		}

		return model;
	}
	
	public static ListBoxModel buildIspwActionItems(@AncestorInPath Jenkins context, @QueryParameter String ispwAction,
			@AncestorInPath Item project) {
		
		ListBoxModel model = new ListBoxModel();
		
		model.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY, false));

		Arrays.sort(IspwCommand.publishedActions);
		
		for (String action : IspwCommand.publishedActions) {
			boolean isSelected = false;

			if (ispwAction != null) {
				isSelected = action.matches(ispwAction);
			}			

			model.add(new Option(action, action, isSelected));
		}

		return model;
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
