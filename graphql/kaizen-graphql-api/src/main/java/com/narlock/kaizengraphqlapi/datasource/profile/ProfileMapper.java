package com.narlock.kaizengraphqlapi.datasource.profile;

import com.narlock.kaizengraphqlapi.datasource.profile.model.ProfileModel;
import com.narlock.kaizengraphqlapi.datasource.profile.model.RowInfoModel;
import com.narlock.kaizengraphqlapi.model.profile.Profile;
import com.narlock.kaizengraphqlapi.model.profile.RowInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  @Mapping(target = "profile", source = "profile")
  @Mapping(target = "health", source = "health")
  @Mapping(target = "rowInfoList", source = "rowInfoList")
  Profile map(ProfileModel profileModel);

  default List<RowInfo> mapRowInfoList(List<RowInfoModel> rowInfoModels) {
    return rowInfoModels.stream().map(this::mapRowInfo).collect(Collectors.toList());
  }

  RowInfo mapRowInfo(RowInfoModel rowInfoModel);
}
