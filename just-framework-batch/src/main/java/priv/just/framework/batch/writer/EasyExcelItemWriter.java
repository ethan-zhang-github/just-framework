package priv.just.framework.batch.writer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private int maxSheetLines = Integer.MAX_VALUE;
    private Class<? extends T> targetClz;
    private Resource template;
    private WriteMode writeMode = WriteMode.NORMAL;

    @Override
    public void write(List<? extends T> items) {
        switch (writeMode) {
            case NORMAL:
                ensureSheetSize();
                writer.write(items, getCurSheet());
                break;
            case TEMPLATE:
                writer.write(items, getCurSheet());
                break;
            case FILL:
                writer.fill(items, getCurSheet());
                break;
            default:
                break;
        }
        linesWritten.addAndGet(items.size());
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        Assert.notNull(resource, "The resource must be set!");
        Assert.notNull(targetClz, "The target class must be set!");
        switch (writeMode) {
            case NORMAL:
                writer = EasyExcel.write(resource.getFile(), targetClz).build();
                break;
            case TEMPLATE:
            case FILL:
                Assert.notNull(template, "The template must be set!");
                Assert.isTrue(template.getFile().exists(), "The template file must exists!");
                writer = EasyExcel.write(resource.getFile(), targetClz).withTemplate(template.getFile()).build();
                break;
            default:
                break;
        }
        sheets = Collections.synchronizedList(new ArrayList<>());
        ensureSheetSize();
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
                curSheetIndex.incrementAndGet();
                WriteSheet writeSheet = EasyExcel.writerSheet(curSheetIndex.get(), sheetNamePrefix + "-" + curSheetIndex.get()).build();
                sheets.add(writeSheet);
            }
        }
    }

    private WriteSheet getCurSheet() {
        return sheets.get(curSheetIndex.get());
    }

    public enum WriteMode {
        NORMAL,
        TEMPLATE,
        FILL
    }

}
