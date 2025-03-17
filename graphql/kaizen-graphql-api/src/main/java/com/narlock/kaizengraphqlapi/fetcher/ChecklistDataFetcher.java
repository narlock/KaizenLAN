package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.checklist.ChecklistDataSource;
import com.narlock.kaizengraphqlapi.model.checklist.Checklist;
import com.narlock.kaizengraphqlapi.model.checklist.ChecklistItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ChecklistDataFetcher {
  private final ChecklistDataSource checklistDataSource;

  @DgsMutation
  public Checklist createChecklist(
      @InputArgument String name,
      @InputArgument Integer profileId,
      @InputArgument String repeatEvery) {
    return checklistDataSource.createChecklist(name, profileId, repeatEvery);
  }

  @DgsMutation
  public Boolean deleteChecklist(@InputArgument String name, @InputArgument Integer profileId) {
    checklistDataSource.deleteChecklist(name, profileId);
    return true;
  }

  @DgsQuery
  public List<Checklist> checklists(@InputArgument Integer profileId) {
    return checklistDataSource.getChecklists(profileId);
  }

  @DgsQuery
  public ChecklistItem checklistItem(
      @InputArgument Integer id,
      @InputArgument String checklistName,
      @InputArgument Integer profileId) {
    return checklistDataSource.getChecklistItem(id, checklistName, profileId);
  }

  @DgsQuery
  public List<ChecklistItem> checklistItems(
      @InputArgument String checklistName, @InputArgument Integer profileId) {
    return checklistDataSource.getChecklistItems(checklistName, profileId);
  }

  @DgsMutation
  public Boolean deleteChecklistItem(@InputArgument Integer id) {
    checklistDataSource.deleteChecklistItem(id);
    return true;
  }

  @DgsMutation
  public ChecklistItem createChecklistItem(@InputArgument ChecklistItem checklistItem) {
    return checklistDataSource.createChecklistItem(checklistItem);
  }

  @DgsMutation
  public ChecklistItem completeChecklistItem(
      @InputArgument Integer id,
      @InputArgument String checklistName,
      @InputArgument Integer profileId) {
    return checklistDataSource.completeChecklistItem(id, checklistName, profileId);
  }
}
