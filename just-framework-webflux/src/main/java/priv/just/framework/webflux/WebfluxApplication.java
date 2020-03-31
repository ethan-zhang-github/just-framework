package priv.just.framework.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-27 11:32
 */
@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        // Reactor debug
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
        SpringApplication.run(WebfluxApplication.class, args);
    }

}
