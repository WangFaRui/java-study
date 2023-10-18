package com.itwray.study.thrid.dingtalk.frame.model.business;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务系统-用户信息
 *
 * @author Wray
 * @since 2023/9/4
 */
@Getter
@Setter
public class UserResponse {

    private Long id;

    private String userName;

    private String mobile;
}
