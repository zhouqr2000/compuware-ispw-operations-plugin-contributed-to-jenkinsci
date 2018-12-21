package com.compuware.ispw.restapi.util;

import static com.cloudbees.plugins.credentials.CredentialsMatchers.filter;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.withId;
import static com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials;
import java.io.PrintStream;
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
import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.model.rest.AssignmentResponse;
import com.compuware.ispw.model.rest.MessageResponse;
import com.compuware.ispw.model.rest.ReleaseInfo;
import com.compuware.ispw.model.rest.ReleaseResponse;
import com.compuware.ispw.model.rest.SetInfoResponse;
import com.compuware.ispw.model.rest.TaskInfo;
import com.compuware.ispw.model.rest.TaskListResponse;
import com.compuware.ispw.model.rest.TaskListingResponse;
import com.compuware.ispw.model.rest.TaskResponse;
import com.compuware.ispw.restapi.Constants;
import com.compuware.ispw.restapi.HttpMode;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.ResponseContentSupplier;
import com.compuware.ispw.restapi.action.IspwCommand;
import com.compuware.jenkins.common.configuration.CpwrGlobalConfiguration;
import com.compuware.jenkins.common.configuration.HostConnection;
import hudson.model.Item;
import hudson.security.ACL;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import jenkins.model.Jenkins;

/**
 * Utilities for ISPW operations plug-in
 * 
 * @author Sam Zhou
 *
 */
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
	
	//Fix CES bug - CWE-124094 - Get assignment/release/set task list doesn't return a JSON array ("tasks":[]) if they contains just one task
	public static String fixCesTaskListResponseJson(String responseJson) {
		String fixedResponseJson = responseJson;
		
		if(responseJson.startsWith("{\"tasks\":{")) {
			fixedResponseJson = responseJson.replace("{\"tasks\":{", "{\"tasks\":[{");
			fixedResponseJson = fixedResponseJson.replace("}}", "}]}");
		}
		
		return fixedResponseJson;
	}
	
	public static Object endLog(PrintStream logger, String ispwAction, IspwRequestBean ispwRequestBean, String responseJson, boolean block) {
		Object returnObject = null;
		JsonProcessor jsonProcessor = new JsonProcessor();
		
		if (IspwCommand.GenerateTasksInAssignment.equals(ispwAction)) {
			TaskResponse taskResponse = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResponse.getSetId()+" created to generate");
			returnObject = taskResponse;
		} else if (IspwCommand.GetAssignmentTaskList.equals(ispwAction)) {
			
			String fixedResponseJson = fixCesTaskListResponseJson(responseJson);
			TaskListResponse listResponse = jsonProcessor.parse(fixedResponseJson, TaskListResponse.class);
			
			logger.println("TaskId, Module, Type, UserId, Version, Status, Application/Stream/Level, Release");
			for(TaskInfo taskInfo: listResponse.getTasks()) {
				logger.println(" " + taskInfo.getTaskId() + ", " + taskInfo.getModuleName() + ", "
						+ taskInfo.getModuleType() + ", " + taskInfo.getUserId() + ", "
						+ taskInfo.getVersion() + ", " + taskInfo.getStatus() + ", "
						+ taskInfo.getApplication() + "/" + taskInfo.getStream() + "/"
						+ taskInfo.getLevel() + ", " + taskInfo.getRelease());
			}
			returnObject = listResponse;
		} else if (IspwCommand.GetAssignmentInfo.equals(ispwAction)) {
			AssignmentInfo assignment = jsonProcessor.parse(responseJson, AssignmentInfo.class);
			logger.println("Stream/Application/Default path: " + assignment.getStream() + "/"
					+ assignment.getApplication() + "/" + assignment.getDefaultPath());
			logger.println("Assignment: " + assignment.getProjectNumber() + " - "
					+ assignment.getDescription());
			logger.println("Owner: " + assignment.getOwner());
			logger.println("Rreference number: " + assignment.getRefNumber());
			logger.println("Release: " + assignment.getRelease());
			logger.println("User tag: " + assignment.getUserTag());
			returnObject = assignment;
		} else if (IspwCommand.CreateAssignment.equals(ispwAction)) {
			AssignmentResponse assignResp =
					jsonProcessor.parse(responseJson, AssignmentResponse.class);
			logger.println("Created Assignment " + assignResp.getAssignmentId());
			returnObject = assignResp;
		} else if (IspwCommand.PromoteAssignment.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to promote Assignment "+ispwRequestBean.getIspwContextPathBean().getAssignmentId());
			returnObject = taskResp;
		} else if (IspwCommand.DeployAssignment.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to deploy Assignment "+ispwRequestBean.getIspwContextPathBean().getAssignmentId());	
			returnObject = taskResp;
		} else if (IspwCommand.RegressAssignment.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to regress Assignment "+ispwRequestBean.getIspwContextPathBean().getAssignmentId());
			returnObject = taskResp;
		} else if (IspwCommand.GetReleaseInfo.equals(ispwAction)) {
			ReleaseInfo releaseInfo = jsonProcessor.parse(responseJson, ReleaseInfo.class);
			logger.println("Stream/Application: " + releaseInfo.getStream() + "/"
					+ releaseInfo.getApplication());
			logger.println("Release: " + releaseInfo.getReleaseId() + " - "
					+ releaseInfo.getDescription());
			logger.println("Owner: " + releaseInfo.getOwner());
			logger.println("Work reference #: " + releaseInfo.getWorkRefNumber());
			logger.println("Release reference: " + releaseInfo.getReleasePreference());
			logger.println("User tag: " + releaseInfo.getUserTag());
			returnObject = releaseInfo;
		} else if (IspwCommand.GetReleaseTaskList.equals(ispwAction)) {
			
			String fixedResponseJson = fixCesTaskListResponseJson(responseJson);
			TaskListResponse listResponse = jsonProcessor.parse(fixedResponseJson, TaskListResponse.class);
			
			logger.println("TaskId, Module, Type, UserId, Version, Status, Application/Stream/Level, Release");
			for(TaskInfo taskInfo: listResponse.getTasks()) {
				logger.println(" " + taskInfo.getTaskId() + ", " + taskInfo.getModuleName() + ", "
						+ taskInfo.getModuleType() + ", " + taskInfo.getUserId() + ", "
						+ taskInfo.getVersion() + ", " + taskInfo.getStatus() + ", "
						+ taskInfo.getApplication() + "/" + taskInfo.getStream() + "/"
						+ taskInfo.getLevel() + ", " + taskInfo.getRelease());
			}
			returnObject = listResponse;
		} else if (IspwCommand.CreateRelease.equals(ispwAction)) {
			ReleaseResponse releaseResp = jsonProcessor.parse(responseJson, ReleaseResponse.class);
			logger.println("Created Release " + releaseResp.getReleaseId());
			returnObject = releaseResp;
		} else if (IspwCommand.GenerateTasksInRelease.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to generate Release "+ispwRequestBean.getIspwContextPathBean().getReleaseId());
			returnObject = taskResp;;
		} else if (IspwCommand.GetReleaseTaskGenerateListing.equals(ispwAction)) {
			TaskListingResponse listingResp = jsonProcessor.parse(responseJson, TaskListingResponse.class);
			logger.println("Listing: "+listingResp.getListing());
			returnObject = listingResp;
		} else if (IspwCommand.GetReleaseTaskInfo.equals(ispwAction)) {
			TaskInfo taskInfo = jsonProcessor.parse(responseJson,  TaskInfo.class);
			logger.println("TaskId, Module, Type, UserId, Version, Status, Application/Stream/Level, Release");
			logger.println(" " + taskInfo.getTaskId() + ", " + taskInfo.getModuleName() + ", "
					+ taskInfo.getModuleType() + ", " + taskInfo.getUserId() + ", "
					+ taskInfo.getVersion() + ", " + taskInfo.getStatus() + ", "
					+ taskInfo.getApplication() + "/" + taskInfo.getStream() + "/"
					+ taskInfo.getLevel() + ", " + taskInfo.getRelease());
			returnObject = taskInfo;
		} else if (IspwCommand.DeployRelease.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to deploy Release "+ispwRequestBean.getIspwContextPathBean().getReleaseId());
			returnObject = taskResp;
		} else if (IspwCommand.PromoteRelease.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to promote Release "+ispwRequestBean.getIspwContextPathBean().getReleaseId());
			returnObject = taskResp;
		} else if (IspwCommand.RegressRelease.equals(ispwAction)) {
			TaskResponse taskResp = jsonProcessor.parse(responseJson, TaskResponse.class);
			logger.println("Set "+taskResp.getSetId()+" created to regress Release "+ispwRequestBean.getIspwContextPathBean().getReleaseId());
			returnObject = taskResp;
		} else if(IspwCommand.GetSetInfoAction.equals(ispwAction)) {
			SetInfoResponse setInfoResp = jsonProcessor.parse(responseJson, SetInfoResponse.class);
			logger.println("SetId, State, Owner, Application/Stream, Start Date/Start Time");
			logger.println(" " + setInfoResp.getSetid() + ", " + setInfoResp.getState() + ", "
					+ setInfoResp.getOwner() + ", " + setInfoResp.getApplicationId() + "/"
					+ setInfoResp.getStreamName() + ", " + setInfoResp.getStartDate() + "/"
					+ setInfoResp.getStartTime());
			returnObject = setInfoResp;
		} else if (IspwCommand.GetSetTaskListAction.equals(ispwAction)) {
			
			String fixedResponseJson = fixCesTaskListResponseJson(responseJson);
			TaskListResponse listResponse = jsonProcessor.parse(fixedResponseJson, TaskListResponse.class);
			
			logger.println("TaskId, Module, Type, UserId, Version, Status, Application/Stream/Level, Release");
			for(TaskInfo taskInfo: listResponse.getTasks()) {
				logger.println(" " + taskInfo.getTaskId() + ", " + taskInfo.getModuleName() + ", "
						+ taskInfo.getModuleType() + ", " + taskInfo.getUserId() + ", "
						+ taskInfo.getVersion() + ", " + taskInfo.getStatus() + ", "
						+ taskInfo.getApplication() + "/" + taskInfo.getStream() + "/"
						+ taskInfo.getLevel() + ", " + taskInfo.getRelease());
			}
			returnObject = listResponse;
		}
		
		logger.println("ISPW Operation Complete");
		
		return returnObject;
	}

	public static HttpMode resetHttpMode(String ispwAction) {
		HttpMode httpMode = HttpMode.POST;

		if (IspwCommand.GetAssignmentInfo.equals(ispwAction)
				|| IspwCommand.GetAssignmentTaskList.equals(ispwAction)
				|| IspwCommand.GetReleaseInfo.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskList.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskGenerateListing.equals(ispwAction)
				|| IspwCommand.GetReleaseTaskInfo.equals(ispwAction)
				|| IspwCommand.GetSetInfoAction.equals(ispwAction)
				|| IspwCommand.GetSetTaskListAction.equals(ispwAction)) {
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
	
	public static ListBoxModel buildIspwActionItems(
			@AncestorInPath Jenkins context, @QueryParameter String ispwAction,
			@AncestorInPath Item project) {

		String[] publishedActions = ReflectUtils.listPublishedCommands();
		
		ListBoxModel model = new ListBoxModel();

		model.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY, false));

		Arrays.sort(publishedActions);

		for (String action : publishedActions) {
			boolean isSelected = false;

			if (ispwAction != null) {
				isSelected = action.matches(ispwAction);
			}

			model.add(new Option(action, action, isSelected));
		}

		return model;
	}
	
	public static String maskToken(String token) {
		
		if (isIspwDebugMode()) {
			return token;
		} else {
			StringBuffer masked =
					new StringBuffer(StringUtils.trimToEmpty(token));
			
			if (token.length() >= 8) {
				masked.setLength(0);
				String s1 = token.substring(0, 2);
				masked.append(s1);
				
				for(int i=0; i<token.length()-4; i++)
					masked.append("*");
				
				String s2 = token.substring(token.length() - 2, token.length());
				masked.append(s2);
			}

			return masked.toString();
		}
		
	}
	
	public static void logMessageIfAny(PrintStream logger, ResponseContentSupplier response,
			boolean isError) {
		String jsonContent = StringUtils.trimToEmpty(response.getContent());

		if (jsonContent.startsWith("{") && jsonContent.endsWith("}")) {

			// print ISPW error message if any
			JsonProcessor jsonProcessor = new JsonProcessor();
			MessageResponse messageResp = jsonProcessor.parse(jsonContent, MessageResponse.class);

			// tidy the message
			String message =
					StringUtils.trimToEmpty(messageResp.getMessage()).replaceAll("(\\s)+", " ")
							.replaceAll("( \\.)", ".");
			
			if (StringUtils.isNotBlank(message)) {
				if (isError)
					logger.println("Error - " + message);
				else
					logger.println("Message - " + message);
			}
		}
	}
	
	public static String getSystemProperty(String key) {
		String result = System.getProperty(key);
		return StringUtils.trimToEmpty(result);
	}
	
	public static boolean isIspwDebugMode() {
		String debugMode = getSystemProperty(Constants.ISPW_DEBUG_MODE);
		return Constants.TRUE.equalsIgnoreCase(debugMode);
	}

}
