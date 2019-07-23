package com.bridgelabz.Bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

	@Before("execution(* com.bridgelabz.Bean.Person.display())")
	public void beforeAdvice(JoinPoint beforepoint) {
		System.out.println("Log Before Method is running Before " + beforepoint.getSignature().getName() + " Method");
		System.out.println("Method Name :" + beforepoint.getSignature().getName());
	}

	@After("execution(* com.bridgelabz.Bean.Person.show())")
	public void afterAdvice(JoinPoint afterpoint) {
		System.out.println("\nLog After Method is running After " + afterpoint.getSignature().getName() + " Method");
		System.out.println("Method Name :" + afterpoint.getSignature().getName());
	}

}
