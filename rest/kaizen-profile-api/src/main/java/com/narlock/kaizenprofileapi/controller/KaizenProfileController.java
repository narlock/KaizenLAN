package com.narlock.kaizenprofileapi.controller;

import com.narlock.kaizenprofileapi.model.*;
import com.narlock.kaizenprofileapi.model.error.BadRequestException;
import com.narlock.kaizenprofileapi.service.KaizenProfileService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class KaizenProfileController {

  private final KaizenProfileService kaizenProfileService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProfileResponse getProfileById(@PathVariable("id") Integer id) {
    return kaizenProfileService.getKaizenProfileById(id);
  }

  /**
   * Creates a Profile and Health entry for a Kaizen Profile
   *
   * @param request
   * @return
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest request) {
    if (request.getProfileId() != null) {
      throw new BadRequestException("Profile ID must be null when creating a new profile");
    }
    return kaizenProfileService.createProfile(request);
  }

  @PostMapping("/xp")
  @ResponseStatus(HttpStatus.OK)
  public ProfileResponse addXpToProfile(@RequestParam Integer id, @RequestParam Integer xp) {
    return kaizenProfileService.addXpToProfile(id, xp);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public ProfileResponse updateFullProfile(@RequestBody FullProfileRequest request) {
    return kaizenProfileService.updateFullProfile(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfileById(@PathVariable("id") Integer id) {
    kaizenProfileService.deleteProfileById(id);
  }

  @PostMapping("/{id}/rowInfo")
  @ResponseStatus(HttpStatus.OK)
  public List<RowInfo> saveRowInfo(
      @PathVariable("id") Integer id, @Valid @RequestBody RowInfoRequest request) {
    return kaizenProfileService.saveRowInfo(id, request);
  }

  @GetMapping("/{id}/rowInfo")
  @ResponseStatus(HttpStatus.OK)
  public List<RowInfo> getRowInfo(@PathVariable("id") Integer id) {
    return kaizenProfileService.getRowInfoById(id);
  }

  @PutMapping("/{id}/rowInfo")
  @ResponseStatus(HttpStatus.OK)
  public List<RowInfo> updateRowInfo(
      @PathVariable("id") Integer id, @Valid @RequestBody RowInfoRequest request) {
    return kaizenProfileService.updateRowInfo(id, request);
  }

  @DeleteMapping("/{id}/rowInfo/{index}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRowInfo(
      @PathVariable("id") Integer id, @PathVariable("index") Integer rowIndex) {
    kaizenProfileService.deleteRowInfo(id, rowIndex);
  }
}
