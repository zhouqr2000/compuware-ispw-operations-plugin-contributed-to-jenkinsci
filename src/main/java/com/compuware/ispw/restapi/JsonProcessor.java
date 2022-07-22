package com.compuware.ispw.restapi;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import com.compuware.ces.communications.service.data.EventCallback;
import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;
import com.compuware.ispw.model.rest.AssignmentInfo;
import com.compuware.ispw.model.rest.SetInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * JSON marshaller and unmarshaller
 * 
 * @author Sam Zhou
 */
public class JsonProcessor {

	private static Logger logger = Logger.getLogger(JsonProcessor.class);
	
	public String generate(Object object) {
		String json = "{}";

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.writerWithDefaultPrettyPrinter();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			AnnotationIntrospector introspector =
					new JaxbAnnotationIntrospector(mapper.getTypeFactory());
			mapper.setAnnotationIntrospector(introspector);

			StringWriter stringWriter = new StringWriter();
			mapper.writeValue(stringWriter, object);
			
			json = stringWriter.toString();			
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		return json;
	}

	public <T> T parse(String json, Class<T> clazz) {
		T object = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.writerWithDefaultPrettyPrinter();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			AnnotationIntrospector introspector =
					new JaxbAnnotationIntrospector(mapper.getTypeFactory());
			mapper.setAnnotationIntrospector(introspector);

			object = mapper.readValue(json, clazz);
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		return object;
	}

	public void test() {
		try {
			String body = "{\"text\":\"Generated!\"}";
			String encodeBody = URLEncoder.encode(body, "UTF-8");
			SetInfo setInfo = new SetInfo();

			setInfo.setRuntimeConfig("TPZP");
			setInfo.setExecStat("I");

			ArrayList<EventCallback> events = new ArrayList<EventCallback>();
			EventCallback event = new EventCallback();
			event.setName("complete");
			event.setUrl("http://localhost:8080/jenkins/ispw-webhook-step/wait");
			event.setBody(encodeBody);
			events.add(event);
			setInfo.setEventCallbacks(events);

			ArrayList<HttpHeader> httpHeaders = new ArrayList<HttpHeader>();
			HttpHeader httpHeader = new HttpHeader();
			httpHeader.setName("Jenkins-Crumb");
			httpHeader.setValue("no-security");
			httpHeaders.add(httpHeader);
			event.setHttpHeaders(httpHeaders);

			BasicAuthentication auth = new BasicAuthentication();
			auth.setUsername("admin");
			auth.setPassword("library");
			event.setCredentials(auth);

			String json = generate(setInfo);
			logger.info("setInfo json=" + json);

			SetInfo setInfo2 = parse(json, SetInfo.class);
			logger.info("setInfo2="
					+ ReflectionToStringBuilder.toString(setInfo2, ToStringStyle.MULTI_LINE_STYLE));

			String jsonAssignInfo =
					"{\"stream\":\"PLAY\",\"application\":\"PLAY\",\"defaultPath\":\"DEV2\",\"description\":\"JkGen\",\"owner\":\"PMISVZ0\",\"refNumber\":\" \",\"releaseId\":\" \",\"userTag\":\" \",\"assignmentId\":\"PLAY000313\"}";
			AssignmentInfo assignInfo = parse(jsonAssignInfo, AssignmentInfo.class);

			logger.info("assignInfo="
					+ ReflectionToStringBuilder
							.toString(assignInfo, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
	}

	public static void main(String[] args) {
		JsonProcessor generator = new JsonProcessor();
		generator.test();
	}
}
