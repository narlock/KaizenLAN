package com.narlock.kaizengraphqlapi.datasource.water;

import com.narlock.kaizengraphqlapi.model.water.WaterEntry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WaterTrackDataSource {

  private final WebClient waterTrackWebClient;

  public WaterEntry createWaterEntry(WaterEntry waterEntry) {
    return waterTrackWebClient
        .post()
        .uri(uriBuilder -> uriBuilder.build())
        .bodyValue(waterEntry)
        .retrieve()
        .bodyToMono(WaterEntry.class)
        .block();
  }

  public WaterEntry addWaterToEntry(WaterEntry waterEntry) {
    return waterTrackWebClient
        .patch()
        .uri(uriBuilder -> uriBuilder.build())
        .bodyValue(waterEntry)
        .retrieve()
        .bodyToMono(WaterEntry.class)
        .block();
  }

  public Boolean deleteWaterEntriesByProfile(Integer profileId) {
    waterTrackWebClient
        .delete()
        .uri(uriBuilder -> uriBuilder.queryParam("profileId", profileId).build())
        .retrieve()
        .bodyToMono(Void.class)
        .block();
    return true;
  }

  public WaterEntry getWaterEntry(Integer profileId, String entryDate) {
    List<WaterEntry> waterEntryList =
        waterTrackWebClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .queryParam("profileId", profileId)
                        .queryParam("entryDate", entryDate)
                        .build())
            .retrieve()
            .bodyToFlux(WaterEntry.class)
            .collectList()
            .block();
    return waterEntryList.get(0);
  }

  public List<WaterEntry> getWaterEntries(Integer profileId) {
    return waterTrackWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.queryParam("profileId", profileId).build())
        .retrieve()
        .bodyToFlux(WaterEntry.class)
        .collectList()
        .block();
  }

  public List<WaterEntry> getWaterEntriesByRange(
      Integer profileId, String startDate, String endDate) {
    return waterTrackWebClient
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
        .bodyToFlux(WaterEntry.class)
        .collectList()
        .block();
  }
}
