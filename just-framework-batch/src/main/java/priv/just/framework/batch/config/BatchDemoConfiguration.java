package priv.just.framework.batch.config;

import com.alibaba.excel.util.DateUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import priv.just.framework.batch.domain.DemoInput;
import priv.just.framework.batch.domain.DemoOutput;
import priv.just.framework.batch.reader.EasyExcelItemReader;
import priv.just.framework.batch.writer.EasyExcelItemWriter;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class BatchDemoConfiguration {

    private static final Random random = new Random();

    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job demoJob(Step demoFirstStep) {
        return jobBuilderFactory
                .get("demoJob")
                .start(demoFirstStep)
                .build();
    }

    @Bean
    public Step demoFirstStep(ItemReader<DemoInput> easyExcelItemReader,
                              ItemProcessor<DemoInput, DemoOutput> demoInputOutputItemProcessor,
                              ItemWriter<DemoOutput> easyExcelItemWriter) {
        return stepBuilderFactory
                .get("demoFirstStep")
                .<DemoInput, DemoOutput>chunk(20)
                .reader(easyExcelItemReader)
                .processor(demoInputOutputItemProcessor)
                .writer(easyExcelItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public ItemReader<DemoInput> easyExcelItemReader(@Value("#{jobParameters['readPath']}") String readPath) {
        EasyExcelItemReader<DemoInput> easyExcelItemReader = new EasyExcelItemReader<>();
        easyExcelItemReader.setResource(new FileSystemResource(readPath));
        easyExcelItemReader.setTargetClz(DemoInput.class);
        return easyExcelItemReader;
    }

    @StepScope
    @Bean
    public ItemReader<DemoInput> demoInputItemReader() {
        DemoInputItemReader demoInputItemReader = new DemoInputItemReader();
        demoInputItemReader.setName("demoInputItemReader");
        demoInputItemReader.setPageSize(100);
        return demoInputItemReader;
    }

    @StepScope
    @Bean
    public ItemProcessor<DemoInput, DemoOutput> demoInputOutputItemProcessor() {
        return new DemoInputOutputItemProcessor();
    }

    @StepScope
    @Bean
    public ItemWriter<DemoOutput> easyExcelItemWriter(@Value("#{jobParameters['writePath']}") String writePath,
                                                      @Value("#{jobParameters['template']}") String template) {
        EasyExcelItemWriter<DemoOutput> easyExcelItemWriter = new EasyExcelItemWriter<>();
        easyExcelItemWriter.setResource(new FileSystemResource(writePath));
        easyExcelItemWriter.setTargetClz(DemoOutput.class);
        //easyExcelItemWriter.setWriteMode(EasyExcelItemWriter.WriteMode.TEMPLATE);
        //easyExcelItemWriter.setTemplate(new ClassPathResource(template));
        easyExcelItemWriter.setMaxSheetLines(500);
        return easyExcelItemWriter;
    }

    static class DemoInputItemReader extends AbstractPaginatedDataItemReader<DemoInput> {
        @Override
        protected Iterator<DemoInput> doPageRead() {
            if (page > 10) {
                return null;
            }
            return Stream.generate(() -> {
                DemoInput item = new DemoInput();
                item.setId(random.nextInt(10000));
                item.setDate(new Date());
                item.setUuid(UUID.randomUUID().toString());
                return item;
            }).limit(pageSize).iterator();
        }
    }

    static class DemoInputOutputItemProcessor implements ItemProcessor<DemoInput, DemoOutput> {
        @Override
        public DemoOutput process(DemoInput input) {
            DemoOutput output = new DemoOutput();
            output.setId(input.getId());
            output.setFormatDate(DateUtils.format(input.getDate()));
            output.setMessage(input.getUuid().replaceAll("-", "").toUpperCase());
            return output;
        }
    }

}
