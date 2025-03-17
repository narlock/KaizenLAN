package com.narlock.simplenotepadapi.controller;

import com.narlock.simplenotepadapi.model.FileInfo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing files in a simple notepad-like application. This controller provides REST
 * endpoints for listing, reading, writing, and deleting text files.
 */
@RestController
public class SimpleNotepadController {
  private static final String BASE_DIR = "notepad_files";

  /**
   * Creates a new instance of {@link SimpleNotepadController} and ensures that the base directory
   * for storing files exists.
   */
  public SimpleNotepadController() {
    try {
      Files.createDirectories(Paths.get(BASE_DIR));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Lists all files in the base directory, sorted by the specified field and order.
   *
   * @param sortBy The field to sort the file listings by (default is "name").
   * @param order The order of the sorting, either "asc" for ascending or "desc" for descending
   *     (default is "desc").
   * @return A list of {@link FileInfo} representing the files.
   * @throws IOException If an I/O error occurs accessing the directory.
   */
  @GetMapping
  public List<FileInfo> listFiles(
      @RequestParam(required = false, defaultValue = "date") String sortBy,
      @RequestParam(required = false, defaultValue = "desc") String order)
      throws IOException {
    List<Path> files = new ArrayList<>(Files.list(Paths.get(BASE_DIR)).toList());
    Comparator<Path> comparator =
        switch (sortBy) {
          case "name" -> Comparator.comparing(Path::getFileName);
          case "date" -> Comparator.comparingLong(
              f -> {
                try {
                  return f.toFile().lastModified();
                } catch (Exception e) {
                  return 0L;
                }
              });
          default -> throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        };

    if (order.equals("desc")) {
      comparator = comparator.reversed();
    }

    files.sort(comparator);
    return files.stream()
        .map(file -> new FileInfo(file.getFileName().toString(), file.toFile().lastModified()))
        .toList();
  }

  /**
   * Reads the content of a file specified by filename.
   *
   * @param filename The name of the file to read.
   * @return The content of the file as a String.
   * @throws IOException If an I/O error occurs when reading the file.
   * @throws FileNotFoundException If the file does not exist.
   */
  @GetMapping("/{filename}")
  public String readFile(@PathVariable String filename) throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    if (!Files.exists(filePath)) {
      throw new FileNotFoundException("File not found: " + filename);
    }
    return Files.readString(filePath);
  }

  /**
   * Writes content to a file specified by filename. If the file does not exist, it is created.
   *
   * @param filename The name of the file to write to.
   * @param content The content to write into the file.
   * @return A success message indicating the file has been written.
   * @throws IOException If an I/O error occurs when writing to the file.
   */
  @PostMapping("/{filename}")
  public String writeFile(@PathVariable String filename, @RequestBody String content)
      throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    Files.writeString(filePath, content);
    return "File written successfully: " + filename;
  }

  /**
   * Deletes a file specified by filename.
   *
   * @param filename The name of the file to delete.
   * @return A message indicating whether the file was successfully deleted or not found.
   * @throws IOException If an I/O error occurs when attempting to delete the file.
   */
  @DeleteMapping("/{filename}")
  public String deleteFile(@PathVariable String filename) throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    if (Files.deleteIfExists(filePath)) {
      return "File deleted successfully: " + filename;
    } else {
      return "File not found: " + filename;
    }
  }
}
