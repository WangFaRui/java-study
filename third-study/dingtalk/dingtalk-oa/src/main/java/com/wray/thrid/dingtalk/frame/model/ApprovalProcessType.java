package com.wray.thrid.dingtalk.frame.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 审批流程类型
 *
 * @author wangfarui
 * @since 2023/8/28
 */
@Getter
@Slf4j
public enum ApprovalProcessType {

    UNKOWNN(0, "未知"),
    START_PROCESS(1, "发起流程"),
    AGREE(2, "审批同意"),
    REFUSE(3, "审批拒绝"),
    COMMENT(4, "评论"),
    REDIRECT(5, "审批转交");

    private final Integer code;

    private final String desc;

    public static final String RESULT_AGREE = "agree";

    public static final String RESULT_REFUSE = "refuse";

    public static final String RESULT_REDIRECT = "redirect";

    public static final String TYPE_START = "start";

    public static final String TYPE_FINISH = "finish";

    public static final String TYPE_COMMENT = "comment";

    public static final String TYPE_TERMINATE = "terminate";

    ApprovalProcessType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 任务状态变更类型：
     * start：审批任务开始
     * finish：审批任务正常结束（完成或转交） 再根据result判断 agree（同意）、refuse（拒绝）、redirect（转交）
     * cancel：说明当前节点有多个审批人并且是或签，其中一个人执行了审批，其他审批人会推送cancel类型事件。
     * comment：评论
     */
    public static ApprovalProcessType confirmProcessType(String type, String result) {
        if (TYPE_START.equals(type)) {
            return START_PROCESS;
        }
        if (TYPE_FINISH.equals(type)) {
            if (RESULT_AGREE.equals(result)) {
                return AGREE;
            } else if (RESULT_REFUSE.equals(result)) {
                return REFUSE;
            } else if (RESULT_REDIRECT.equals(result)) {
                return REDIRECT;
            }
        }
        if (TYPE_COMMENT.equals(type)) {
            return COMMENT;
        }

        log.warn("未知的审批流程类型, type: {}, result: {}", type, result);
        return UNKOWNN;
    }
}
