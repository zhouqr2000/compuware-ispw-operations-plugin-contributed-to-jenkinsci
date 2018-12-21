package com.compuware.ispw.restapi.util;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import com.compuware.ispw.restapi.action.IAction;
import com.compuware.ispw.restapi.action.IspwAction;
import com.compuware.ispw.restapi.action.IspwCommand;

/**
 * Use annotation and reflection to read and instantiate ISPW actions
 * 
 * @author Sam Zhou
 *
 */
public class ReflectUtils
{
	private static Logger logger = Logger.getLogger(ReflectUtils.class);

	public static String[] listPublishedCommands()
	{
		ArrayList<String> commands = new ArrayList<String>();
		List<Field> fields = FieldUtils.getAllFieldsList(IspwCommand.class);

		for (Field field : fields)
		{
			if (field.isAnnotationPresent(IspwAction.class))
			{
				IspwAction ispwAction = field.getAnnotation(IspwAction.class);
				Class<?> clazz = ispwAction.clazz();

				String command = StringUtils.EMPTY;
				try
				{
					command = (String) FieldUtils.readStaticField(field);
					commands.add(command);
				}
				catch (IllegalAccessException e)
				{
					String message = String.format("Failed to read command value in field: %s, clazz: %s", command,
							clazz.getName());
					logger.error(message, e);
				}
			}
		}

		return commands.toArray(new String[commands.size()]);
	}

	public static Class<?> getCommandClass(String command)
	{
		Class<?> clazz = null;

		List<Field> fields = FieldUtils.getAllFieldsList(IspwCommand.class);

		for (Field field : fields)
		{
			if (field.isAnnotationPresent(IspwAction.class))
			{
				String definedCommand = StringUtils.EMPTY;
				try
				{
					definedCommand = (String) FieldUtils.readStaticField(field);
				}
				catch (IllegalAccessException e)
				{
					String message = String.format("Failed to read command value in field: %s", command);
					logger.error(message, e);
				}

				if (definedCommand.equals(command))
				{
					IspwAction ispwAction = field.getAnnotation(IspwAction.class);
					clazz = ispwAction.clazz();
				}
			}
		}

		String message = String.format("Reflect to get command %s -> class %s", command, clazz.getName());
		logger.info(message);

		return clazz;
	}

	public static IAction createAction(String command, PrintStream log)
	{
		IAction action = null;
		Class<?> clazz = getCommandClass(command);

		if (clazz != null)
		{
			try
			{
				action = (IAction) ConstructorUtils.invokeConstructor(clazz, log);
			}
			catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)
			{
				String message = String.format("Failed to instantiate command: %s from action class: %s", command,
						clazz.getName());
				log.println(message);

				logger.error(message, e);
			}
		}

		String message = String.format("Reflect to instantiate command %s -> Class %s -> instance %s", command, clazz.getName(),
				action.toString());
		logger.info(message);

		return action;
	}
}
