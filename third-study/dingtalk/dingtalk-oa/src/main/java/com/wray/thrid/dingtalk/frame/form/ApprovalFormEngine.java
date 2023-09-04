package com.wray.thrid.dingtalk.frame.form;

import cn.hutool.crypto.digest.MD5;
import com.aliyun.dingtalkworkflow_1_0.models.FormComponent;
import com.wray.thrid.dingtalk.frame.config.DingTalkConfig;
import com.wray.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
import com.wray.thrid.dingtalk.frame.util.DingTalkFormUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues;

/**
 * 审批表单Engine
 *
 * @author wangfarui
 * @since 2022/11/11
 */
public interface ApprovalFormEngine {

    /**
     * 获取业务审批类型枚举
     */
    BusinessApprovalTypeEnum getBusinessApprovalTypeEnum();

    /**
     * 表单版本
     */
    default String version() {
        return DingTalkConfig.getFormVersion(getBusinessApprovalTypeEnum(), () -> {
            Field[] fields = this.getClass().getDeclaredFields();
            long versionSum = 0L;
            for (Field field : fields) {
                int version = DingTalkFormUtil.computeComponentVersion(field);
                versionSum += version;
            }
            return MD5.create().digestHex16(String.valueOf(versionSum));
        });
    }

    default List<FormComponent> buildFormComponent() {
        Field[] fields = this.getClass().getDeclaredFields();
        List<FormComponent> defaultFormComponents = DingTalkFormUtil.generateDefaultFormComponents();
        List<FormComponent> list = new ArrayList<>(fields.length + defaultFormComponents.size());
        for (Field field : fields) {
            FormComponent formComponent = DingTalkFormUtil.generateFormComponent(field);
            if (formComponent != null) {
                list.add(formComponent);
            }
        }
        // 默认表单控件置于最后
        list.addAll(defaultFormComponents);
        return list;
    }

    default List<StartProcessInstanceRequestFormComponentValues> buildFormComponentValues() {
        Field[] fields = this.getClass().getDeclaredFields();
        List<StartProcessInstanceRequestFormComponentValues> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            StartProcessInstanceRequestFormComponentValues formComponentValues = DingTalkFormUtil.generateFormComponentValues(field, this);
            if (formComponentValues != null) {
                list.add(formComponentValues);
            }
        }
        return list;
    }
}
