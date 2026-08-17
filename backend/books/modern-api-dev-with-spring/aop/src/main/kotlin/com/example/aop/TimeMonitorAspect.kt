package com.example.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeMonitorAspect {

    @Around("@annotation(com.example.aop.TimeMonitor)")
    fun logTime(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - startTime

        println("${joinPoint.signature} takes: ${executionTime}ms")

        return proceed
    }
}

// @Aspect
// @Component
// public class TimeMonitorAspect {

// @Around("@annotation(com.example.aop.TimeMonitor)")
// public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
// long startTime = System.currentTimeMillis();
// Object proceed = joinPoint.proceed();
// long executionTime = System.currentTimeMillis() - startTime;

// System.out.println(joinPoint.getSignature() + " takes: " + executionTime +
// "ms");

// return proceed;
// }
// }