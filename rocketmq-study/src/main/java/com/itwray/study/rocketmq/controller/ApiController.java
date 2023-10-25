package com.itwray.study.rocketmq.controller;

import com.itwray.study.rocketmq.consumer.ConsumerService;
import com.itwray.study.rocketmq.producer.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对外接口服务
 *
 * @author Wray
 * @since 2023/10/20
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private ProducerService producerService;

    @Resource
    private ConsumerService consumerService;

    @GetMapping("/produce")
    public void produce(@RequestParam String msg, @RequestParam(required = false) String topic) {
        producerService.send(topic, msg);
    }

    @GetMapping("/consume")
    public List<String> consume() {
        return consumerService.receiveMessage();
    }

    @GetMapping("/extConsume")
    public List<String> extConsume() {
        return consumerService.extConsume();
    }

    @GetMapping("/extConsume2")
    public List<String> extConsume2() {
        return consumerService.extConsume2();
    }
}
