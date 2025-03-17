package com.narlock.simplenotepadapi.controller;

import com.narlock.simplenotepadapi.model.FileInfo;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing CSV files in a simple notepad-like application. This controller provides
 * REST endpoints for listing, reading, writing, and deleting CSV files.
 */
@RestController
@RequestMapping("/csv")
public class CSVController {
  private static final String BASE_DIR = "csv_files";

  /**
   * Creates a new instance of {@link CSVController} and ensures that the base directory for storing
   * CSV files exists.
   */
  public CSVController() {
    try {
      Files.createDirectories(Paths.get(BASE_DIR));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Lists all CSV files in the base directory, sorted by the specified field and order.
   *
   * @param sortBy The field to sort the file listings by (default is "name").
   * @param order The order of the sorting, either "asc" for ascending or "desc" for descending
   *     (default is "desc").
   * @return A list of {@link FileInfo} representing the CSV files.
   * @throws IOException If an I/O error occurs accessing the directory.
   */
  @GetMapping
  public List<FileInfo> listCSVFiles(
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
   * Reads the content of a CSV file specified by filename.
   *
   * @param filename The name of the CSV file to read.
   * @return The content of the CSV file as a String.
   * @throws IOException If an I/O error occurs when reading the file.
   * @throws FileNotFoundException If the file does not exist.
   */
  @GetMapping("/{filename}")
  public String readCSVFile(@PathVariable String filename) throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    if (!Files.exists(filePath)) {
      throw new FileNotFoundException("CSV file not found: " + filename);
    }
    return Files.readString(filePath);
  }

  /**
   * Writes content to a CSV file specified by filename. If the file does not exist, it is created.
   *
   * @param filename The name of the CSV file to write to.
   * @param content The content to write into the CSV file.
   * @return A success message indicating the CSV file has been written.
   * @throws IOException If an I/O error occurs when writing to the file.
   */
  @PostMapping("/{filename}")
  public String writeCSVFile(@PathVariable String filename, @RequestBody String content)
      throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    Files.writeString(filePath, content);
    return "CSV file written successfully: " + filename;
  }

  /**
   * Deletes a CSV file specified by filename.
   *
   * @param filename The name of the CSV file to delete.
   * @return A message indicating whether the CSV file was successfully deleted or not found.
   * @throws IOException If an I/O error occurs when attempting to delete the file.
   */
  @DeleteMapping("/{filename}")
  public String deleteCSVFile(@PathVariable String filename) throws IOException {
    Path filePath = Paths.get(BASE_DIR, filename);
    if (Files.deleteIfExists(filePath)) {
      return "CSV file deleted successfully: " + filename;
    } else {
      return "CSV file not found: " + filename;
    }
  }
}
