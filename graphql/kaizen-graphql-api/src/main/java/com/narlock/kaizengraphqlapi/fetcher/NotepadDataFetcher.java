package com.narlock.kaizengraphqlapi.fetcher;

import com.narlock.kaizengraphqlapi.datasource.notepad.NotepadDataSource;
import com.narlock.kaizengraphqlapi.model.notepad.File;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class NotepadDataFetcher {
  private final NotepadDataSource notepadDataSource;

  @DgsQuery
  public String fileContent(@InputArgument String name) {
    return notepadDataSource.getFileContent(name);
  }

  @DgsQuery
  public List<File> getFiles() {
    return notepadDataSource.getFiles();
  }

  @DgsQuery
  public String csvContent(@InputArgument String name) {
    return notepadDataSource.getCSVContent(name);
  }

  @DgsQuery
  public List<File> getCSVs() {
    return notepadDataSource.getCSVs();
  }

  @DgsMutation
  public Boolean writeFile(@InputArgument String name, @InputArgument String content) {
    return notepadDataSource.writeFile(name, content);
  }

  @DgsMutation
  public Boolean writeCSV(@InputArgument String name, @InputArgument String content) {
    return notepadDataSource.writeCSV(name, content);
  }

  @DgsMutation
  public Boolean deleteFile(@InputArgument String name) {
    return notepadDataSource.deleteFile(name);
  }

  @DgsMutation
  public Boolean deleteCSV(@InputArgument String name) {
    return notepadDataSource.deleteCSV(name);
  }
}
