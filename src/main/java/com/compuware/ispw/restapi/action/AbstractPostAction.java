package com.compuware.ispw.restapi.action;

import org.apache.commons.lang3.StringUtils;

import com.compuware.ispw.restapi.IspwRequestBean;
import com.compuware.ispw.restapi.JsonProcessor;
import com.compuware.ispw.restapi.util.RestApiUtils;

public abstract class AbstractPostAction implements IAction {

	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody,
			String contextPath, Object jsonObject) {

		IspwRequestBean bean = new IspwRequestBean();

		String path = contextPath.replace("{srid}", srid);

		String[] lines = ispwRequestBody.split("\n");
		for (String line : lines) {
			line = StringUtils.trimToEmpty(line);
			int indexOfEqualSign = line.indexOf("=");
			if (indexOfEqualSign != -1) {
				String name = StringUtils.trimToEmpty(line.substring(0, indexOfEqualSign));
				String value =
						StringUtils
								.trimToEmpty(line.substring(indexOfEqualSign + 1, line.length()));

				if (StringUtils.isNotBlank(value)) {
					RestApiUtils.reflectSetter(jsonObject, name, value);
				}
			}
		}

		bean.setContextPath(path);

		JsonProcessor jsonGenerator = new JsonProcessor();
		String jsonRequest = jsonGenerator.generate(jsonObject);
		bean.setJsonRequest(jsonRequest);

		return bean;
	}

}
