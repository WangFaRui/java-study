package com.itwray.study.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 日志 AOP
 *
 * @author Wray
 * @since 2023/9/19
 */
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.itwray.study.spring.aop.UserService.*(..))")
    public void printLog() {
        System.out.println("[Before] print log");
    }
}
