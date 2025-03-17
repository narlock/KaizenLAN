package com.narlock.kaizenprofileapi.repository;

import com.narlock.kaizenprofileapi.model.RowInfo;
import com.narlock.kaizenprofileapi.model.RowInfoId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RowInfoRepository extends JpaRepository<RowInfo, RowInfoId> {
  @Modifying
  @Transactional
  @Query(
      "INSERT INTO RowInfo (profileId, rowIndex, widgets) VALUES (:profileId, :rowIndex, :widgets)")
  void saveRowInfo(
      @Param("profileId") Integer profileId,
      @Param("rowIndex") Integer rowIndex,
      @Param("widgets") String widgets);

  @Modifying
  @Transactional
  @Query(
      "UPDATE RowInfo r SET r.profileId = :profileId, r.rowIndex = :rowIndex, r.widgets = :widgets WHERE r.profileId = :profileId AND r.rowIndex = :rowIndex")
  void updateRowInfo(
      @Param("profileId") Integer profileId,
      @Param("rowIndex") Integer rowIndex,
      @Param("widgets") String widgets);

  @Query(value = "SELECT * FROM RowInfo WHERE profile_id = :profileId", nativeQuery = true)
  List<RowInfo> findByProfileIdNative(@Param("profileId") Integer profileId);
}
