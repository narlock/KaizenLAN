package com.narlock.checklistapi.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private String status;
  private String message;
  private String detail;
  private String timestamp;
  private final String documentation = "http://github.com/narlock/narlock-checklist-api";
}
