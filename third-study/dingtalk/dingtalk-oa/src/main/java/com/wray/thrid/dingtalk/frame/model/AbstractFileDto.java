package com.wray.thrid.dingtalk.frame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件抽象对象
 *
 * @author wangfarui
 * @since 2023/8/19
 */
@Getter
@Setter
public abstract class AbstractFileDto {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件URL地址
     */
    private String fileUrl;
}
