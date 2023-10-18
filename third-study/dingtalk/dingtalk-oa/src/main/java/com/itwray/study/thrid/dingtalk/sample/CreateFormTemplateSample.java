package com.itwray.study.thrid.dingtalk.sample;

import com.alibaba.fastjson.JSON;
import com.aliyun.teautil.models.*;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.teaopenapi.models.*;

public class CreateFormTemplateSample {

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
        com.aliyun.dingtalkworkflow_1_0.Client client = CreateFormTemplateSample.createClient();
        FormCreateHeaders formCreateHeaders = new FormCreateHeaders();
        formCreateHeaders.xAcsDingtalkAccessToken = "4d4406d9cb713ce8beb70de43883d111";
        // 1. 单行输入控件
        FormComponentProps formComponentProps1 = new FormComponentProps()
                .setLabel("名称");
        FormComponent formComponent1 = new FormComponent()
                .setComponentType("TextField")
                .setProps(formComponentProps1);

        FormCreateRequest formCreateRequest = new FormCreateRequest()
                .setName("测试OA审批")
                .setFormComponents(java.util.Arrays.asList(
                        formComponent1
                ));
        try {
            // PROC-5FAC1A50-E521-4EEF-AC2F-4A2422E2EA9D
            FormCreateResponse response = client.formCreateWithOptions(formCreateRequest, formCreateHeaders, new RuntimeOptions());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception _err) {
            _err.printStackTrace();
        }
    }
}