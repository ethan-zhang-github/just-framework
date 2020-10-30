package priv.just.framework.batch.reader;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * EasyExcel 扩展 Spring Batch {@link ItemReader}
 * 支持多线程并发读取
 * 支持读取 excel 文件中的多个 sheet
 * 支持根据偏移量 {@link #sheetIndexes} 或名称 {@link #sheetNames} 过滤 sheet
 * @param <T> 目标读取类型
 * @author Ethan Zhang
 */
@Slf4j
@Getter
@Setter
@ThreadSafe
public class EasyExcelItemReader<T> implements ItemReader<T>, InitializingBean, DisposableBean {

    private Resource resource;
    private Class<? extends T> targetClz;
    private EasyExcelItemReaderListener listener;
    private BlockingQueue<T> buffer;
    private int bufferSize = Integer.MAX_VALUE;
    private ExcelReader reader;
    private boolean finished;
    private AtomicInteger linesReaded = new AtomicInteger(0);
    private Set<Integer> sheetIndexes = new HashSet<>();
    private Set<String> sheetNames = new HashSet<>();
    private AtomicInteger sheetNum;

    @Override
    public T read() throws InterruptedException {
        if (finished && buffer.size() == 0) {
            return null;
        }
        linesReaded.incrementAndGet();
        return buffer.poll(1, TimeUnit.MINUTES);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resource, "The resource must be set!");
        Assert.notNull(targetClz, "The target class must be set!");
        buffer = new LinkedBlockingQueue<>(bufferSize);
        listener = new EasyExcelItemReaderListener();
        reader = EasyExcel.read(resource.getFile(), targetClz, listener).build();
        List<ReadSheet> readSheets = reader.excelExecutor().sheetList();
        if (CollectionUtils.isNotEmpty(sheetIndexes) || CollectionUtils.isNotEmpty(sheetNames)) {
            readSheets = readSheets.stream()
                    .filter(readSheet -> sheetIndexes.contains(readSheet.getSheetNo())
                            || sheetNames.contains(readSheet.getSheetName()))
                    .collect(Collectors.toList());
        }
        Assert.notEmpty(readSheets, "The resource file must have at least 1 sheet!");
        sheetNum = new AtomicInteger(readSheets.size());
        reader.read(readSheets);
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(reader)) {
            reader.finish();
        }
    }

    private class EasyExcelItemReaderListener extends AnalysisEventListener<T> {

        @Override
        public void invoke(T data, AnalysisContext context) {
            try {
                buffer.put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            if (sheetNum.decrementAndGet() == 0) {
                finished = true;
            }
        }

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            log.error("EasyExcelItemReaderListener execute error!", exception);
            finished = true;
            throw exception;
        }
        
    }

}
