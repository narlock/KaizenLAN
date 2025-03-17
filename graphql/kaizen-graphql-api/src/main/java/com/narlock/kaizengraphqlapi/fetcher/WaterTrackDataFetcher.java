package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.water.WaterTrackDataSource;
import com.narlock.kaizengraphqlapi.model.water.WaterEntry;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class WaterTrackDataFetcher {
  private final WaterTrackDataSource waterTrackDataSource;

  @DgsMutation
  public WaterEntry createWaterEntry(Integer profileId, String entryDate, Integer entryAmount) {
    return waterTrackDataSource.createWaterEntry(
        WaterEntry.builder()
            .profileId(profileId)
            .entryAmount(entryAmount)
            .entryDate(entryDate)
            .build());
  }

  @DgsMutation
  public WaterEntry addWaterToEntry(Integer profileId, String entryDate, Integer entryAmount) {
    return waterTrackDataSource.addWaterToEntry(
        WaterEntry.builder()
            .profileId(profileId)
            .entryDate(entryDate)
            .entryAmount(entryAmount)
            .build());
  }

  @DgsMutation
  public Boolean deleteWaterEntriesByProfile(Integer profileId) {
    return waterTrackDataSource.deleteWaterEntriesByProfile(profileId);
  }

  @DgsQuery
  public WaterEntry waterEntry(Integer profileId, String entryDate) {
    return waterTrackDataSource.getWaterEntry(profileId, entryDate);
  }

  @DgsQuery
  public List<WaterEntry> waterEntries(Integer profileId) {
    return waterTrackDataSource.getWaterEntries(profileId);
  }

  @DgsQuery
  public List<WaterEntry> waterEntriesByRange(Integer profileId, String startDate, String endDate) {
    return waterTrackDataSource.getWaterEntriesByRange(profileId, startDate, endDate);
  }
}
