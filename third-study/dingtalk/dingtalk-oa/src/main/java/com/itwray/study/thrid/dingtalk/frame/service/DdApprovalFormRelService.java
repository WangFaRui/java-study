package com.itwray.study.thrid.dingtalk.frame.service;

import com.itwray.study.thrid.dingtalk.frame.dao.DdApprovalFormRelDao;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalFormRel;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.itwray.study.thrid.dingtalk.frame.form.ApprovalFormEngine;
import com.itwray.study.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 钉钉审批表单关联表 服务实现类
 * </p>
 *
 * @author wangfarui
 * @since 2023-08-02
 */
@Service
public class DdApprovalFormRelService {

    @Resource
    private DdApprovalFormRelDao ddApprovalFormRelDao;

    /**
     * 决定一个钉钉审批表单关联对象
     */
    @Transactional(rollbackFor = Exception.class)
    public DdApprovalFormRel resolveDdApprovalFormRel(ApprovalFormInstance instance) {
        ApprovalFormEngine approvalForm = instance.getApprovalForm();

        // 从关联表中查询钉钉表单模板Code
        DdApprovalFormRel ddApprovalFormRel = ddApprovalFormRelDao.lambdaQuery()
                .eq(DdApprovalFormRel::getTenantId, instance.getTenantId())
                .eq(DdApprovalFormRel::getTypeCode, approvalForm.getBusinessApprovalTypeEnum().getCode())
                .last("limit 1")
                .one();
        if (ddApprovalFormRel != null) {
            String version = approvalForm.version();
            // 版本号是否一致
            if (version.equals(ddApprovalFormRel.getFormVersion())) {
                return ddApprovalFormRel;
            }
            return updateDdApprovalFormRel(approvalForm, ddApprovalFormRel);
        }
        // 关联表无映射时，生成关联数据并返回
        return createDdApprovalFormRel(instance);
    }

    private DdApprovalFormRel updateDdApprovalFormRel(ApprovalFormEngine approvalForm, DdApprovalFormRel ddApprovalFormRel) {
        DingTalkWorkflowClient.saveOrUpdateApprovalForm(approvalForm, ddApprovalFormRel.getProcessCode());
        BusinessApprovalTypeEnum typeEnum = approvalForm.getBusinessApprovalTypeEnum();
        ddApprovalFormRelDao.lambdaUpdate()
                .eq(DdApprovalFormRel::getId, ddApprovalFormRel.getId())
                .set(DdApprovalFormRel::getTypeCode, typeEnum.getCode())
                .set(DdApprovalFormRel::getTypeName, typeEnum.getName())
                .set(DdApprovalFormRel::getFormVersion, approvalForm.version())
                .update();
        ddApprovalFormRel.setTypeCode(typeEnum.getCode());
        ddApprovalFormRel.setTypeName(typeEnum.getName());
        ddApprovalFormRel.setFormVersion(approvalForm.version());
        return ddApprovalFormRel;
    }

    private DdApprovalFormRel createDdApprovalFormRel(ApprovalFormInstance instance) {
        ApprovalFormEngine approvalForm = instance.getApprovalForm();
        BusinessApprovalTypeEnum businessApprovalTypeEnum = approvalForm.getBusinessApprovalTypeEnum();

        // 根据审批表单模板名称获取历史钉钉表单模板code
        String processCode = DingTalkWorkflowClient.getProcessCodeByName(businessApprovalTypeEnum.getName());

        if (StringUtils.isBlank(processCode)) {
            // 新生成钉钉审批表单模板
            processCode = DingTalkWorkflowClient.saveOrUpdateApprovalForm(approvalForm, null);
        }

        // 初始化关联对象并保存
        DdApprovalFormRel ddApprovalFormRel = new DdApprovalFormRel();
        ddApprovalFormRel.setTenantId(instance.getTenantId());
        ddApprovalFormRel.setTypeCode(businessApprovalTypeEnum.getCode());
        ddApprovalFormRel.setTypeName(businessApprovalTypeEnum.getName());
        ddApprovalFormRel.setCreateById(instance.getUserId(false));
        ddApprovalFormRel.setCreateTime(new Date());
        ddApprovalFormRel.setProcessCode(processCode);
        ddApprovalFormRelDao.save(ddApprovalFormRel);

        return ddApprovalFormRel;
    }

}
