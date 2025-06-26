package kfu.itis.ru.jira.demo.plugin.util;

import java.util.List;
import java.util.stream.Collectors;
import com.atlassian.jira.issue.Issue;

public final class IssueListFormatter {

  private IssueListFormatter() {
    // prevent instantiation
  }

  public static String formatIssues(List<Issue> issues) {
    return issues.stream()
        .map(
            issue ->
                String.format(
                    "%s: %s (%s)", issue.getKey(), issue.getSummary(), issue.getStatus().getName()))
        .collect(Collectors.joining(System.lineSeparator()));
  }
}