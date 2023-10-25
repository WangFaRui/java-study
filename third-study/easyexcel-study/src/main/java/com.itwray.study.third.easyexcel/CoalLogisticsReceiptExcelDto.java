package com.itwray.study.third.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CoalLogisticsReceiptExcelDto {

    @ExcelProperty("序号")
    private Integer sn;

    @ExcelProperty("供货日期")
    private String deliveryDate;

    @ExcelProperty("商品名称")
    private String goodsName;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty("发运地")
    private String shippingPlace;

    @ExcelProperty("发运数量（吨）")
    private String shippingQty;

    @ExcelProperty("目的地")
    private String deliveryPlace;

    @ExcelProperty("收货日期")
    private String receiptDate;

    @ExcelProperty("实收数量（吨）")
    private String qty;

    @ExcelProperty("损耗（吨）")
    private String loss;

    @ExcelProperty("备注")
    private String remark;

}
