package com.pr0maxx.logging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.env.Environment;


@Aspect
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    private String getLevel() {
        return env.getProperty("logging.aspect.level", "INFO").toUpperCase();
    }

    private void logAtLevel(String message, Object... args) {
        switch (getLevel()) {
            case "DEBUG" -> log.debug(message, args);
            case "INFO" -> log.info(message, args);
            case "WARN" -> log.warn(message, args);
            case "ERROR" -> log.error(message, args);
            default -> log.info(message, args);
        }
    }

    @Before("@annotation(com.pr0maxx.logging.Loggable)")
    public void logBefore(JoinPoint joinPoint) {
        logAtLevel("[Before] Вызов метода: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("@annotation(com.pr0maxx.logging.Loggable)")
    public void logAfterReturning(JoinPoint joinPoint) {
        logAtLevel("[AfterReturning] Метод {} успешно завершен.", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(com.pr0maxx.logging.Loggable)", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAtLevel("[AfterThrowing] Ошибка в методе {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @Around("@annotation(com.pr0maxx.logging.Loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logAtLevel("[Around] Старт выполнения: {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        logAtLevel("[Around] Завершено выполнение: {} за {} мс", joinPoint.getSignature(), duration);

        return result;
    }
}