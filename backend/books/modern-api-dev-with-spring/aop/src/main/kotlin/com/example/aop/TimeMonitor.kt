package com.example.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimeMonitor

// @Target(ElementType.METHOD)
// @Retention(RetentionPolicy.RUNTIME)
// public @interface TimeMonitor {
// }