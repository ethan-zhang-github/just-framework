package priv.just.framework.spring.cloud.provider.api.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TestChannel {

    String INPUT = "test-input";

    String OUTPUT = "test-output";

    @Output(TestChannel.OUTPUT)
    MessageChannel output();

    @Input(TestChannel.INPUT)
    SubscribableChannel input();

}
