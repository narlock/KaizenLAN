package com.narlock.simpletimeblock.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CalendarEvent")
public class CalendarEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String note;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "end_time")
  private LocalTime endTime;

  private LocalDate date;
  private String meta;

  public CalendarEvent(
      String name,
      String note,
      LocalTime startTime,
      LocalTime endTime,
      LocalDate date,
      String meta) {
    this.name = name;
    this.note = note;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.meta = meta;
  }
}
