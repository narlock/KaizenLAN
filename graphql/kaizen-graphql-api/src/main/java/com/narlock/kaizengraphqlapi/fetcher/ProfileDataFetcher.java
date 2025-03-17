package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.profile.ProfileDataSource;
import com.narlock.kaizengraphqlapi.model.profile.Profile;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ProfileDataFetcher {

  private final ProfileDataSource profileDataSource;

  @DgsQuery
  public Profile profile(@InputArgument Integer id) {
    return profileDataSource.getProfileById(id);
  }

  @DgsMutation
  public Profile createProfile(@InputArgument Profile input) {
    return profileDataSource.createProfile(input);
  }

  @DgsMutation
  public Profile updateProfile(@InputArgument Profile input) {
    return profileDataSource.updateProfile(input);
  }

  @DgsMutation
  public Boolean deleteProfile(@InputArgument Integer id) {
    profileDataSource.deleteProfile(id);
    return true;
  }

  @DgsMutation
  public Profile addXpToProfile(@InputArgument Integer id, @InputArgument Integer xp) {
    return profileDataSource.addXpToProfile(id, xp);
  }
}
