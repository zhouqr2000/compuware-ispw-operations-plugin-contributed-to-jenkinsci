package com.compuware.ispw.restapi;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.compuware.ces.communications.service.data.EventCallback;
import com.compuware.ces.model.BasicAuthentication;
import com.compuware.ces.model.HttpHeader;
import com.compuware.ispw.model.rest.SetInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JsonGenerator {

	private static Logger logger = Logger.getLogger(JsonGenerator.class);

	/*
	 * Moxy - not work public String generate(boolean includeRoot, Object object) { String json =
	 * "{}";
	 * 
	 * try { Map<String, Object> jaxbProperties = new HashMap<String, Object>(2);
	 * jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	 * jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, includeRoot); JAXBContext jc =
	 * JAXBContext.newInstance(new Class[] { object.getClass() }, jaxbProperties);
	 * 
	 * Marshaller marshaller = jc.createMarshaller();
	 * marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 * 
	 * StringWriter stringWriter = new StringWriter(); marshaller.marshal(object, stringWriter);
	 * 
	 * json = stringWriter.toString(); } catch (Exception x) { logger.error(x.getMessage(), x); }
	 * return json; }
	 */

	/*
	 * Jettison - not work public String generate(boolean includeRoot, Object object) { String json
	 * = "{}";
	 * 
	 * try { Map<String, Object> jaxbProperties = new HashMap<String, Object>(2); JAXBContext jc =
	 * JAXBContext.newInstance(new Class[] { object.getClass() }, jaxbProperties);
	 * 
	 * Configuration config = new Configuration(); MappedNamespaceConvention con = new
	 * MappedNamespaceConvention(config); StringWriter stringWriter = new StringWriter();
	 * MappedXMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, stringWriter);
	 * 
	 * Marshaller marshaller = jc.createMarshaller();
	 * marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); marshaller.marshal(object,
	 * xmlStreamWriter);
	 * 
	 * json = stringWriter.toString(); } catch (Exception x) { logger.error(x.getMessage(), x); }
	 * return json; }
	 */

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
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
	}

	public static void main(String[] args) {
		JsonGenerator generator = new JsonGenerator();
		generator.test();
	}
}
