package com.narlock.checklistapi.repository;

import com.narlock.checklistapi.model.ChecklistItem;
import com.narlock.checklistapi.model.ChecklistItemId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, ChecklistItemId> {

  @Query(
      "SELECT c FROM ChecklistItem c WHERE c.checklistName = :checklistName AND c.profileId = :profileId")
  List<ChecklistItem> findByChecklistName(
      @Param("checklistName") String checklistName, @Param("profileId") Integer profileId);

  @Query(
      "SELECT c FROM ChecklistItem c WHERE c.checklistName = :checklistName AND c.profileId = :profileId AND c.name = :name")
  ChecklistItem findByChecklistNameProfileIdName(
      @Param("checklistName") String checklistName,
      @Param("profileId") Integer profileId,
      @Param("name") String name);

  @Modifying
  @Query(
      "DELETE FROM ChecklistItem c WHERE c.checklistName = :checklistName AND c.profileId = :profileId")
  void deleteByChecklistNameAndProfileId(
      @Param("checklistName") String checklistName, @Param("profileId") Integer profileId);

  @Modifying
  @Query("DELETE FROM ChecklistItem c WHERE c.id = :id")
  void deleteByChecklistItemId(@Param("id") Integer id);
}
