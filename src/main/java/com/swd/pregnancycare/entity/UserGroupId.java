package com.swd.pregnancycare.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserGroupId implements Serializable {
  private int user;
  private int group;

  public UserGroupId() {}

  public UserGroupId(int user, int group) {
    this.user = user;
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserGroupId that = (UserGroupId) o;
    return user == that.user && group == that.group;
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, group);
  }
}
