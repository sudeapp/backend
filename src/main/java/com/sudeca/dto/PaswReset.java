package com.sudeca.dto;

import lombok.Data;

@Data
public class PaswReset {
  private String email_code;
  private String password;

  public PaswReset() {

  }
}
