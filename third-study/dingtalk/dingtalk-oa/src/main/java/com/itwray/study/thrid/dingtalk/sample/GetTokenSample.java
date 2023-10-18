package com.itwray.study.thrid.dingtalk.sample;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

public class GetTokenSample {
    public static void main(String[] args) {
        // 7011eb7b5a5e37c694b18c6c79406111
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey("ding7lc9hw1iizyltcg8");
            req.setAppsecret("kujxoSo9zCHw5mv7ejIZjNbI8NK2zfvARHIc5y-svUxqUxZlZgHnpeCYFvBRkm1-");
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}