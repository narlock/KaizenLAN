package com.narlock.kaizengraphqlapi.datasource.habit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narlock.kaizengraphqlapi.model.habit.Habit;
import com.narlock.kaizengraphqlapi.model.habit.HabitEntry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HabitDataSource {
  private final WebClient habitWebClient;

  public Habit createHabit(String name, Integer profileId) {
    Habit habit = Habit.builder().name(name).profileId(profileId).build();
    return habitWebClient
        .post()
        .uri(uriBuilder -> uriBuilder.path("/habit").build())
        .bodyValue(habit)
        .retrieve()
        .bodyToMono(Habit.class)
        .block();
  }

  public HabitEntry createHabitEntry(String name, Integer profileId, String date) {
    HabitEntry habitEntry = HabitEntry.builder().name(name).profileId(profileId).date(date).build();
    return habitWebClient
        .post()
        .uri(uriBuilder -> uriBuilder.path("/habit-entry").build())
        .bodyValue(habitEntry)
        .retrieve()
        .bodyToMono(HabitEntry.class)
        .block();
  }

  public List<Habit> getHabitsForProfile(Integer profileId) {
    return habitWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/habit").queryParam("profileId", profileId).build())
        .retrieve()
        .bodyToFlux(Habit.class)
        .collectList()
        .block();
  }

  public Integer getHabitStreak(String name, Integer profileId) {
    Habit habit = Habit.builder().name(name).profileId(profileId).build();
    return habitWebClient
        .method(HttpMethod.GET)
        .uri(uriBuilder -> uriBuilder.path("/habit/streak").build())
        .bodyValue(habit)
        .retrieve()
        .bodyToMono(Integer.class)
        .block();
  }

  public List<String> getCompletedDatesForHabit(String name, Integer profileId) {
    Mono<List<String>> response =
        habitWebClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .path("/habit-entry")
                        .queryParam("name", name)
                        .queryParam("profileId", profileId)
                        .build())
            .retrieve()
            .bodyToMono(String.class)
            .map(
                jsonString -> {
                  ObjectMapper mapper = new ObjectMapper();
                  try {
                    return mapper.readValue(jsonString, new TypeReference<>() {});
                  } catch (Exception e) {
                    throw new RuntimeException("Error parsing JSON", e);
                  }
                });

    return response.block();
  }

  public String getHabitByDate(String name, Integer profileId, String date) {
    try {
      List<String> dates =
          habitWebClient
              .get()
              .uri(
                  uriBuilder ->
                      uriBuilder
                          .path("/habit-entry")
                          .queryParam("name", name)
                          .queryParam("profileId", profileId)
                          .queryParam("date", date)
                          .build())
              .retrieve()
              .bodyToFlux(String.class)
              .collectList()
              .block();
      return date;
    } catch (WebClientResponseException e) {
      throw e;
    }
  }

  public Boolean deleteHabit(String name, Integer profileId) {
    habitWebClient
        .delete()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/habit")
                    .queryParam("name", name)
                    .queryParam("profileId", profileId)
                    .build())
        .retrieve()
        .bodyToMono(Void.class)
        .block();
    return true;
  }
}
