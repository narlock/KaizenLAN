package com.narlock.weighttrackapi.controller;

import com.narlock.weighttrackapi.model.WeightEntry;
import com.narlock.weighttrackapi.service.WeightTrackService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/weight")
@RequiredArgsConstructor
public class WeightTrackController {

  private final WeightTrackService weightTrackService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WeightEntry createWeightEntry(@RequestBody WeightEntry weightEntry) {
    return weightTrackService.createWeightEntry(weightEntry);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public WeightEntry updateWeightEntry(@RequestBody WeightEntry weightEntry) {
    return weightTrackService.updateWeightEntry(weightEntry);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@RequestParam Integer profileId) {
    weightTrackService.deleteWeightEntriesByProfileId(profileId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<WeightEntry> getWeightEntries(
      @RequestParam Integer profileId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate entryDate) {
    if (entryDate != null) {
      return weightTrackService.getWeightEntryById(profileId, entryDate);
    }

    return weightTrackService.getWeightEntries(profileId);
  }

  @GetMapping("/range")
  @ResponseStatus(HttpStatus.OK)
  public List<WeightEntry> getWeightEntriesRange(
      @RequestParam Integer profileId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    return weightTrackService.getWeightEntriesRange(profileId, startDate, endDate);
  }
}
