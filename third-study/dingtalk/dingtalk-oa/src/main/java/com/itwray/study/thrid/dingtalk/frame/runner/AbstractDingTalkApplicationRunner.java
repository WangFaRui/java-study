package com.itwray.study.thrid.dingtalk.frame.runner;

import com.itwray.study.thrid.dingtalk.frame.config.DingTalkConfig;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.service.DdApprovalFormRelService;
import com.itwray.study.thrid.dingtalk.frame.form.ApprovalFormEngine;
import com.itwray.study.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

import java.util.Set;

/**
 * 钉钉应用Runner 抽象类
 *
 * <ol>
 *     <li>初始化业务审批模板</li>
 * </ol>
 *
 * @author wangfarui
 * @since 2023/8/30
 */
@Slf4j
public abstract class AbstractDingTalkApplicationRunner implements ApplicationRunner, Ordered {

    private final DdApprovalFormRelService ddApprovalFormRelService;

    public AbstractDingTalkApplicationRunner(DdApprovalFormRelService ddApprovalFormRelService) {
        this.ddApprovalFormRelService = ddApprovalFormRelService;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.innerRun();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    public void innerRun() {
        this.initApprovalForm();
    }

    /**
     * 初始化审批表单
     */
    private void initApprovalForm() {
        BusinessApprovalTypeEnum[] businessApprovalTypeEnums = BusinessApprovalTypeEnum.values();
        Set<Long> tenantIds = DingTalkConfig.getAllTenantId();
        for (Long tenantId : tenantIds) {
            for (BusinessApprovalTypeEnum typeEnum : businessApprovalTypeEnums) {
                ApprovalFormEngine approvalForm;
                try {
                    approvalForm = typeEnum.getFormClazz().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("[" + typeEnum.getName() + "]审批表单实例化失败", e);
                    throw new RuntimeException(e);
                }
                ApprovalFormInstance approvalFormInstance = ApprovalFormInstance.builder()
                        .approvalForm(approvalForm)
                        .tenantId(tenantId)
                        .build();
                try {
                    DingTalkConfig.setTenantIdContext(tenantId);
                    ddApprovalFormRelService.resolveDdApprovalFormRel(approvalFormInstance);
                } finally {
                    DingTalkConfig.removeTenantIdContext();
                }
            }
        }
        log.info("初始化钉钉审批表单成功");
    }

}
