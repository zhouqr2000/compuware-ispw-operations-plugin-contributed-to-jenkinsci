package com.compuware.ispw.restapi.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.compuware.ispw.restapi.IspwRequestBean;

public abstract class AbstractGetAction implements IAction {

	public IspwRequestBean getIspwRequestBean(String srid, String ispwRequestBody, String contextPath, List<String> pathTokens) {
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
					if (pathTokens.contains(name)) {
						path = path.replace("{" + name + "}", value);
					}
				}
			}
		}

		bean.setContextPath(path);
		return bean;

	}

}
