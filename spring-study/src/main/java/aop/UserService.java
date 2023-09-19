package aop;

import org.springframework.stereotype.Component;

import java.time.ZoneId;

/**
 * 用户服务
 *
 * @author Wray
 * @since 2023/9/19
 */
@Component
public class UserService {

    public final ZoneId zoneId = ZoneId.systemDefault();

    public ZoneId getZoneId() {
        return zoneId;
    }

    public final ZoneId getFinalZoneId() {
        return zoneId;
    }
}
