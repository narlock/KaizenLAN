package com.narlock.kaizengraphqlapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${kaizen.host}")
  private String HOST;

  @Value("${api.profile.context}")
  private String PROFILE_CONTEXT;

  @Value("${api.profile.port}")
  private String PROFILE_PORT;

  @Bean
  public WebClient profileWebClient() {
    return WebClient.builder().baseUrl(HOST + PROFILE_PORT + PROFILE_CONTEXT).build();
  }

  @Value("${api.habit.port}")
  private String HABIT_PORT;

  @Bean
  public WebClient habitWebClient() {
    return WebClient.builder().baseUrl(HOST + HABIT_PORT).build();
  }

  @Value("${api.checklist.port}")
  private String CHECKLIST_PORT;

  @Bean
  public WebClient checklistWebClient() {
    return WebClient.builder().baseUrl(HOST + CHECKLIST_PORT).build();
  }

  @Value("${api.weight.context}")
  private String WEIGHT_TRACK_CONTEXT;

  @Value("${api.weight.port}")
  private String WEIGHT_TRACK_PORT;

  @Bean
  public WebClient weightTrackWebClient() {
    return WebClient.builder().baseUrl(HOST + WEIGHT_TRACK_PORT + WEIGHT_TRACK_CONTEXT).build();
  }

  @Value("${api.water.context}")
  private String WATER_TRACK_CONTEXT;

  @Value("${api.water.port}")
  private String WATER_TRACK_PORT;

  @Bean
  public WebClient waterTrackWebClient() {
    return WebClient.builder().baseUrl(HOST + WATER_TRACK_PORT + WATER_TRACK_CONTEXT).build();
  }

  @Value("${api.notepad.context}")
  private String NOTEPAD_CONTEXT;

  @Value("${api.notepad.port}")
  private String NOTEPAD_PORT;

  @Bean
  public WebClient notepadWebClient() {
    return WebClient.builder().baseUrl(HOST + NOTEPAD_PORT + NOTEPAD_CONTEXT).build();
  }
}
