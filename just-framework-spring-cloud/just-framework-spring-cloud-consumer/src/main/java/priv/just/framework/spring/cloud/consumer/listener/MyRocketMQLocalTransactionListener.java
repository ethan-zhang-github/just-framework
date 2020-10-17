package priv.just.framework.spring.cloud.consumer.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import priv.just.framework.spring.cloud.provider.api.domain.TransacationArg;
import priv.just.framework.spring.cloud.provider.api.domain.TransacationInfo;

import static org.apache.rocketmq.spring.core.RocketMQLocalTransactionState.*;

@Component
@RocketMQTransactionListener
public class MyRocketMQLocalTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        if (msg.getPayload() instanceof TransacationInfo && arg instanceof TransacationArg) {
            TransacationInfo transacationInfo = (TransacationInfo) msg.getPayload();
            TransacationArg transacationArg = (TransacationArg) arg;
            if (transacationArg.getStatus() % 2 == 1) {
                return COMMIT;
            }
            return ROLLBACK;
        }
        return UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return COMMIT;
    }

}
