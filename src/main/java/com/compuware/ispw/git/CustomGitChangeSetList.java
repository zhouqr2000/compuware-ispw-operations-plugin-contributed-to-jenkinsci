/**
 * These materials contain confidential information and trade secrets of Compuware Corporation. You shall maintain the materials
 * as confidential and shall not disclose its contents to any third party except as may be required by law or regulation. Use,
 * disclosure, or reproduction is prohibited without the prior express written permission of Compuware Corporation.
 * 
 * All Compuware products listed within the materials are trademarks of Compuware Corporation. All other company or product
 * names are trademarks of their respective owners.
 * 
 * Copyright (c) 2020 Compuware Corporation. All rights reserved.
 */
package com.compuware.ispw.git;

import hudson.model.Run;
import hudson.plugins.git.GitChangeSet;
import hudson.scm.ChangeLogSet;
import hudson.scm.RepositoryBrowser;
import org.kohsuke.stapler.export.Exported;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class CustomGitChangeSetList extends ChangeLogSet<ChangeLogSet.Entry> {
    private final List<ChangeLogSet.Entry> changeSets;
    
    
    public CustomGitChangeSetList(@SuppressWarnings("rawtypes") Run build, RepositoryBrowser<?> browser, List<GitChangeSet> logs) {
        super(build, browser);
        Collections.reverse(logs);  
        this.changeSets = Collections.unmodifiableList(logs);
        for (GitChangeSet log : logs)
            log.setParent(this);
    }

    public boolean isEmptySet() {
        return changeSets.isEmpty();
    }

    public Iterator<ChangeLogSet.Entry> iterator() {
        return changeSets.iterator();
    }

    public List<ChangeLogSet.Entry> getLogs() {
        return changeSets;
    }

    @SuppressWarnings("nls")
	@Exported
    public String getKind() {
        return "git";
    }

}
