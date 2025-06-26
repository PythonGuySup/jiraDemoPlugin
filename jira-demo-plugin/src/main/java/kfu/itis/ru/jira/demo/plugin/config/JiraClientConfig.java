package kfu.itis.ru.jira.demo.plugin.config;

import org.springframework.stereotype.Component;

@Component
public class JiraClientConfig {
  private final String projectKey = "DEMO";


  public String getProjectKey() {
    return projectKey;
  }
}