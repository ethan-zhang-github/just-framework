package priv.just.framework.batch.processor;

import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import priv.just.framework.batch.domain.DemoInput;
import priv.just.framework.batch.domain.DemoOutput;

@Slf4j
public class DemoInputOutputItemProcessor implements ItemProcessor<DemoInput, DemoOutput> {

    @Override
    public DemoOutput process(DemoInput input) {
        log.info("PROCESSOR THREAD，ID：{}，NAME：{}, DATA：{}", Thread.currentThread().getId(),
                Thread.currentThread().getName(), JSON.toJSONString(input));
        DemoOutput output = new DemoOutput();
        output.setId(input.getId());
        output.setFormatDate(DateUtils.format(input.getDate()));
        output.setMessage(input.getUuid().replaceAll("-", "").toUpperCase());
        return output;
    }

}
