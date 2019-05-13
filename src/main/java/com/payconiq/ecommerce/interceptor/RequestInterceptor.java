
package com.payconiq.ecommerce.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import static com.payconiq.ecommerce.util.EcommerceConstants.*;

@Component

public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object)
			throws Exception {
		super.preHandle(request, response, object);
		String uuid = StringUtils.EMPTY;
		if (StringUtils.isBlank(request.getHeader(UUID_HEADER))) {
			uuid = UUID.randomUUID().toString();
		} else {
			uuid = request.getHeader(UUID_HEADER);
		}
		MDC.put(UUID_HEADER, uuid);
		MDC.put(SYSTEM_NAME, "payconiq-nl-ms-ecommerce");
		MDC.put(INTERFACE_NAME, request.getRequestURI());
		return true;
	}

}
