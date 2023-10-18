package com.itwray.study.thrid.dingtalk.frame.service;

import com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceResponseBody;
import com.itwray.study.thrid.dingtalk.frame.dao.DdApprovalBusinessRelDao;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalAuditStatus;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalBusinessRel;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalFormRel;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.itwray.study.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
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
}
