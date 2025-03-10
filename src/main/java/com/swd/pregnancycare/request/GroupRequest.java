package com.swd.pregnancycare.request;

import lombok.Data;

@Data
public class GroupRequest {
  private String name;
  private String description;
  private String owner_email;
}
