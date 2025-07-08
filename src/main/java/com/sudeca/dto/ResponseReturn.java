package com.sudeca.dto;

import lombok.Getter;
import lombok.Setter;

public class ResponseReturn {

  @Getter
  private long id;
   
	  @Setter
	  private String dateCreate;
	   
	  @Setter
	  private String dateModified;
	 
	  @Setter
	  private String dateLastLogin;

	  @Setter
	  private Boolean result;

	  @Setter
	  private String mensaje;

	 @Setter
	 private String status;

	  /*private String token;
	 
  private Boolean active;*/
  
  

  public ResponseReturn() {

  }

  public ResponseReturn(long id, String dateCreate, String dateModified, Boolean result, String mensaje) {
    this.id = id;
    this.dateCreate = dateCreate;
    this.dateModified = dateModified;
	this.result = result;
	this.mensaje = mensaje;
  }

	public String getDateCreate() {
		return dateCreate;
	}

	public String getDateModified() {
		return dateModified;
	}

	public String getDateLastLogin() {
		return dateLastLogin;
	}

	public Boolean getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "UserReturn [id=" + id + ", dateCreate=" + dateCreate + ", dateModified=" + dateModified
				 + "]";
	}

}
