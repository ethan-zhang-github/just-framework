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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * EasyExcel 扩展 Spring Batch {@link ItemWriter}
 * 支持三种写入模式
 * 常规 {@link WriteMode#NORMAL}
 * 模块 {@link WriteMode#TEMPLATE}
 * 填充 {@link WriteMode#FILL}
 * 支持数据分片写入多个工作表 {@link #sheets}
 * 每个工作表最多写入行数 {@link #maxSheetLines}
 * 一个工作表写满后 {@link #ensureSheetSize()} 自动扩容
 * @param <T> 目标读取类型
 */
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
    private AtomicReference<WriteSheet> curSheet = new AtomicReference<>();

    @Override
    public synchronized void write(List<? extends T> items) {
        switch (writeMode) {
            case NORMAL:
                ensureSheetSize();
                writer.write(items, curSheet.get());
                break;
            case TEMPLATE:
                writer.write(items, curSheet.get());
                break;
            case FILL:
                writer.fill(items, curSheet.get());
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
        sheets = new ArrayList<>();
        ensureSheetSize();
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(writer)) {
            writer.finish();
        }
    }

    private void ensureSheetSize() {
        if (linesWritten.get() >= sheets.size() * maxSheetLines) {
            synchronized (this) {
                if (linesWritten.get() >= sheets.size() * maxSheetLines) {
                    curSheetIndex.incrementAndGet();
                    WriteSheet writeSheet = EasyExcel.writerSheet(curSheetIndex.get(), sheetNamePrefix + "-" + curSheetIndex.get()).build();
                    sheets.add(writeSheet);
                    curSheet.set(sheets.get(curSheetIndex.get()));
                }
            }
        }
    }

    public enum WriteMode {
        NORMAL,
        TEMPLATE,
        FILL
    }

}
