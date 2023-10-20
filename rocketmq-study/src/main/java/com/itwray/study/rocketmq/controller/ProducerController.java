package com.itwray.study.rocketmq.controller;

import com.itwray.study.rocketmq.producer.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 生产消息的控制器
 *
 * @author Wray
 * @since 2023/10/20
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Resource
    private ProducerService producerService;

    @GetMapping("/produceNormalMessage")
    public void produceNormalMessage(@RequestParam String msg) {
        producerService.send(msg);
    }
}
