package com.wray.thrid.dingtalk.frame.form;

import com.wray.thrid.dingtalk.frame.annotation.FormComponent;
import com.wray.thrid.dingtalk.frame.model.BusinessApprovalTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 示例审批表单
 *
 * @author wangfarui
 * @since 2023/8/2
 */
@Data
public class DemoApprovalForm implements ApprovalFormEngine {

    @FormComponent(value = "名称", required = true)
    private String projectName;

//    @FormComponent(value = "创建时间", pattern = "yyyy-MM-dd")
//    private Date createTime;
//
//    @FormComponent("项目明细")
//    private List<ProjectDetail> detailList;
//
//    @FormComponent("附件")
//    private FileDto fileDto;
//
//    @FormComponent("附件2")
//    private List<FileDto> fileDtoList;

//    @FormComponent(value = "关联审批单据", componentType = ComponentType.RelateField, required = true)
//    private String related;

    @Override
    public BusinessApprovalTypeEnum getBusinessApprovalTypeEnum() {
        return BusinessApprovalTypeEnum.DEMO;
    }

    @Data
    public static class ProjectDetail {

        @FormComponent("编号")
        private Long code;

        /**
         * 默认不做精度控制，可以使用String自己控制精度
         */
        @FormComponent("吨位")
        private BigDecimal unit;

        @FormComponent("金额")
        private BigDecimal money;

        @FormComponent("创建日期")
        private Date creatTime;
    }
}
