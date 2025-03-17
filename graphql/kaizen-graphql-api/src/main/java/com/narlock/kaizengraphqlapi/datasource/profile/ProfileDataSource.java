package com.narlock.kaizengraphqlapi.datasource.profile;

import com.narlock.kaizengraphqlapi.datasource.profile.model.ProfileModel;
import com.narlock.kaizengraphqlapi.datasource.profile.model.RowInfoModel;
import com.narlock.kaizengraphqlapi.datasource.profile.model.RowInfoRequestModel;
import com.narlock.kaizengraphqlapi.model.profile.Profile;
import com.narlock.kaizengraphqlapi.model.profile.RowInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ProfileDataSource {

  private final WebClient profileWebClient;
  private final ProfileMapper profileMapper;

  public Profile getProfileById(int id) {
    ProfileModel profileModel =
        profileWebClient
            .get()
            .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
            .retrieve()
            .bodyToMono(ProfileModel.class)
            .block();
    return profileMapper.map(profileModel);
  }

  public Profile createProfile(Profile input) {
    ProfileModel profileModel =
        profileWebClient
            .post()
            .uri(uriBuilder -> uriBuilder.build())
            .bodyValue(input)
            .retrieve()
            .bodyToMono(ProfileModel.class)
            .block();
    return profileMapper.map(profileModel);
  }

  public Profile updateProfile(Profile input) {
    ProfileModel profileModel =
        profileWebClient
            .put()
            .uri(uriBuilder -> uriBuilder.build())
            .bodyValue(input)
            .retrieve()
            .bodyToMono(ProfileModel.class)
            .block();
    return profileMapper.map(profileModel);
  }

  public void deleteProfile(Integer id) {
    profileWebClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public List<RowInfo> createRowInfo(Integer id, Integer rowIndex, String widgets) {
    RowInfoRequestModel body =
        RowInfoRequestModel.builder().rowIndex(rowIndex).widgets(widgets).build();
    List<RowInfoModel> rowInfoModelList =
        profileWebClient
            .post()
            .uri(uriBuilder -> uriBuilder.path("/{id}/rowInfo").build(id))
            .bodyValue(body)
            .retrieve()
            .bodyToFlux(RowInfoModel.class)
            .collectList()
            .block();
    return profileMapper.mapRowInfoList(rowInfoModelList);
  }

  public List<RowInfo> updateRowInfo(Integer id, Integer rowIndex, String widgets) {
    RowInfoRequestModel body =
        RowInfoRequestModel.builder().rowIndex(rowIndex).widgets(widgets).build();
    List<RowInfoModel> rowInfoModelList =
        profileWebClient
            .put()
            .uri(uriBuilder -> uriBuilder.path("/{id}/rowInfo").build(id))
            .bodyValue(body)
            .retrieve()
            .bodyToFlux(RowInfoModel.class)
            .collectList()
            .block();
    return profileMapper.mapRowInfoList(rowInfoModelList);
  }

  public void deleteRowInfo(Integer id, Integer rowIndex) {
    profileWebClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/{id}/rowInfo/{rowIndex}").build(id, rowIndex))
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public Profile addXpToProfile(Integer id, Integer xp) {
    ProfileModel profileModel =
        profileWebClient
            .post()
            .uri(
                uriBuilder ->
                    uriBuilder.path("/xp").queryParam("id", id).queryParam("xp", xp).build())
            .retrieve()
            .bodyToMono(ProfileModel.class)
            .block();
    return profileMapper.map(profileModel);
  }
}
