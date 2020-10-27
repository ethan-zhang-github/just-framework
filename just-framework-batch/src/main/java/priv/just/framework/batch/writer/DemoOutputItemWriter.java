package priv.just.framework.batch.writer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import priv.just.framework.batch.domain.DemoOutput;

import java.util.List;

@Slf4j
public class DemoOutputItemWriter implements ItemWriter<DemoOutput> {

    @Override
    public void write(List<? extends DemoOutput> items) {
        log.info("WRITER THREAD，ID：{}，NAME：{}，COUNT：{}，DATA：{}", Thread.currentThread().getId(),
                Thread.currentThread().getName(), items.size(), JSON.toJSONString(items));
    }

}
