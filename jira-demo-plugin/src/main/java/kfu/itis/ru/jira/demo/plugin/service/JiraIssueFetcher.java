package kfu.itis.ru.jira.demo.plugin.service;

import java.util.List;
import javax.inject.Inject;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import org.springframework.stereotype.Component;
import kfu.itis.ru.jira.demo.plugin.config.JiraClientConfig;

@Component
public class JiraIssueFetcher {
  private final SearchService searchService;
  private final String projectKey;

  @Inject
  public JiraIssueFetcher(
      @ComponentImport SearchService searchService, JiraClientConfig clientConfig) {
    this.searchService = searchService;
    this.projectKey = clientConfig.getProjectKey();
  }

  public List<Issue> fetchOpenIssues() throws SearchException {
    ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

    Query query =
        JqlQueryBuilder.newBuilder()
            .where()
            .project(projectKey)
            .and()
            .resolution()
            .isEmpty()
            .buildQuery();

    return searchService.search(user, query, PagerFilter.getUnlimitedFilter()).getIssues();
  }
}