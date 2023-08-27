package com.wray.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceHeaders;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceRequest;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

import java.util.Collections;

public class StartInstanceSample {

    /**
     * 使用 Token 初始化账号Client
     */
    public static com.aliyun.dingtalkworkflow_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkworkflow_1_0.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.Client client = StartInstanceSample.createClient();
        StartProcessInstanceHeaders startProcessInstanceHeaders = new StartProcessInstanceHeaders();
        startProcessInstanceHeaders.xAcsDingtalkAccessToken = "7011eb7b5a5e37c694b18c6c79406111";
        StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues0 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                .setName("名称")
                .setValue("wray");
        StartProcessInstanceRequest startProcessInstanceRequest = new StartProcessInstanceRequest()
                .setOriginatorUserId("306354436429120376")
                .setProcessCode("PROC-0FAB48EA-FB39-4981-AD80-E67D552B3355")
                .setDeptId(-1L)
                .setFormComponentValues(Collections.singletonList(formComponentValues0));
        try {
            StartProcessInstanceResponse response = client.startProcessInstanceWithOptions(startProcessInstanceRequest, startProcessInstanceHeaders, new RuntimeOptions());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception _err) {
            _err.printStackTrace();
        }
    }
}