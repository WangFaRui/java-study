package com.itwray.study.thrid.dingtalk.frame.listener;

import com.itwray.study.thrid.dingtalk.frame.model.DingTalkCallbackEvent;

/**
 * 钉钉回调监听器
 *
 * @author wangfarui
 * @since 2023/8/21
 */
@FunctionalInterface
public interface DingTalkCallbackListener {

    void callback(DingTalkCallbackEvent callbackEvent);
}
