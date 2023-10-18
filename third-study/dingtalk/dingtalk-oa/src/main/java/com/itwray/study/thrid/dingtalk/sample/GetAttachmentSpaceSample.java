package com.itwray.study.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceResponse;
import com.aliyun.teautil.models.RuntimeOptions;

/**
 * 获取审批钉盘空间信息
 */
public class GetAttachmentSpaceSample {

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
        // 4579431296
        com.aliyun.dingtalkworkflow_1_0.Client client = GetAttachmentSpaceSample.createClient();
        com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceHeaders getAttachmentSpaceHeaders = new com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceHeaders();
        getAttachmentSpaceHeaders.xAcsDingtalkAccessToken = "<your access token>";
        com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceRequest getAttachmentSpaceRequest = new com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceRequest()
                .setUserId("01232767186526519111");
        try {
            GetAttachmentSpaceResponse response = client.getAttachmentSpaceWithOptions(getAttachmentSpaceRequest, getAttachmentSpaceHeaders, new RuntimeOptions());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception _err) {
            _err.printStackTrace();
        }
    }
}