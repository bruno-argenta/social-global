package com.myklovr.rest.dto;

public class GenericDTO {

	private int code;
	private String message;	
	private Object object;
	
	public GenericDTO(String message, int code, Object object) {
		this.message = message;
		this.code = code;
		this.object = object;
	}
	
	public GenericDTO(int code) {
		this.message = "";
		this.code = code;
		this.object = "";
	}
	
	public GenericDTO(String message, int code) {
		this.message = message;
		this.code = code;
		this.object = "";
	}	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
