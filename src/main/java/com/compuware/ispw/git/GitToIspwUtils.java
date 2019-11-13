package com.compuware.ispw.git;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.QueryParameter;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.compuware.ispw.restapi.util.RestApiUtils;
import com.compuware.jenkins.common.configuration.CpwrGlobalConfiguration;
import com.compuware.jenkins.common.utils.CommonConstants;
import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.model.AbstractBuild;
import hudson.model.Item;
import hudson.model.Run;
import hudson.remoting.VirtualChannel;
import hudson.scm.ChangeLogSet;
import hudson.security.ACL;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import jenkins.model.Jenkins;

public class GitToIspwUtils
{

	public static ListBoxModel buildStandardCredentialsIdItems(@AncestorInPath Jenkins context,
			@QueryParameter String credentialsId, @AncestorInPath Item project)
	{
		List<StandardUsernamePasswordCredentials> creds = CredentialsProvider.lookupCredentials(
				StandardUsernamePasswordCredentials.class, project, ACL.SYSTEM, Collections.<DomainRequirement> emptyList());

		StandardListBoxModel model = new StandardListBoxModel();

		model.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY, false));

		for (StandardUsernamePasswordCredentials c : creds)
		{
			boolean isSelected = false;

			if (credentialsId != null)
			{
				isSelected = credentialsId.matches(c.getId());
			}

			String description = Util.fixEmptyAndTrim(c.getDescription());
			model.add(new Option(c.getUsername() + (description != null ? " (" + description + ")" : StringUtils.EMPTY), //$NON-NLS-1$ //$NON-NLS-2$
					c.getId(), isSelected));
		}

		return model;
	}

	public static ListBoxModel buildContainerPrefItems(@AncestorInPath Jenkins context, @QueryParameter String containerPref,
			@AncestorInPath Item project)
	{
		ListBoxModel model = new ListBoxModel();

		model.add(new Option(GitToIspwConstants.CONTAINER_PREF_PER_COMMIT, GitToIspwConstants.CONTAINER_PREF_PER_COMMIT));
		model.add(new Option(GitToIspwConstants.CONTAINER_PREF_PER_BRANCH, GitToIspwConstants.CONTAINER_PREF_PER_BRANCH));
		model.add(new Option(GitToIspwConstants.CONTAINER_PREF_CUSTOM, GitToIspwConstants.CONTAINER_PREF_CUSTOM));

		return model;
	}

	public static Map<String, RefMap> parse(String branchMapping)
	{
		Map<String, RefMap> map = new HashMap<String, RefMap>();

		String[] lines = branchMapping.split("\n"); //$NON-NLS-1$
		for (String line : lines)
		{
			line = StringUtils.trimToEmpty(line);

			if (line.startsWith("#")) //$NON-NLS-1$
			{
				continue;
			}

			int indexOfArrow = line.indexOf("=>"); //$NON-NLS-1$
			if (indexOfArrow != -1)
			{
				String pattern = StringUtils.trimToEmpty(line.substring(0, indexOfArrow));
				String ispwLevel = StringUtils.EMPTY;
				String containerPref = GitToIspwConstants.CONTAINER_PREF_PER_COMMIT;
				String containerDesc = StringUtils.EMPTY;

				String rest = line.substring(indexOfArrow + 2);
				StringTokenizer tokenizer = new StringTokenizer(rest, ",");
				if (tokenizer.hasMoreTokens())
				{
					ispwLevel = StringUtils.trimToEmpty(tokenizer.nextToken());
				}

				if (tokenizer.hasMoreElements())
				{
					containerPref = StringUtils.trimToEmpty(tokenizer.nextToken());
				}

				if (tokenizer.hasMoreElements())
				{
					containerDesc = StringUtils.trimToEmpty(tokenizer.nextToken());
				}

				RefMap refMap = new RefMap(ispwLevel, containerPref, containerDesc);
				map.put(pattern, refMap);
			}
		}

		return map;
	}

	/**
	 * Gets the ref, refId, fromHash, and toHash environment variables and trims them to empty.
	 * 
	 * @param envVars
	 *            the EnvVars for Jenkins
	 */
	public static void trimEnvironmentVariables(EnvVars envVars)
	{
		String toHash = envVars.get(GitToIspwConstants.VAR_TO_HASH, null);
		String fromHash = envVars.get(GitToIspwConstants.VAR_FROM_HASH, null);
		String ref = envVars.get(GitToIspwConstants.VAR_REF, null);
		String refId = envVars.get(GitToIspwConstants.VAR_REF_ID, null);

		envVars.put(GitToIspwConstants.VAR_TO_HASH, StringUtils.trimToEmpty(toHash));
		envVars.put(GitToIspwConstants.VAR_FROM_HASH, StringUtils.trimToEmpty(fromHash));
		envVars.put(GitToIspwConstants.VAR_REF, StringUtils.trimToEmpty(ref));
		envVars.put(GitToIspwConstants.VAR_REF_ID, StringUtils.trimToEmpty(refId));
	}

	/**
	 * Calls the IspwCLI and returns whether the execution was successful. Any exceptions thrown by the executor are caught and
	 * returned as a boolean.
	 * 
	 * @param launcher
	 *            the launcher
	 * @param build
	 *            the Jenkins Run
	 * @param logger
	 *            the logger
	 * @param mapDb
	 *            the database that store the Git push information.
	 * @param gitPushList
	 *            the list of GitPushInfos that is linked to the database. The IspwCLI will be called once for each push.
	 * @param envVars
	 *            the environment variables including ref, refId, fromHash, and toHash
	 * @param publishStep
	 *            The step that this CLI execution is being called from. The publish step is used to get the information input
	 *            into the Jenkins UI.
	 * @param workspacePath
	 *            The folder that holds the git repository cloned by the SCM step.
	 * @return a boolean indicating success.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static boolean callCli(Launcher launcher, Run<?, ?> build, PrintStream logger, EnvVars envVars, RefMap refMap,
			IGitToIspwPublish publishStep, String workspacePath, boolean isPrintHelpOnly) throws InterruptedException, IOException
	{
		CpwrGlobalConfiguration globalConfig = CpwrGlobalConfiguration.get();
		if (launcher == null)
		{
			return false;
		}
		VirtualChannel vChannel = launcher.getChannel();

		if (vChannel == null)
		{
			return false;
		}

		String toHash = envVars.get(GitToIspwConstants.VAR_TO_HASH, null);
		String fromHash = envVars.get(GitToIspwConstants.VAR_FROM_HASH, null);
		String ref = envVars.get(GitToIspwConstants.VAR_REF, null);
		String refId = envVars.get(GitToIspwConstants.VAR_REF_ID, null);
		
		if (!isPrintHelpOnly)
		{
			if (refMap == null)
			{
				logger.println("branch mapping is not defined for refId: " + refId);
				return false;
			}
			else
			{
				logger.println("mapping refId: " + refId + " to refMap=" + refMap.toString());
			}
		}
		Properties remoteProperties = vChannel.call(new RemoteSystemProperties());
		String remoteFileSeparator = remoteProperties.getProperty(CommonConstants.FILE_SEPARATOR_PROPERTY_KEY);
		String osFile = launcher.isUnix()
				? GitToIspwConstants.SCM_DOWNLOADER_CLI_SH
				: GitToIspwConstants.SCM_DOWNLOADER_CLI_BAT;
		String cliScriptFile = globalConfig.getTopazCLILocation(launcher) + remoteFileSeparator + osFile;
		logger.println("CLI Script File: " + cliScriptFile); //$NON-NLS-1$
		String cliScriptFileRemote = new FilePath(vChannel, cliScriptFile).getRemote();
		logger.println("CLI Script File Remote: " + cliScriptFileRemote); //$NON-NLS-1$

		String topazCliWorkspace = workspacePath + "\\" + CommonConstants.TOPAZ_CLI_WORKSPACE;
		logger.println("TopazCliWorkspace: " + topazCliWorkspace); //$NON-NLS-1$

		FilePath workDir = new FilePath(vChannel, build.getRootDir().toString());
		workDir.mkdirs();

		if (RestApiUtils.isIspwDebugMode())
		{
			String buildTag = envVars.get("BUILD_TAG"); //$NON-NLS-1$
			logger.println("Getting buildTag =" + buildTag);
		}

		boolean success = true;
		CliExecutor cliExecutor = new CliExecutor(logger, build, launcher, envVars, workspacePath, topazCliWorkspace,
				globalConfig, cliScriptFileRemote, workDir);
		try
		{
			String ispwLevel = StringUtils.EMPTY;
			String containerDesc = StringUtils.EMPTY;
			String containerPref = StringUtils.EMPTY;
			
			if (refMap != null)
			{
				ispwLevel = refMap.getIspwLevel();
				containerDesc = refMap.getContainerDesc();
				containerPref = refMap.getContainerPref();
			}
			success = cliExecutor.execute(publishStep.getConnectionId(), publishStep.getCredentialsId(),
					publishStep.getRuntimeConfig(), publishStep.getStream(), publishStep.getApp(), ispwLevel,
					containerPref, containerDesc, publishStep.getGitRepoUrl(),
					publishStep.getGitCredentialsId(), ref, refId, fromHash, toHash, isPrintHelpOnly);
		}
		catch (AbortException e)
		{
			logger.println(e);
			success = false;
		}

		if (!success)
		{
			if (fromHash.trim().isEmpty())
			{
				logger.println("Synchronization failed.");
			}
			else if (fromHash.contentEquals("-1"))
			{
				logger.println("Synchronization for " + toHash.trim().replaceAll(":",  " , ") + " failed.");
			}
			else
			{
				logger.println("Synchronization for push ending with commit " + toHash.trim() + " failed.");
			}
		}
		else
		{
			if (fromHash.contentEquals("-1"))
			{
				logger.println("Synchronization for " + toHash.trim().replaceAll(":",  " , ") + " was successful.");
			}
			else
			{
				logger.println("Synchronization for push ending with commit " + toHash.trim() + " was successful.");
			}
		}
		return success;
	}
	
	/**
	* Get the change sets 
	 * 
	 * @param run - job execution 
	 * 
	 * @return the <ChangeLogSet> List. Else, null.
	*/
	public static List<? extends ChangeLogSet<? extends ChangeLogSet.Entry>> getChangeSets(Run<?, ?> run, PrintStream logger)
	{
		if (run instanceof AbstractBuild)
		{
			//freestyle project
			return Collections.singletonList(((AbstractBuild<?, ?>) run).getChangeSet());
		}
		else if (run instanceof WorkflowRun)
		{
			//pipeline
			return ((WorkflowRun) run).getChangeSets();
		}

		return null;
	}
}
