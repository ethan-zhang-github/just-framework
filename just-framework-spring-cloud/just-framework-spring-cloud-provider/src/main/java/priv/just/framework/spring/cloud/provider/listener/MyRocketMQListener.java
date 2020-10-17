package priv.just.framework.spring.cloud.provider.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.service.UserService;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(
        topic = "test_pre-release_topic_common_order_general",
        consumerGroup = "provider-consumer-group",
        selectorExpression = "addUserInfo"
)
public class MyRocketMQListener implements RocketMQListener<UserInfo> {

    @Resource
    private UserService userService;

    @Override
    public void onMessage(UserInfo userInfo) {
        userService.addUserInfo(userInfo);
    }

}
