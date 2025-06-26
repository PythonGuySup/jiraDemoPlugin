package kfu.itis.ru.jira.demo.plugin.config;

import org.springframework.stereotype.Component;

@Component
public class BotConfig {
  private final String token = "BOT_TOKEN";
  private final String username = "LOG_USERNAME";


  public String getUsername() {
    return username;
  }

  public String getToken() {
    return token;
  }
}