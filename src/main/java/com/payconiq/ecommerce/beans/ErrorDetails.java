
package com.payconiq.ecommerce.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.payconiq.ecommerce.util.EcommerceConstants.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonIgnoreProperties({ "statusCode" })
@Data
@ToString
public class ErrorDetails {
	private String code;
	private String description;
	private Integer priority;
	private String severity;
	private String system;
	private String interfaceName;
	private String statusCode;

	public ErrorDetails(String system, String interfaceName, String statusCode, String description) {
		super();
		this.description = description;
		this.system = system;
		this.interfaceName = interfaceName;
		this.statusCode = statusCode;
		this.priority = DEFAULT_PRIORITY;
		this.severity = DEFAULT_SEVERITY;
	}

}