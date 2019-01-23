package com.compuware.ispw.restapi.action;

import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.compuware.ces.communications.service.data.EventCallback;
import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;
import com.compuware.ispw.restapi.IspwContextPathBean;
import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.WebhookToken;
import com.compuware.ispw.restapi.util.ReflectUtils;
import com.compuware.ispw.restapi.util.RestApiUtils;

/**
 * A generic type post action
 * 
 * @author Sam Zhou
 *
 */
public abstract class GenericPostAction<T> extends AbstractPostAction {

	public GenericPostAction(PrintStream logger) {
		super(logger);
	}
	
	public IspwRequestBean getIspwRequestBean(Class<T> postClazz, String srid, String ispwRequestBody,
			WebhookToken webhookToken, String contextPath) {

		IspwRequestBean bean = new IspwRequestBean();

		IspwContextPathBean ispwContextPathBean = new IspwContextPathBean();
		ispwContextPathBean.setSrid(srid);
		bean.setIspwContextPathBean(ispwContextPathBean);
		
		String path = contextPath.replace("{srid}", srid);
		T postObject = ReflectUtils.newInstance(postClazz);
		
		bean.setJsonObject(postObject);

		boolean hasEvent = false;
		ArrayList<EventCallback> events = new ArrayList<EventCallback>();
		EventCallback event = new EventCallback();
		events.add(event);

		String[] lines = ispwRequestBody.split("\n");
		for (String line : lines) {
			line = StringUtils.trimToEmpty(line);
			
			if(line.startsWith("#")) {
				continue;
			}
			
			int indexOfEqualSign = line.indexOf("=");
			if (indexOfEqualSign != -1) {
				String name = StringUtils.trimToEmpty(line.substring(0, indexOfEqualSign));
				String value =
						StringUtils
								.trimToEmpty(line.substring(indexOfEqualSign + 1, line.length()));

				if (StringUtils.isNotBlank(value)) {
					if (name.equals(assignmentId)) {
						path = path.replace("{" + assignmentId + "}", value);
						ispwContextPathBean.setAssignmentId(value);
					} else if(name.equals(releaseId)) {
						path = path.replace("{" + releaseId + "}", value);
						ispwContextPathBean.setReleaseId(value);
					} else if (name.equals(level)) {
						path = path.replace("{" + level + "}", value);
						ispwContextPathBean.setLevel(value);
					} else if (name.equals(mname)) {
						path = path.replace("{" + mname + "}", value);
						ispwContextPathBean.setMname(value);
					} else if (name.equals(mtype)) {
						path = path.replace("{" + mtype + "}", value);
						ispwContextPathBean.setMtype(value);
					} else if (name.equals(checkout)) {
						path = path.replace("{" + checkout + "}", value);
						ispwContextPathBean.setMtype(value);
					} else if (name.equals(httpHeaders)) {
						ArrayList<HttpHeader> httpHeaders = RestApiUtils.toHttpHeaders(value);
						if (!httpHeaders.isEmpty()) {
							ReflectUtils.reflectSetter(postObject, "httpHeaders", value);
						}
					} else if (name.equals(credentials)) {
						BasicAuthentication auth = RestApiUtils.toBasicAuthentication(value);
						if (auth != null) {
							ReflectUtils.reflectSetter(postObject, "credentials", auth);
						}
					} else if (name.equals(eventsName)) {
						hasEvent = true;
						event.setName(value);
					} else if (name.equals(eventsMethod)) {
						hasEvent = true;
						event.setMethod(value);
					} else if (name.equals(eventsBody)) {
						hasEvent = true;
						event.setBody(value);
					} else if (name.equals(eventsHttpHeaders)) {
						ArrayList<HttpHeader> httpHeaders = RestApiUtils.toHttpHeaders(value);
						if (!httpHeaders.isEmpty()) {
							hasEvent = true;
							event.setHttpHeaders(httpHeaders);
						}
					} else if (name.equals(eventsCredentials)) {
						BasicAuthentication auth = RestApiUtils.toBasicAuthentication(value);
						if (auth != null) {
							hasEvent = true;
							event.setCredentials(auth);
						}
					} else {
						ReflectUtils.reflectSetter(postObject, name, value); // set the rest of the
																				// SetInfo fields using
																				// reflection
					}

				}
			}
		}

		if (webhookToken != null && hasEvent) {
			event.setUrl(webhookToken.getURL());
			ReflectUtils.reflectSetter(postObject, "eventCallbacks", events);
		}
		
		//if query parms are not set, remove them from query string
		path = path.replace("level={level}", StringUtils.EMPTY);
		path = path.replace("mname={mname}", StringUtils.EMPTY);
		path = path.replace("mtype={mtype}", StringUtils.EMPTY);
		path = path.replace("checkout={checkout}", StringUtils.EMPTY);
		
		int index = path.indexOf("?");
		if (index != -1) {
			String s1 = path.substring(0, index);
			String s2 = path.substring(index);
			s2 = s2.replaceAll("[&]+", "&");
			path = s1 + s2;
			
			if (path.endsWith("&")) {
				path = path.substring(0, path.length() - 1);
			}
		}
		
		bean.setContextPath(path);

		JsonProcessor jsonGenerator = new JsonProcessor();
		String jsonRequest = jsonGenerator.generate(postObject);
		bean.setJsonRequest(jsonRequest);
		return bean;
	}

}
