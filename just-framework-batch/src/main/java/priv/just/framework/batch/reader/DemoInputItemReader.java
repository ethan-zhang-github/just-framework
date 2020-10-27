package priv.just.framework.batch.reader;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import priv.just.framework.batch.domain.DemoInput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Setter
public class DemoInputItemReader extends AbstractPaginatedDataItemReader<DemoInput> {

    private Random random = new Random();

    private int totalPage;

    @Override
    protected Iterator<DemoInput> doPageRead() {
        if (page >= totalPage) {
            return null;
        }
        List<DemoInput> list = Stream.generate(() -> {
            DemoInput item = new DemoInput();
            item.setId(random.nextInt(10000));
            item.setDate(new Date());
            item.setUuid(UUID.randomUUID().toString());
            return item;
        }).limit(pageSize).collect(Collectors.toList());
        log.info("READER THREAD，ID：{}，NAME：{}，DATA：{}", Thread.currentThread().getId(),
                Thread.currentThread().getName(), JSON.toJSONString(list));
        return list.iterator();
    }

}
