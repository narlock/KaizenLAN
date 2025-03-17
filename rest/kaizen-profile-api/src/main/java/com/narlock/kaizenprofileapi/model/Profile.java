package com.narlock.kaizenprofileapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Profile")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "image_url")
  private String imageUrl;

  private Integer xp;

  @Column(name = "num_rows")
  private Integer numRows;

  private String pin;
}
