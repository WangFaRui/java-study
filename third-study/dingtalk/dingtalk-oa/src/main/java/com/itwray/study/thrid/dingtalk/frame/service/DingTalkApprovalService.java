package com.itwray.study.thrid.dingtalk.frame.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceRequest;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkCommonConstant;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalBusinessRel;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdApprovalFormRel;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.itwray.study.thrid.dingtalk.frame.config.DingTalkConfig;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdUserRel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 钉钉审批 服务层
 *
 * @author wangfarui
 * @since 2023/8/2
 */
@Service
@Slf4j
public class DingTalkApprovalService {

    @Resource
    private DdUserRelService ddUserRelService;
    @Resource
    private DdApprovalFormRelService ddApprovalFormRelService;
    @Resource
    private DdApprovalBusinessRelService ddApprovalBusinessRelService;

    /**
     * 发起审批流程实例
     */
    public void startApprovalFlowInstance(ApprovalFormInstance instance) {
        log.info("发起审批流程实例: {}", instance);
        instance.checkParamValid();
        DingTalkConfig.setTenantIdContext(instance.getTenantId());

        try {
            // 1. 创建并获取审批模板
            DdApprovalFormRel ddApprovalFormRel = ddApprovalFormRelService.resolveDdApprovalFormRel(instance);
            log.info("发起审批流程实例-审批模板: {}", ddApprovalFormRel);

            // 2. 获取审批发起人对应的钉钉用户信息
            DdUserRel ddUserRel = ddUserRelService.resolveDdUserRel(instance.getTenantId(), instance.getUserId());
            // 2.1 若手动指定钉钉部门id，则覆盖用户关联的钉钉部门
            if (instance.getDepartmentId() != null) {
                ddUserRel.setDdDeptId(instance.getDepartmentId());
            }
            log.info("发起审批流程实例-钉钉用户: {}", ddUserRel);

            // 3. 发起审批实例，拿取实例id
            String instanceId = DingTalkWorkflowClient.processInstances(
                    instance, ddUserRel, ddApprovalFormRel,
                    () -> buildSelfRelateFieldValues(instance.getBusinessId())
            );
            log.info("发起审批流程实例-审批实例: {}", instanceId);

            // 4. 保存审批实例与业务单据的关联信息
            ddApprovalBusinessRelService.saveApprovalBusinessRel(instance, ddApprovalFormRel, instanceId);
        } finally {
            DingTalkConfig.removeTenantIdContext();
        }

    }

    public StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues buildSelfRelateFieldValues(Long businessId) {
        List<DdApprovalBusinessRel> approvalRecords = ddApprovalBusinessRelService.getApprovalRecords(businessId);
        if (approvalRecords.isEmpty()) {
            return null;
        }

        // 审批实例id
        List<JSONObject> list = new ArrayList<>(approvalRecords.size());
        for (DdApprovalBusinessRel ddApprovalBusinessRel : approvalRecords) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("procInstId", ddApprovalBusinessRel.getInstanceId());
            jsonObject.put("businessId", ddApprovalBusinessRel.getInstanceBusinessId());
            list.add(jsonObject);
        }
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("list", list);

        // 审批实例标题
        List<String> instanceTitleList = approvalRecords.stream()
                .map(DdApprovalBusinessRel::getInstanceTitle)
                .collect(Collectors.toList());

        return new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                .setName(DingTalkCommonConstant.SELF_RELATE_FIELD_NAME)
                .setValue(JSON.toJSONString(instanceTitleList))
                .setExtValue(JSON.toJSONString(jsonObject2));
    }

}
