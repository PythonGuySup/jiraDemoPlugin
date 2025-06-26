package kfu.itis.ru.jira.demo.plugin.telegram;

import com.atlassian.sal.api.lifecycle.LifecycleAware;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Component
public class BotRegistrar implements LifecycleAware, InitializingBean, DisposableBean {
  private static final Logger log = LoggerFactory.getLogger(BotRegistrar.class);
  private final JiraIssuePollingBot bot;
  private TelegramBotsApi botsApi;

  public BotRegistrar(JiraIssuePollingBot bot) {
    this.bot = bot;
  }

  @Override
  public void onStart() {
    try {
      this.botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(bot);
      log.info("Telegram bot successfully registered");
    } catch (TelegramApiException e) {
      log.error("Failed to register Telegram bot", e);
    }
  }

  @Override
  public void onStop() {
    // Cleanup if needed
  }

  @Override
  public void afterPropertiesSet() {
    onStart();
  }

  @Override
  public void destroy() {
    onStop();
  }
}