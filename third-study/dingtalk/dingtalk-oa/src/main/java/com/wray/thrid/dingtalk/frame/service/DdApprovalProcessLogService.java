package com.wray.thrid.dingtalk.frame.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.aliyun.dingtalkstorage_1_0.models.GetFileDownloadInfoResponseBody;
import com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceResponseBody;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wray.thrid.dingtalk.frame.client.DingTalkStorageClient;
import com.wray.thrid.dingtalk.frame.client.DingTalkWorkflowClient;
import com.wray.thrid.dingtalk.frame.dao.DdApprovalProcessLogAttachmentDao;
import com.wray.thrid.dingtalk.frame.dao.DdApprovalProcessLogDao;
import com.wray.thrid.dingtalk.frame.model.ApprovalProcessType;
import com.wray.thrid.dingtalk.frame.model.ApprovalTaskChange;
import com.wray.thrid.dingtalk.frame.model.entity.DdApprovalProcessLog;
import com.wray.thrid.dingtalk.frame.model.entity.DdApprovalProcessLogAttachment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 钉钉审批流程日志 服务层
 *
 * @author wangfarui
 * @since 2023/8/28
 */
@Service
public class DdApprovalProcessLogService {

    @Resource
    private DdApprovalProcessLogDao ddApprovalProcessLogDao;

    @Resource
    private DdApprovalProcessLogAttachmentDao ddApprovalProcessLogAttachmentDao;

    @Transactional(rollbackFor = Exception.class)
    public void generateLog(ApprovalTaskChange taskChange, Long tenantId, Long businessRelId) {
        Long count = ddApprovalProcessLogDao.lambdaQuery()
                .eq(DdApprovalProcessLog::getTenantId, tenantId)
                .eq(DdApprovalProcessLog::getEventId, taskChange.getEventId())
                .count();
        if (count > 0) {
            // 已生成日志 ignore
            return;
        }

        DdApprovalProcessLog approvalProcessLog = new DdApprovalProcessLog();
        approvalProcessLog.setTenantId(tenantId);
        approvalProcessLog.setBusinessRelId(businessRelId);
        approvalProcessLog.setEventId(taskChange.getEventId());
        approvalProcessLog.setTaskId(taskChange.getTaskId());
        approvalProcessLog.setStaffId(taskChange.getStaffId());
        approvalProcessLog.setStaffName(taskChange.getStaffName());
        approvalProcessLog.setProcessContent(taskChange.getContent() != null ? taskChange.getContent() : taskChange.getRemark());
        approvalProcessLog.setCreateTime(taskChange.getEventBornTime());
        ApprovalProcessType processType = ApprovalProcessType.confirmProcessType(taskChange.getType(), taskChange.getResult());
        approvalProcessLog.setProcessType(processType.getCode());
        ddApprovalProcessLogDao.save(approvalProcessLog);

        this.saveLogAttachment(taskChange, tenantId, approvalProcessLog.getId());
    }

    private void saveLogAttachment(ApprovalTaskChange taskChange, Long tenantId, Long logId) {
        // 查询实例详情
        GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResult instanceResponse = DingTalkWorkflowClient.getProcessInstanceById(taskChange.getProcessInstanceId());
        List<GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecords> operationRecords = instanceResponse.getOperationRecords();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        String eventBornTime = simpleDateFormat.format(taskChange.getEventBornTime());
        for (int len = operationRecords.size() - 1; len >= 0; len--) {
            GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecords records = operationRecords.get(len);
            if (!taskChange.getStaffId().equals(records.getUserId())) {
                continue;
            }
            if (!eventBornTime.equals(records.getDate())) {
                try {
                    Date parse = simpleDateFormat.parse(records.getDate());
                    long after = parse.getTime() + (60 * 1000);
                    if (after < taskChange.getEventBornTime().getTime()) {
                        // 操作记录的时间点 小于 事件发生的事件点
                        break;
                    }
                } catch (ParseException e) {
                    // ignore
                }
                continue;
            }
            // 匹配到审批操作记录，判断当前审批操作是否携带附件
            if (CollectionUtils.isNotEmpty(records.getAttachments())) {
                List<String> fileIdList = records.getAttachments()
                        .stream()
                        .map(GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecordsAttachments::getFileId)
                        .collect(Collectors.toList());
                DingTalkWorkflowClient.authorizeApprovalDentry(fileIdList);

                for (GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecordsAttachments attachment : records.getAttachments()) {
                    String wyysFileUrl = this.uploadFileToBusinessSystem(attachment.getFileId(), attachment.getFileName());
                    if (wyysFileUrl == null) continue;
                    DdApprovalProcessLogAttachment logAttachment = new DdApprovalProcessLogAttachment();
                    logAttachment.setTenantId(tenantId);
                    logAttachment.setLogId(logId);
                    logAttachment.setFileName(attachment.getFileName());
                    logAttachment.setFileType(attachment.getFileType());
                    logAttachment.setFileUrl(wyysFileUrl);
                    ddApprovalProcessLogAttachmentDao.save(logAttachment);
                }
            }
            break;
        }
    }

    private String uploadFileToBusinessSystem(String fileId, String fileName) {
        GetFileDownloadInfoResponseBody.GetFileDownloadInfoResponseBodyHeaderSignatureInfo headerSignatureInfo = DingTalkStorageClient.getFileDownloadInfo(fileId);
        if (headerSignatureInfo == null) return null;

        // http下载文件
        HttpRequest httpRequest = HttpRequest.post(headerSignatureInfo.getResourceUrls().get(0));
        for (Map.Entry<String, String> entry : headerSignatureInfo.getHeaders().entrySet()) {
            httpRequest.header(entry.getKey(), entry.getValue());
        }
        HttpResponse httpResponse = httpRequest.execute();
        InputStream inputStream = httpResponse.bodyStream();

        // 保存到业务系统文件服务器，并返回全量路径地址
        return "";
    }

}
