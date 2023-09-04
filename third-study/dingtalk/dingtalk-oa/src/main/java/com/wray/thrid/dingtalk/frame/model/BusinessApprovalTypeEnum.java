package com.wray.thrid.dingtalk.frame.model;

import com.wray.thrid.dingtalk.frame.form.ApprovalFormEngine;
import com.wray.thrid.dingtalk.frame.form.DemoApprovalForm;
import lombok.Getter;

/**
 * 业务审批类型枚举
 *
 * @author wangfarui
 * @since 2023/8/2
 */
@Getter
public enum BusinessApprovalTypeEnum {
    DEMO(-1, "测试OA审批", DemoApprovalForm.class),
    ;

    /**
     * 业务审批类型的编号
     * <p>枚举内唯一!!!</p>
     * <p>在不改动code的情况下，修改表单控件，会同步更新钉钉表单模板</p>
     */
    private final Integer code;

    /**
     * 审批表单名称（即钉钉表单的名称）
     * <p>枚举内唯一!!!</p>
     */
    private final String name;

    /**
     * 审批表单Class类
     */
    private final Class<? extends ApprovalFormEngine> formClazz;

    /**
     * 审批表单描述
     */
    private final String desc;

    BusinessApprovalTypeEnum(Integer code, String name, Class<? extends ApprovalFormEngine> formClazz) {
        this(code, name, formClazz, null);
    }

    BusinessApprovalTypeEnum(Integer code, String name, Class<? extends ApprovalFormEngine> formClazz, String desc) {
        this.code = code;
        this.name = name;
        this.formClazz = formClazz;
        this.desc = desc;
    }

    public static BusinessApprovalTypeEnum getApprovalFormEnum(Integer code) {
        BusinessApprovalTypeEnum[] businessApprovalTypeEnums = values();
        for (BusinessApprovalTypeEnum businessApprovalTypeEnum : businessApprovalTypeEnums) {
            if (businessApprovalTypeEnum.getCode().equals(code)) {
                return businessApprovalTypeEnum;
            }
        }
        return null;
    }

    public static String getName(Integer code) {
        BusinessApprovalTypeEnum[] businessApprovalTypeEnums = values();
        for (BusinessApprovalTypeEnum businessApprovalTypeEnum : businessApprovalTypeEnums) {
            if (businessApprovalTypeEnum.getCode().equals(code)) {
                return businessApprovalTypeEnum.getName();
            }
        }
        return null;
    }
}
