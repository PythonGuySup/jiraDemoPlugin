package kfu.itis.ru.jira.demo.plugin.config;

import org.springframework.stereotype.Component;

@Component
public class BotConfig {
  private final String token = "7649005728:AAFP_kJilh51V0s6kG96E1qvznKULQUL3mk";
  private final String username = "log_koioes_bot";


  public String getUsername() {
    return username;
  }

  public String getToken() {
    return token;
  }
}