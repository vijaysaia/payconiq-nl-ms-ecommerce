
package com.payconiq.ecommerce.commons;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableAspectJAutoProxy
@Aspect
@Slf4j
public class PerfMetricsConfiguration {

	private static final String LOG_MESSAGE_FORMAT = "{}.{} execution time: {}ms";
	
    @Pointcut("execution(@PerfLog * com.payconiq..*.*(..))")
    public void isAnnotated() {}
    
    @Around("isAnnotated()")
    public Object logPerformanceMetrics(ProceedingJoinPoint joinPoint ) throws Throwable {
    	StopWatch stopWatch = new StopWatch();
    	stopWatch.start();
    	Object returnValue = null;
    	try {
    		returnValue = joinPoint.proceed();
    	} finally {
	    	stopWatch.stop();
	    	logExecutionTime(joinPoint, stopWatch); 
	    }
    	return returnValue; 
    }
    
    private void logExecutionTime(ProceedingJoinPoint joinPoint, StopWatch stopWatch) {
    	log.info(LOG_MESSAGE_FORMAT, joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
    }
}
