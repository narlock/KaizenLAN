package com.narlock.simplejournalapi.controller;

import com.narlock.simplejournalapi.model.JournalEntry;
import com.narlock.simplejournalapi.service.SimpleJournalService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class SimpleJournalController {

  private final SimpleJournalService simpleJournalService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry) {
    return simpleJournalService.saveNewJournalEntry(journalEntry);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public JournalEntry getJournalEntry(@RequestParam("date") LocalDate date, @RequestParam("profileId") Integer profileId) {
    return simpleJournalService.getJournalEntryByDateProfile(profileId, date);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public JournalEntry updateJournalEntry(@RequestBody JournalEntry journalEntry) {
    return simpleJournalService.updateJournalEntry(journalEntry);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteJournalEntry(@RequestParam("date") LocalDate date, @RequestParam("profileId") Integer profileId) {
    simpleJournalService.deleteJournalEntry(date, profileId);
  }
}
