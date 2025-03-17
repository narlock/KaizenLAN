package com.narlock.watertrackapi.controller;

import com.narlock.watertrackapi.model.WaterEntry;
import com.narlock.watertrackapi.model.WaterEntryId;
import com.narlock.watertrackapi.service.WaterEntryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/water")
@RequiredArgsConstructor
public class WaterEntryController {

  private final WaterEntryService waterEntryService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WaterEntry createWaterEntry(@RequestBody WaterEntry waterEntry) {
    return waterEntryService.createWaterEntry(waterEntry);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public WaterEntry addWaterToEntry(@RequestBody WaterEntry waterEntry) {
    return waterEntryService.addWaterToEntry(waterEntry);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteWaterEntries(@RequestParam Integer profileId) {
    waterEntryService.deleteByProfileId(profileId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<WaterEntry> getWaterEntries(
      @RequestParam Integer profileId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate entryDate) {
    if (entryDate != null) {
      return List.of(
          waterEntryService.getWaterEntryById(
              WaterEntryId.builder().profileId(profileId).entryDate(entryDate).build()));
    }

    return waterEntryService.getWaterEntriesByProfileId(profileId);
  }

  @GetMapping("/range")
  @ResponseStatus(HttpStatus.OK)
  public List<WaterEntry> getWaterEntriesRange(
      @RequestParam Integer profileId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    return waterEntryService.getWaterEntriesByRange(profileId, startDate, endDate);
  }
}
