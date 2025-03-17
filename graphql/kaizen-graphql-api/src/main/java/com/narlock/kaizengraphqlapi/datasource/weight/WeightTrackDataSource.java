package com.narlock.kaizengraphqlapi.datasource.weight;

import com.narlock.kaizengraphqlapi.model.weight.WeightEntry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WeightTrackDataSource {
  private final WebClient weightTrackWebClient;

  public WeightEntry createWeightEntry(WeightEntry weightEntry) {
    return weightTrackWebClient
        .post()
        .uri(uriBuilder -> uriBuilder.build())
        .bodyValue(weightEntry)
        .retrieve()
        .bodyToMono(WeightEntry.class)
        .block();
  }

  public WeightEntry updateWeightEntry(WeightEntry weightEntry) {
    return weightTrackWebClient
        .put()
        .uri(uriBuilder -> uriBuilder.build())
        .bodyValue(weightEntry)
        .retrieve()
        .bodyToMono(WeightEntry.class)
        .block();
  }

  public Boolean deleteWeightEntriesByProfile(Integer profileId) {
    weightTrackWebClient
        .delete()
        .uri(uriBuilder -> uriBuilder.queryParam("profileId", profileId).build())
        .retrieve()
        .bodyToMono(Void.class)
        .block();
    return true;
  }

  public WeightEntry getWeightEntry(Integer profileId, String entryDate) {
    List<WeightEntry> weightEntryList =
        weightTrackWebClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .queryParam("profileId", profileId)
                        .queryParam("entryDate", entryDate)
                        .build())
            .retrieve()
            .bodyToFlux(WeightEntry.class)
            .collectList()
            .block();
    return weightEntryList.get(0);
  }

  public List<WeightEntry> getWeightEntries(Integer profileId) {
    return weightTrackWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.queryParam("profileId", profileId).build())
        .retrieve()
        .bodyToFlux(WeightEntry.class)
        .collectList()
        .block();
  }

  public List<WeightEntry> getWeightEntriesByRange(
      Integer profileId, String startDate, String endDate) {
    return weightTrackWebClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/range")
                    .queryParam("profileId", profileId)
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .build())
        .retrieve()
        .bodyToFlux(WeightEntry.class)
        .collectList()
        .block();
  }
}
