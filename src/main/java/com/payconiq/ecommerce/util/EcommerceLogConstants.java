
package com.payconiq.ecommerce.util;

public class EcommerceLogConstants {


	
	/*********************** following will be used for all LOG levels start *********************/
	// Request-Response
	public static final String IN_REQ = "Start inbound service: [{}] [{}]. Inbound Request: [{}]";
	public static final String IN_RES = "End inbound service: [{}] [{}]. Inbound Response: [{}]";
	public static final String OUT_REQ = "Start outbound service: [{}] [{}]. Outbound Request: [{}]";
	public static final String OUT_RES = "End outbound service: [{}] [{}]. Outbound Response: [{}]";

	// Exceptions
	public static final String EX = "Exception occurred in functionality: [{}], possibly by: [{}]. Exception Message: [{}], Debug Info: [{}]";
	public static final String ABORT_REQ = "Stopping Request flow. Exception Message: [{}], Debug Info: [{}]";

	

}