package com.itwray.study.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceResponse;
import com.aliyun.teautil.models.RuntimeOptions;

public class GetInstanceDetailSample {

    /**
     * 使用 Token 初始化账号Client
     */
    public static com.aliyun.dingtalkworkflow_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkworkflow_1_0.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.Client client = GetInstanceDetailSample.createClient();
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders getProcessInstanceHeaders = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders();
        getProcessInstanceHeaders.xAcsDingtalkAccessToken = "d62086b9f7943eedb06ee0c096d9e111";
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest getProcessInstanceRequest = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest()
                .setProcessInstanceId("KW2ZY679TRyy3Nupz5MN8A10181692956111");
        try {
            GetProcessInstanceResponse response = client.getProcessInstanceWithOptions(getProcessInstanceRequest, getProcessInstanceHeaders, new RuntimeOptions());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception _err) {
            _err.printStackTrace();
        }        
    }
}