package com.itwray.study.springboot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于{@link SpringBootApplication}和{@link SpringApplication}的示例
 *
 * @author Wray
 * @since 2023/10/8
 */
@SpringBootApplication
public class SpringBootApplicationDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationDemo.class, args);
    }
}
