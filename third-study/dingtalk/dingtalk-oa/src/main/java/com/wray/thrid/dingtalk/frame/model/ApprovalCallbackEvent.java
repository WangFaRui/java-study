package com.wray.thrid.dingtalk.frame.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批回调事件对象
 *
 * @author wangfarui
 * @since 2023/8/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalCallbackEvent extends DingTalkCallbackEvent {

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 用户id
     * <p>当钉钉企业用户与SCM企业用户的手机号匹配不到时，可能为空</p>
     */
    private Long userId;

    /**
     * 审批事件类型
     */
    private ApprovalEventType approvalEventType;

}
