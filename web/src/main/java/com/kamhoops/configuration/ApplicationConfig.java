package com.kamhoops.configuration;

import com.kamhoops.view.BuildInformationView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Application Configuration
 * <p/>
 * These are global configuration values
 */
@Configuration
public class ApplicationConfig {

   @Value("${test.data.generate=false}")
    private boolean testDataGenerationRequired;

    @Value("${git.commit.id=}")
    private String gitCommitId;

    @Value("${git.commit.user.email=}")
    private String gitCommitUserEmail;

    @Value("${git.commit.id.abbrev=}")
    private String gitCommitIdAbbreviated;

    @Value("${git.branch=}")
    private String gitBranch;

    @Value("${git.build.time=}")
    private String gitBuildTime;

    private BuildInformationView buildInformationView;

    public BuildInformationView getBuildInformationView() {
        if (buildInformationView == null) {
            this.buildInformationView = new BuildInformationView() {
                @Override
                public String getCommitUserEmail() {
                    return gitCommitUserEmail;
                }

                @Override
                public String getCommitId() {
                    return gitCommitId;
                }

                @Override
                public String getCommitIdAbbreviated() {
                    return gitCommitIdAbbreviated;
                }

                @Override
                public String getBranch() {
                    return gitBranch;
                }

                @Override
                public String getBuildTime() {
                    return gitBuildTime;
                }
            };
        }

        return buildInformationView;
    }

    public boolean isTestDataGenerationRequired() {
        return testDataGenerationRequired;
    }
}
