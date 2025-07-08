package com.sudeca.dto;

public class Message {
	
	String mensaje;

	Boolean result;
	

	public Message() {
	}
	
	public Message(String msg, Boolean result) {
		this.mensaje = msg;
		this.result = result;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Message [mensaje=" + mensaje + ", result=" + result +"]";
	}

}
