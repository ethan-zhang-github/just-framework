package priv.just.framework.batch.writer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@Setter
public class EasyExcelItemWriter<T> implements ItemWriter<T>, InitializingBean, DisposableBean {

    private Resource resource;
    private ExcelWriter writer;
    private List<WriteSheet> sheets;
    private AtomicInteger curSheetIndex = new AtomicInteger(-1);
    private AtomicInteger linesWritten = new AtomicInteger(0);
    private String sheetNamePrefix = "sheet";
    private int maxSheetLines = 100000;
    private Class<T> targetClz;

    @Override
    public void write(List<? extends T> items) {
        ensureSheetSize();
        writer.write(items, getCurSheet());
        linesWritten.addAndGet(items.size());
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(resource, "The resource must be set");
        if (Objects.isNull(writer)) {
            try {
                writer = EasyExcel.write(resource.getFile(), targetClz).build();
            } catch (IOException e) {
                log.error("ExcelWriter initializing error!", e);
                throw new ItemStreamException("ExcelWriter initializing error!");
            }
        }
        if (Objects.isNull(sheets)) {
            sheets = Collections.synchronizedList(new ArrayList<>());
        }
        if (CollectionUtils.isEmpty(sheets)) {
            ensureSheetSize();
        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(writer)) {
            writer.finish();
        }
    }

    private void ensureSheetSize() {
        int curMaxLines = sheets.size() * maxSheetLines;
        if (linesWritten.get() >= curMaxLines) {
            synchronized (this) {
                WriteSheet writeSheet = EasyExcel.writerSheet(curSheetIndex.get(), sheetNamePrefix + "-" + curSheetIndex.get()).build();
                sheets.add(writeSheet);
                curSheetIndex.incrementAndGet();
            }
        }
    }

    private WriteSheet getCurSheet() {
        return sheets.get(curSheetIndex.get());
    }

}
