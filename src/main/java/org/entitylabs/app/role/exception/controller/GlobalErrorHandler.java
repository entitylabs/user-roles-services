package org.entitylabs.app.role.exception.controller;

import org.entitylabs.app.role.exception.RoleAlreadyExistsException;
import org.entitylabs.app.role.exception.RoleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
			HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

		var locale = exchange.getLocaleContext().getLocale();
		var errors = ex.resolveErrorMessages(getMessageSource(), locale);
		ex.getBody().setProperty("errors", errors.values());

		return super.handleExceptionInternal(ex, null, headers, status, exchange);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public Mono<ResponseEntity<Object>> handleMyException(RoleNotFoundException ex, ServerWebExchange exchange) {

		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.NOT_FOUND, exchange);

	}

	@ExceptionHandler(RoleAlreadyExistsException.class)
	public Mono<ResponseEntity<Object>> handleMyException(RoleAlreadyExistsException ex, ServerWebExchange exchange) {

		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.CONFLICT, exchange);

	}

}