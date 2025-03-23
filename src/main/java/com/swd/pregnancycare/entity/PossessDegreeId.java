package com.swd.pregnancycare.entity;

import java.io.Serializable;
import java.util.Objects;

public class PossessDegreeId implements Serializable {
  private int user;
  private int certificate;

  public PossessDegreeId(int user, int certificate) {
    this.user = user;
    this.certificate = certificate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PossessDegreeId that = (PossessDegreeId) o;
    return user == that.user && certificate == that.certificate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, certificate);
  }
}
