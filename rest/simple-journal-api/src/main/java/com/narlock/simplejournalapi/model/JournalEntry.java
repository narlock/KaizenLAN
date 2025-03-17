package com.narlock.simplejournalapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "JournalEntry")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private LocalDate date;

    private Integer profileId;
    private String box1;
    private String box2;
    private String box3;
    private String box4;
    private Integer mood;
}
