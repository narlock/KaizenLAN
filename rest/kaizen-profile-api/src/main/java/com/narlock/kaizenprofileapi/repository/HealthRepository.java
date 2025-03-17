package com.narlock.kaizenprofileapi.repository;

import com.narlock.kaizenprofileapi.model.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HealthRepository extends JpaRepository<Health, Integer> {
  @Modifying
  @Transactional
  @Query(
      "INSERT INTO Health (profileId, height, weight, goalWeight, goalWater) VALUES (:profileId, :height, :weight, :goalWeight, :goalWater)")
  void saveHealth(
      @Param("profileId") Integer profileId,
      @Param("height") Double height,
      @Param("weight") Double weight,
      @Param("goalWeight") Double goalWeight,
      @Param("goalWater") Double goalWater);

  @Modifying
  @Transactional
  @Query(
      "UPDATE Health h SET h.height = :height, h.weight = :weight, h.goalWeight = :goalWeight, h.goalWater = :goalWater WHERE h.profileId = :profileId")
  int updateHealth(
      @Param("profileId") Integer profileId,
      @Param("height") Double height,
      @Param("weight") Double weight,
      @Param("goalWeight") Double goalWeight,
      @Param("goalWater") Double goalWater);

  void deleteByProfileId(Integer profileId);
}
