package sk.baric.animalshop.rest.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlerConfiguration {

	@ExceptionHandler
	public ResponseEntity<String> handle(Exception ex, HttpServletResponse response) throws IOException {
		return response(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handle(ResponseStatusException ex, HttpServletResponse response) throws IOException {
		return response(ex.getMessage(), ex.getStatus().value());
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handle(HttpClientErrorException ex, HttpServletResponse response) throws IOException {
		return response(ex.getMessage(), ex.getRawStatusCode());
	}
	
	private ResponseEntity<String> response(String msg, int httpCode) {
        return new ResponseEntity<>(msg, HttpStatus.valueOf(httpCode));
	}
}
