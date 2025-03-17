package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.weight.WeightTrackDataSource;
import com.narlock.kaizengraphqlapi.model.weight.WeightEntry;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class WeightTrackDataFetcher {
  private final WeightTrackDataSource weightTrackDataSource;

  @DgsMutation
  public WeightEntry createWeightEntry(
      @InputArgument Integer profileId,
      @InputArgument String entryDate,
      @InputArgument Double entryAmount) {
    return weightTrackDataSource.createWeightEntry(
        WeightEntry.builder()
            .profileId(profileId)
            .entryDate(entryDate)
            .entryAmount(entryAmount)
            .build());
  }

  @DgsMutation
  public WeightEntry updateWeightEntry(
      @InputArgument Integer profileId,
      @InputArgument String entryDate,
      @InputArgument Double entryAmount) {
    return weightTrackDataSource.updateWeightEntry(
        WeightEntry.builder()
            .profileId(profileId)
            .entryDate(entryDate)
            .entryAmount(entryAmount)
            .build());
  }

  @DgsMutation
  public Boolean deleteWeightEntriesByProfile(@InputArgument Integer profileId) {
    return weightTrackDataSource.deleteWeightEntriesByProfile(profileId);
  }

  @DgsQuery
  public WeightEntry weightEntry(
      @InputArgument Integer profileId, @InputArgument String entryDate) {
    return weightTrackDataSource.getWeightEntry(profileId, entryDate);
  }

  @DgsQuery
  public List<WeightEntry> weightEntries(@InputArgument Integer profileId) {
    return weightTrackDataSource.getWeightEntries(profileId);
  }

  @DgsQuery
  public List<WeightEntry> weightEntriesByRange(
      @InputArgument Integer profileId,
      @InputArgument String startDate,
      @InputArgument String endDate) {
    return weightTrackDataSource.getWeightEntriesByRange(profileId, startDate, endDate);
  }
}
