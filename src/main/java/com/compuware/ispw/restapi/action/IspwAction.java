/**
 * 
 */
package com.compuware.ispw.restapi.action;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
/**
 * Annotation to mark an ISPW command
 * 
 * @author Sam Zhou
 *
 */
public @interface IspwAction
{
	// the class to be instantiate to execute the action
	Class<?> clazz();

	// true to expose the action, false not
	boolean exposed() default true;
}
