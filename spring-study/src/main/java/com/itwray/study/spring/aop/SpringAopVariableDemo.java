package com.itwray.study.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * spring aop 成员变量示例
 *
 * @author Wray
 * @since 2023/9/19
 */
@ComponentScan
@EnableAspectJAutoProxy
public class SpringAopVariableDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringAopVariableDemo.class);
        applicationContext.refresh();

        UserService userService = applicationContext.getBean(UserService.class);

        ZoneId serviceZoneId = userService.getZoneId();
        System.out.println("serviceZoneId: " + ZonedDateTime.now(serviceZoneId));

        ZoneId finalZoneId = userService.getFinalZoneId();
        if (finalZoneId != null) {
            System.out.println("finalZoneId: " + ZonedDateTime.now(finalZoneId));
        }

        ZoneId zoneId = userService.zoneId;
        if (zoneId != null) {
            System.out.println("zoneId: " + ZonedDateTime.now(zoneId));
        }

    }
}
