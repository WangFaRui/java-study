package com.itwray.study.thrid.dingtalk.frame.service;

import com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceResponseBody;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkConfig;
import com.itwray.study.thrid.dingtalk.frame.dao.DdApprovalBusinessRelDao;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalAuditStatus;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalInstanceStatusEnum;
import com.itwray.study.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalBusinessRel;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalFormRel;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdUserRel;
import com.itwray.study.thrid.dingtalk.frame.util.SpringUtil;
import com.itwray.study.thrid.dingtalk.frame.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 钉钉审批业务关联表 服务实现类
 * </p>
 *
 * @author wangfarui
 * @since 2023-08-02
 */
@Service
public class DdApprovalBusinessRelService {

    @Resource
    private DdApprovalBusinessRelDao ddApprovalBusinessRelDao;

    private static DdUserRelService ddUserRelService;

    /**
     * 保存钉钉审批实例关联数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveApprovalBusinessRel(ApprovalFormInstance instance, DdApprovalFormRel ddApprovalFormRel, String instanceId) {
        // 查询审批实例详情
        GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResult instanceDetail = DingTalkWorkflowClient.getProcessInstanceById(instanceId);

        // 保存钉钉审批业务关联数据
        DdApprovalBusinessRel ddApprovalBusinessRel = new DdApprovalBusinessRel();
        ddApprovalBusinessRel.setTenantId(instance.getTenantId());
        ddApprovalBusinessRel.setBusinessId(instance.getBusinessId());
        ddApprovalBusinessRel.setInstanceId(instanceId);
        ddApprovalBusinessRel.setInstanceHash(instanceId.hashCode());
        ddApprovalBusinessRel.setInstanceBusinessId(instanceDetail.getBusinessId());
        ddApprovalBusinessRel.setInstanceTitle(instanceDetail.getTitle());
        ddApprovalBusinessRel.setFormRelId(ddApprovalFormRel.getId());
        BusinessApprovalTypeEnum businessApprovalTypeEnum = instance.getApprovalForm().getBusinessApprovalTypeEnum();
        ddApprovalBusinessRel.setTypeCode(businessApprovalTypeEnum.getCode());
        ddApprovalBusinessRel.setTypeName(businessApprovalTypeEnum.getName());
        ddApprovalBusinessRel.setProcessCode(ddApprovalFormRel.getProcessCode());
        ddApprovalBusinessRel.setAuditStatus(ApprovalAuditStatus.AUDITING.getCode());
        ddApprovalBusinessRel.setCreateById(instance.getUserId());
        ddApprovalBusinessRel.setCreateTime(new Date());
        ddApprovalBusinessRelDao.save(ddApprovalBusinessRel);

        // TODO 业务系统保存实例id与租户id的映射关系
    }

    /**
     * 根据审批实例获取租户id
     */
    public Long getTenantIdByInstance(String instanceId) {
        // TODO 业务系统根据实例id获取租户id
        return 1L;
    }


    /**
     * 根据审批实例获取审批业务关联数据
     */
    public DdApprovalBusinessRel getDdApprovalBusinessRelByInstance(String instanceId) {
        List<DdApprovalBusinessRel> list = ddApprovalBusinessRelDao.lambdaQuery()
                .eq(DdApprovalBusinessRel::getInstanceHash, instanceId.hashCode())
                .list();
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            return ddApprovalBusinessRelDao.lambdaQuery()
                    .in(DdApprovalBusinessRel::getId, list.stream().map(DdApprovalBusinessRel::getId).collect(Collectors.toList()))
                    .eq(DdApprovalBusinessRel::getInstanceId, instanceId)
                    .last("limit 1")
                    .one();
        }
        return list.get(0);
    }

    public List<DdApprovalBusinessRel> getApprovalRecords(Long businessId) {
        return ddApprovalBusinessRelDao.lambdaQuery()
                .eq(DdApprovalBusinessRel::getBusinessId, businessId)
                .list();
    }

    @Transactional(rollbackFor = Exception.class)
    public void terminateProcessInstance(Long businessId) {
        DdApprovalBusinessRel approvalBusinessRel = ddApprovalBusinessRelDao.lambdaQuery()
                .eq(DdApprovalBusinessRel::getBusinessId, businessId)
                .orderByDesc(DdApprovalBusinessRel::getId)
                .last("limit 1")
                .one();
        if (approvalBusinessRel == null) {
            throw new RuntimeException("未找到对应的钉钉审批流程");
        }
        if (!ApprovalInstanceStatusEnum.RUNNING.getCode().equals(approvalBusinessRel.getAuditStatus())) {
            throw new RuntimeException("审批流程状态不是审批中，无法撤销");
        }
        Date nowDate = new Date();
        long createTime = approvalBusinessRel.getCreateTime().getTime();
        if (nowDate.getTime() - createTime <= 15000) {
            throw new RuntimeException("审批发起15秒内不能撤销审批流程");
        }
        // TODO 业务系统的租户id和用户id
        Long tenantId = UserUtils.getTenantId();
        Long userId = UserUtils.getUserId();
        if (!userId.equals(approvalBusinessRel.getCreateById())) {
            throw new RuntimeException("只有发起人可以撤销审批流程");
        }

        try {
            DingTalkConfig.setTenantIdContext(tenantId);
            DdUserRel ddUserRel = getDdUserRelService().resolveDdUserRel(tenantId, userId);
            DingTalkWorkflowClient.terminateProcessInstance(approvalBusinessRel.getInstanceId(), ddUserRel.getDdUserId());
        } finally {
            DingTalkConfig.removeTenantIdContext();
        }
    }

    private DdUserRelService getDdUserRelService() {
        if (ddUserRelService == null) {
            ddUserRelService = SpringUtil.getBean(DdUserRelService.class);
        }
        return ddUserRelService;
    }
}
