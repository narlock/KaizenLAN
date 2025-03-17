package com.narlock.checklistapi.controller;

import com.narlock.checklistapi.model.ChecklistItem;
import com.narlock.checklistapi.model.ChecklistItemRequest;
import com.narlock.checklistapi.service.ChecklistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/checklist-item")
@RequiredArgsConstructor
public class ItemController {

  private final ChecklistService checklistService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ChecklistItem createChecklistItem(@RequestBody ChecklistItemRequest checklistItemRequest) {
    return checklistService.createChecklistItem(checklistItemRequest);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ChecklistItem getChecklistItem(
      @PathVariable("id") Integer id,
      @RequestParam("checklistName") String checklistName,
      @RequestParam("profileId") Integer profileId) {
    return checklistService.getChecklistItem(id, checklistName, profileId);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ChecklistItem updateChecklistItem(
      @PathVariable("id") Integer id, @RequestBody ChecklistItemRequest checklistItemRequest) {
    return checklistService.updateChecklistItem(id, checklistItemRequest);
  }

  @PatchMapping("/{id}/streak")
  @ResponseStatus(HttpStatus.OK)
  public ChecklistItem checklistItemStreak(
      @PathVariable("id") Integer id,
      @RequestParam("checklistName") String checklistName,
      @RequestParam("profileId") Integer profileId) {
    return checklistService.updateStreak(id, checklistName, profileId);
  }

  @PatchMapping("/{id}/complete")
  @ResponseStatus(HttpStatus.OK)
  public ChecklistItem checklistItemComplete(
      @PathVariable("id") Integer id,
      @RequestParam("checklistName") String checklistName,
      @RequestParam("profileId") Integer profileId) {
    return checklistService.completeChecklistItem(id, checklistName, profileId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteChecklistItem(@PathVariable("id") Integer id) {
    checklistService.deleteChecklistItem(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ChecklistItem> getChecklistItemsByChecklistName(
      @RequestParam("checklistName") String checklistName,
      @RequestParam("profileId") Integer profileId) {
    return checklistService.getChecklistItemsByChecklistName(checklistName, profileId);
  }
}
