package com.itwray.study.rocketmq.controller;

import com.itwray.study.rocketmq.consumer.service.ConsumerService;
import com.itwray.study.rocketmq.producer.ProducerService;
import com.itwray.study.rocketmq.producer.UserVo;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 批量发送消息
     * @param msg 消息内容
     * @param size 消息数量
     */
    @GetMapping("/batchProduce")
    public void batchProduce(@RequestParam String msg, @RequestParam Integer size) {
        producerService.send(msg, size);
    }

    /**
     * 生产{@link UserVo}对象的消息
     */
    @PostMapping("/produceUser")
    public void produceUser(@RequestBody UserVo userVo) {
        producerService.send(null, userVo);
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
