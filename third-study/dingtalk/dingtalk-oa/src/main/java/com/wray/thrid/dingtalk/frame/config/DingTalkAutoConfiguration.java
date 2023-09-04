package com.wray.thrid.dingtalk.frame.config;

import com.wray.thrid.dingtalk.frame.runner.AbstractDingTalkApplicationRunner;
import com.wray.thrid.dingtalk.frame.runner.DefaultDingTalkApplicationRunner;
import com.wray.thrid.dingtalk.frame.runner.LocalDingTalkApplicationRunner;
import com.wray.thrid.dingtalk.frame.service.DdApprovalFormRelService;
import com.wray.thrid.dingtalk.frame.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 钉钉自动装配
 *
 * @author wangfarui
 * @since 2023/8/16
 */
@Configuration(proxyBeanMethods = false)
@Import({DingTalkApolloConfiguration.class, DingTalkCallbackProcessor.class})
@Slf4j
public class DingTalkAutoConfiguration {

    @Value("${os.name}")
    private String osName;

    @Value("${dingtalk.oa.runner.enabled:false}")
    private boolean enabled;

    @Bean
    @ConditionalOnMissingBean
    public AbstractDingTalkApplicationRunner defaultDingTalkRunner(DdApprovalFormRelService ddApprovalFormRelService) {
        if (this.enabled || (StringUtils.isBlank(this.osName) || this.osName.contains("Linux"))) {
            log.info("钉钉配置：启用默认钉钉应用启动器");
            return new DefaultDingTalkApplicationRunner(ddApprovalFormRelService);
        } else {
            log.info("钉钉配置: 启用本地钉钉应用启动器, osName: " + this.osName);
            return new LocalDingTalkApplicationRunner(ddApprovalFormRelService);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringUtil defaultSpringUtil() {
        return new SpringUtil();
    }
}
