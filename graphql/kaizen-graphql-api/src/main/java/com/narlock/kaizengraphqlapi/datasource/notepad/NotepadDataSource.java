package com.narlock.kaizengraphqlapi.datasource.notepad;

import com.narlock.kaizengraphqlapi.model.notepad.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NotepadDataSource {
  private final WebClient notepadWebClient;

  public String getFileContent(String name) {
    return notepadWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/{name}").build(name))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public List<File> getFiles() {
    return notepadWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/").build())
        .retrieve()
        .bodyToFlux(File.class)
        .collectList()
        .block();
  }

  public String getCSVContent(String name) {
    return notepadWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/csv/{name}").build(name))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public List<File> getCSVs() {
    return notepadWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/csv").build())
        .retrieve()
        .bodyToFlux(File.class)
        .collectList()
        .block();
  }

  public Boolean writeFile(String name, String content) {
    try {
      notepadWebClient
          .post()
          .uri(uriBuilder -> uriBuilder.path("/{name}").build(name))
          .bodyValue(content)
          .retrieve()
          .bodyToMono(String.class)
          .block();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Boolean writeCSV(String name, String content) {
    try {
      notepadWebClient
          .post()
          .uri(uriBuilder -> uriBuilder.path("/csv/{name}").build(name))
          .bodyValue(content)
          .retrieve()
          .bodyToMono(String.class)
          .block();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Boolean deleteFile(String name) {
    try {
      String response =
          notepadWebClient
              .delete()
              .uri(uriBuilder -> uriBuilder.path("/{name}").build(name))
              .retrieve()
              .bodyToMono(String.class)
              .block();
      if (response.contains("not found")) {
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Boolean deleteCSV(String name) {
    try {
      String response =
          notepadWebClient
              .delete()
              .uri(uriBuilder -> uriBuilder.path("/csv/{name}").build(name))
              .retrieve()
              .bodyToMono(String.class)
              .block();
      if (response.contains("not found")) {
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
