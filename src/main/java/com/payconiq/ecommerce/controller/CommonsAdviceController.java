
package com.payconiq.ecommerce.controller;

import static com.payconiq.ecommerce.util.EcommerceConstants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.payconiq.ecommerce.beans.ErrorDetails;
import com.payconiq.ecommerce.exception.EcommerceException;
import com.payconiq.ecommerce.util.EcommerceLogConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ControllerAdvice(annotations = RestController.class)
public class CommonsAdviceController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceAccessException.class)
	@ResponseBody
	public ResponseEntity<?> handleException(ResourceAccessException e, WebRequest request) {
		log.error(EcommerceLogConstants.ABORT_REQ, e.getMessage(), request, e);
		final ErrorDetails errorDetails = new ErrorDetails(MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME), null,
				e.getMessage().split(";")[0]);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(EcommerceException.class)
	@ResponseBody
	public ResponseEntity<?> handleException(EcommerceException e, WebRequest request) {

		log.error(EcommerceLogConstants.ABORT_REQ, e.getMessage(), request, e);

		final ErrorDetails errorDetails = new ErrorDetails(MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME), null,
				e.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ IOException.class, Exception.class })
	@ResponseBody
	public ResponseEntity<?> handleGeneralException(Exception e, WebRequest request) {
		log.error(EcommerceLogConstants.ABORT_REQ, e.getMessage(), request, e);
		final ErrorDetails errorDetails = new ErrorDetails(MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME), null,
				e.getMessage().split(";")[0]);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(EcommerceLogConstants.ABORT_REQ, e.getMessage(), request, e);

		final List<String> errors = new ArrayList<>();
		for (final FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		final ErrorDetails errorDetails = new ErrorDetails(MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME), null,
				String.join(", ", errors));
		return handleExceptionInternal(e, errorDetails, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(EcommerceLogConstants.ABORT_REQ, e.getMessage(), request, e);
		final ErrorDetails errorDetails = new ErrorDetails(MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME), null,
				e.getMessage().split(";")[0]);
		return handleExceptionInternal(e, errorDetails, headers, status, request);
	}
}