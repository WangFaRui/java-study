package com.wray.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkcontact_1_0.models.ListEmpAttributeVisibilityHeaders;
import com.aliyun.dingtalkcontact_1_0.models.ListEmpAttributeVisibilityRequest;
import com.aliyun.dingtalkcontact_1_0.models.ListEmpAttributeVisibilityResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

public class ListEmpAttributeVisibilitySample {

    /**
     * 使用 Token 初始化账号Client
     */
    public static com.aliyun.dingtalkcontact_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcontact_1_0.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dingtalkcontact_1_0.Client client = ListEmpAttributeVisibilitySample.createClient();
        ListEmpAttributeVisibilityHeaders listEmpAttributeVisibilityHeaders = new ListEmpAttributeVisibilityHeaders();
        listEmpAttributeVisibilityHeaders.xAcsDingtalkAccessToken = "442701d1636e3cccaea6cc8c875cb111";
        ListEmpAttributeVisibilityRequest listEmpAttributeVisibilityRequest = new ListEmpAttributeVisibilityRequest()
                .setNextToken(-1L)
                .setMaxResults(10);
        try {
            ListEmpAttributeVisibilityResponse response = client.listEmpAttributeVisibilityWithOptions(listEmpAttributeVisibilityRequest, listEmpAttributeVisibilityHeaders, new RuntimeOptions());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception _err) {
            _err.printStackTrace();
        }
    }
}