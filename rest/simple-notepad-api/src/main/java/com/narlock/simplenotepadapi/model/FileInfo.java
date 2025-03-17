package com.narlock.simplenotepadapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfo {
  private String name;
  private String lastModified;

  public FileInfo(String name, long lastModifiedTimestamp) {
    this.name = name;
    this.lastModified = formatDate(lastModifiedTimestamp);
  }

  public String getName() {
    return name;
  }

  public String getLastModified() {
    return lastModified;
  }

  private String formatDate(long timestamp) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(timestamp));
  }
}
