package com.wray.thrid.dingtalk.frame.listener;

import com.wray.thrid.dingtalk.frame.model.DingTalkCallbackEvent;

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
