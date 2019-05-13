
package com.payconiq.ecommerce.exception;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.payconiq.ecommerce.beans.ErrorDetails;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;



@Builder
@Data
@ToString
@JsonIgnoreProperties({ "stackTrace", "cause", "suppressed", "localizedMessage", "message" })
@JsonPropertyOrder({ "code", "description", "system", "interface", "severity", "priority" })
final public class EcommerceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	Set<ErrorDetails> errors = new HashSet<ErrorDetails>();

	public EcommerceException(Set<ErrorDetails> errors, Throwable cause) {
		this.cause = cause;
		if (!CollectionUtils.isEmpty(errors)) {
			this.errors = errors;
		}
	}
	public EcommerceException(ErrorDetails error, Throwable cause) {
		this.cause = cause;
		if (error != null) {
			this.errors.add(error);
		}
	}
	public EcommerceException(Set<ErrorDetails> errors, String message) {
		super(message);
		if (!CollectionUtils.isEmpty(errors)) {
			this.errors= errors;
		}
	}
	public EcommerceException(ErrorDetails error, String message) {
		super(message);
		if (error != null) {
			this.errors.add(error);
		}
	}
	
	
	@JsonIgnore
	private Throwable cause;
}