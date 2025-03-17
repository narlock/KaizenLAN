package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.habit.HabitDataSource;
import com.narlock.kaizengraphqlapi.model.habit.Habit;
import com.narlock.kaizengraphqlapi.model.habit.HabitEntry;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class HabitDataFetcher {
  private final HabitDataSource habitDataSource;

  @DgsMutation
  public Habit createHabit(@InputArgument String name, @InputArgument Integer profileId) {
    return habitDataSource.createHabit(name, profileId);
  }

  @DgsMutation
  public HabitEntry createHabitEntry(
      @InputArgument String name, @InputArgument Integer profileId, @InputArgument String date) {
    return habitDataSource.createHabitEntry(name, profileId, date);
  }

  @DgsMutation
  public Boolean deleteHabit(@InputArgument String name, @InputArgument Integer profileId) {
    return habitDataSource.deleteHabit(name, profileId);
  }

  @DgsQuery
  public List<Habit> habits(@InputArgument Integer profileId) {
    return habitDataSource.getHabitsForProfile(profileId);
  }

  @DgsQuery
  public String habitEntryDate(
      @InputArgument String name, @InputArgument Integer profileId, @InputArgument String date) {
    return habitDataSource.getHabitByDate(name, profileId, date);
  }

  @DgsQuery
  public List<String> habitEntries(@InputArgument String name, @InputArgument Integer profileId) {
    return habitDataSource.getCompletedDatesForHabit(name, profileId);
  }

  @DgsQuery
  public Integer habitStreak(@InputArgument String name, @InputArgument Integer profileId) {
    return habitDataSource.getHabitStreak(name, profileId);
  }
}
