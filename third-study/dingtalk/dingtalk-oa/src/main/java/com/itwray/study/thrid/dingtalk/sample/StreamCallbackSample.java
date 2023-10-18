package com.itwray.study.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;

/**
 * Stream推送方式的钉钉事件回调示例
 *
 * @author Wray
 * @since 2023/8/27
 */
public class StreamCallbackSample {

    public static void main(String[] args) throws Exception {
        OpenDingTalkStreamClientBuilder
                .custom()
                .credential(new AuthClientCredential("AppKey", "AppSecret"))
                //注册事件监听
                .registerAllEventListener(new GenericEventListener() {
                    public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                        try {
                            //事件类型 根据事件类型推断是否为OA审批事件
                            String eventType = event.getEventType();
                            //获取事件体
                            JSONObject bizData = event.getData();
                            //处理事件
                            System.out.println(JSON.toJSONString(bizData));
                            //消费成功
                            return EventAckStatus.SUCCESS;
                        } catch (Exception e) {
                            //消费失败
                            return EventAckStatus.LATER;
                        }
                    }
                })
                .build().start();
    }
}
