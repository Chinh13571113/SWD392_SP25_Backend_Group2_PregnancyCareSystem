package com.swd.pregnancycare.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "possess_degree")
@IdClass(PossessDegreeId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessDegreeEntity {
  @Column(name = "dateBegin")
  private LocalDateTime dateBegin;
  @Column(name = "dateEnd")
  private LocalDateTime dateEnd;


  @Id
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;


  @Id
  @ManyToOne
  @JoinColumn(name = "id_certificate")
  private CertificateEntity certificate;
}
