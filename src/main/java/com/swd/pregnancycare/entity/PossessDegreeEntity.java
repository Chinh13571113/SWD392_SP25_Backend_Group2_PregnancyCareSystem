package com.swd.pregnancycare.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "possess_degree")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessDegreeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "dateBegin")
  private LocalDateTime dateBegin;
  @Column(name = "dateEnd")
  private LocalDateTime dateEnd;


  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;


  @ManyToOne
  @JoinColumn(name = "id_certificate")
  private CertificateEntity certificate;
}
