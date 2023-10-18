package com.itwray.study.thrid.dingtalk.frame.model;

import lombok.Getter;

/**
 * 审批实例的审核状态
 *
 * @author Wray
 * @since 2023/9/4
 */
@Getter
public enum ApprovalAuditStatus {

    AUDITING(1, "审核中"),
    AUDITED_AGREE(2, "审核通过"),
    AUDITED_REJECT(3, "审核拒绝"),
    REVOKE(4, "审核撤销");

    private final Integer code;

    private final String desc;

    ApprovalAuditStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
