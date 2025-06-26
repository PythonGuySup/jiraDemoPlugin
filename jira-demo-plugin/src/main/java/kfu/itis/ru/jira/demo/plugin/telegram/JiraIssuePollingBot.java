package kfu.itis.ru.jira.demo.plugin.telegram;

import java.util.List;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import kfu.itis.ru.jira.demo.plugin.config.BotConfig;
import kfu.itis.ru.jira.demo.plugin.service.JiraIssueFetcher;
import kfu.itis.ru.jira.demo.plugin.util.IssueListFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class JiraIssuePollingBot extends TelegramLongPollingBot {
  private static final Logger log = LoggerFactory.getLogger(JiraIssuePollingBot.class);

  private final JiraIssueFetcher issueFetcher;
  private final String botUsername;

  public JiraIssuePollingBot(BotConfig config, JiraIssueFetcher issueFetcher) {
    super(config.getToken());
    this.issueFetcher = issueFetcher;
    this.botUsername = config.getUsername();
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (!update.hasMessage() || !"/issues".equals(update.getMessage().getText())) {
      return;
    }

    String chatId = String.valueOf(update.getMessage().getChatId());

    try {
      List<Issue> rawResponse = issueFetcher.fetchOpenIssues();
      String parsedResponse = IssueListFormatter.formatIssues(rawResponse);
      SendMessage message = SendMessage.builder().chatId(chatId).text(parsedResponse).build();
      execute(message);
    } catch (TelegramApiException e) {
      log.error("Failed to send message: {}", e.getMessage());
    } catch (SearchException e) {
      log.error("Failed to find issues: {}", e.getMessage());
    }
  }
}