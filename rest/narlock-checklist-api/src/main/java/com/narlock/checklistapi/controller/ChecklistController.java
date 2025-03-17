package com.narlock.checklistapi.controller;

import com.narlock.checklistapi.model.Checklist;
import com.narlock.checklistapi.service.ChecklistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/checklist")
@RequiredArgsConstructor
public class ChecklistController {

  private final ChecklistService checklistService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public Checklist createChecklist(@RequestBody Checklist checklist) {
    return checklistService.createChecklist(checklist);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Checklist> getChecklistsForProfile(
      @RequestParam(name = "profileId") Integer profileId) {
    return checklistService.getChecklistsForProfile(profileId);
  }

  @GetMapping("/{profileId}")
  @ResponseStatus(HttpStatus.OK)
  public Checklist getChecklist(
      @RequestParam(name = "name") String name,
      @PathVariable(name = "profileId") Integer profileId) {
    return checklistService.getChecklist(name, profileId);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public Checklist updateChecklistRepeat(
      @RequestParam(name = "name") String name,
      @RequestParam(name = "profileId") Integer profileId,
      @RequestParam(name = "repeatEvery") String repeatEvery) {
    return checklistService.updateChecklistRepeat(name, profileId, repeatEvery);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteChecklist(
      @RequestParam(name = "name") String name,
      @RequestParam(name = "profileId") Integer profileId) {
    checklistService.deleteChecklist(name, profileId);
  }
}
