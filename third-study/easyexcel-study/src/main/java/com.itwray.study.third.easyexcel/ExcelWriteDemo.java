package com.itwray.study.third.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Excel 写示例
 *
 * @author wangfarui
 * @since 2023/10/25
 */
public class ExcelWriteDemo {

    public static void main(String[] args) {
        String fileName = "/Users/wangfarui/Downloads/dddemo.xlsx";

        List<CoalLogisticsReceiptExcelDto> list = new ArrayList<>();
        CoalLogisticsReceiptExcelDto dto = new CoalLogisticsReceiptExcelDto();
        dto.setSn(1);
        dto.setDeliveryDate("2023-01-01");
        dto.setGoodsName("AA");
        dto.setPlateNumber("111");
        list.add(dto);

        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("deliveryDate");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, CoalLogisticsReceiptExcelDto.class)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet(1)
                .doWrite(list);


    }
}
