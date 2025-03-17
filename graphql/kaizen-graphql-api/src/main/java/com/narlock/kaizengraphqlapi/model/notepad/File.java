package com.narlock.kaizengraphqlapi.model.notepad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
  private String name;
  private String lastModified;
}
