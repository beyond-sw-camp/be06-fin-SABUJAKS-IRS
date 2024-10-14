package com.sabujaks.irs.global.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Pointcut("@annotation(com.sabujaks.irs.global.common.annotation.ExeTimer)")
    private void timePointCut() {
    }

    @Around("timePointCut()")
    public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            logger.info("{} 실행 시간: {}s", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis()/1000.0);
        }
    }
}
