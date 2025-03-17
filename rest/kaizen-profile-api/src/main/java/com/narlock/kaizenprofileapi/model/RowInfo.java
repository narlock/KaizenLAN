package com.narlock.kaizenprofileapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "RowInfo")
@IdClass(RowInfoId.class)
public class RowInfo {
  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  @Id
  @Column(name = "row_index")
  private Integer rowIndex;

  @Column(name = "widgets_list")
  private String widgets;
}
