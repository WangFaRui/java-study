package com.itwray.study.thrid.dingtalk.frame;

import com.aliyun.dingtalkworkflow_1_0.models.GetAttachmentSpaceResponseBody;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkAccessTokenClient;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkUserInfoClient;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkConfig;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkTenantProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 钉钉授权信息管理
 *
 * @author wangfarui
 * @since 2023/8/1
 */
@Slf4j
public class DingTalkAuthManager {

    public static final String TOKEN_KEY = "token";

    public static final String DEFAULT_UNION_ID = "defaultUnionId";

    public static final String SPACE_ID = "spaceId";

    public static String getToken() {
        return DingTalkConfig.getValueByTimerCache(TOKEN_KEY, DingTalkAccessTokenClient::getToken);
    }

    public static String getDefaultUnionId() {
        return DingTalkConfig.getValueByTimerCache(DEFAULT_UNION_ID, () -> {
            String userId = DingTalkConfig.getTenantPropertiesValue(DingTalkTenantProperties::getUserId);
            OapiV2UserGetResponse.UserGetResponse response = DingTalkUserInfoClient.getUserById(userId);
            return response.getUnionid();
        });
    }

    public static String getSpaceId() {
        return DingTalkConfig.getValueByTimerCache(SPACE_ID, () -> {
            GetAttachmentSpaceResponseBody.GetAttachmentSpaceResponseBodyResult attachmentSpace = DingTalkWorkflowClient.getAttachmentSpace();
            return Optional.ofNullable(attachmentSpace)
                    .map(t -> t.getSpaceId().toString())
                    .orElse("");
        });
    }
}
