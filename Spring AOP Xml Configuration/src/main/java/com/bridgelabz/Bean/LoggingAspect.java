package com.bridgelabz.Bean;

import org.aspectj.lang.JoinPoint;

public class LoggingAspect {

	public void beforeAdvice(JoinPoint beforepoint) {
		System.out.println("Log Before Method is running Before " + beforepoint.getSignature().getName() + " Method");
		System.out.println("Method Name :" + beforepoint.getSignature().getName());
	}

	public void afterAdvice(JoinPoint afterpoint) {
		System.out.println("\nLog After Method is running After " + afterpoint.getSignature().getName() + " Method");
		System.out.println("Method Name :" + afterpoint.getSignature().getName());
	}
	
	

}
