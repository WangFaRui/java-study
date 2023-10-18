package com.itwray.study.thrid.dingtalk.frame.client;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkConfig;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkTenantProperties;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkURLConstant;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉TokenClient
 */
@Slf4j
public class DingTalkAccessTokenClient {

    /**
     * 获取钉钉开发者token
     */
    public static String getToken() {
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(DingTalkConfig.getTenantPropertiesValue(DingTalkTenantProperties::getAppKey));
        request.setAppsecret(DingTalkConfig.getTenantPropertiesValue(DingTalkTenantProperties::getAppSecret));
        request.setHttpMethod("GET");
        DefaultDingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.URL_GET_TOKKEN);
        try {
            OapiGettokenResponse response = client.execute(request);
            if (response == null || !response.isSuccess()) {
                log.error("钉钉api请求错误, req: {}, res: {}", JSON.toJSONString(request), JSON.toJSONString(response));
                throw new IllegalArgumentException();
            }
            return response.getAccessToken();
        } catch (ApiException e) {
            log.error("钉钉api请求异常", e);
            throw new IllegalStateException();
        }
    }
}
