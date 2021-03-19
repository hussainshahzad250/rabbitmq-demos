package com.rabbitmq.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	private int code;
	private String message;
	private HttpStatus status;
	private Object responseObject;

	public Response(String message, HttpStatus status) {
		this.code = status.value();
		this.message = message;
		this.status = status;
	}

	public Response(String message, Object responseObject, HttpStatus status) {
		this.code = status.value();
		this.message = message;
		this.status = status;
		this.responseObject = responseObject;
	}

}
