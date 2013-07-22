package com.kamhoops.view;

/**
 * Build Information View
 * <p/>
 * Contains information about the current build
 */
public interface BuildInformationView {
    public String getCommitId();

    public String getCommitIdAbbreviated();

    public String getCommitUserEmail();

    public String getBranch();

    public String getBuildTime();
}
