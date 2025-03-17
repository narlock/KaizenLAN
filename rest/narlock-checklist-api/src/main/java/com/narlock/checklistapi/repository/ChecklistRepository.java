package com.narlock.checklistapi.repository;

import com.narlock.checklistapi.model.Checklist;
import com.narlock.checklistapi.model.ChecklistId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ChecklistRepository extends JpaRepository<Checklist, ChecklistId> {
  @Modifying
  @Transactional
  @Query(
      "INSERT INTO Checklist(name, profileId, repeatEvery) VALUES (:name, :profileId, :repeatEvery)")
  void saveNewChecklist(
      @Param("name") String name,
      @Param("profileId") Integer profileId,
      @Param("repeatEvery") String repeatEvery);

  List<Checklist> findByProfileIdAndName(Integer profileId, String name);

  List<Checklist> findByProfileId(Integer profileId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Checklist c WHERE c.profileId = :profileId AND c.name = :name")
  void deleteByProfileIdAndName(@Param("profileId") Integer profileId, @Param("name") String name);
}
