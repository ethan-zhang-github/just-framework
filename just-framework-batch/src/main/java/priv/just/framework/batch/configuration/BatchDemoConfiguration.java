package priv.just.framework.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import priv.just.framework.batch.domain.DemoInput;
import priv.just.framework.batch.domain.DemoOutput;
import priv.just.framework.batch.processor.DemoInputOutputItemProcessor;
import priv.just.framework.batch.reader.DemoInputItemReader;
import priv.just.framework.batch.reader.EasyExcelItemReader;
import priv.just.framework.batch.writer.DemoOutputItemWriter;
import priv.just.framework.batch.writer.EasyExcelItemWriter;

import javax.annotation.Resource;

@Configuration
public class BatchDemoConfiguration {

    @Resource
    private ThreadPoolTaskExecutor batchTaskExecutor;

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
    public Job parallelJob(Step demoSecondStep) {
        return jobBuilderFactory
                .get("parallelJob")
                .start(demoSecondStep)
                .build();
    }

    @Bean
    public Step demoFirstStep(EasyExcelItemReader<DemoInput> easyExcelItemReader,
                              DemoInputOutputItemProcessor demoInputOutputItemProcessor,
                              EasyExcelItemWriter<DemoOutput> easyExcelItemWriter) {
        return stepBuilderFactory
                .get("demoFirstStep")
                .<DemoInput, DemoOutput>chunk(10)
                .reader(easyExcelItemReader)
                .processor(demoInputOutputItemProcessor)
                .writer(easyExcelItemWriter)
                .taskExecutor(batchTaskExecutor)
                .throttleLimit(batchTaskExecutor.getMaxPoolSize())
                .build();
    }

    @Bean
    public Step demoSecondStep(DemoInputItemReader demoInputItemReader,
                               DemoInputOutputItemProcessor demoInputOutputItemProcessor,
                               EasyExcelItemWriter<DemoOutput> easyExcelItemWriter) {
        return stepBuilderFactory
                .get("demoSecondStep")
                .<DemoInput, DemoOutput>chunk(500)
                .reader(demoInputItemReader)
                .processor(demoInputOutputItemProcessor)
                .writer(easyExcelItemWriter)
                .taskExecutor(batchTaskExecutor)
                .throttleLimit(batchTaskExecutor.getMaxPoolSize())
                .build();
    }

    @StepScope
    @Bean
    public EasyExcelItemReader<DemoInput> easyExcelItemReader(@Value("#{jobParameters['readPath']}") String readPath) {
        EasyExcelItemReader<DemoInput> easyExcelItemReader = new EasyExcelItemReader<>();
        easyExcelItemReader.setResource(new FileSystemResource(readPath));
        easyExcelItemReader.setTargetClz(DemoInput.class);
        return easyExcelItemReader;
    }

    @StepScope
    @Bean
    public DemoInputItemReader demoInputItemReader() {
        DemoInputItemReader demoInputItemReader = new DemoInputItemReader();
        demoInputItemReader.setName("demoInputItemReader");
        demoInputItemReader.setPageSize(1000);
        demoInputItemReader.setTotalPage(100);
        return demoInputItemReader;
    }

    @StepScope
    @Bean
    public DemoInputOutputItemProcessor demoInputOutputItemProcessor() {
        return new DemoInputOutputItemProcessor();
    }

    @StepScope
    @Bean
    public EasyExcelItemWriter<DemoOutput> easyExcelItemWriter(@Value("#{jobParameters['writePath']}") String writePath,
                                                               @Value("#{jobParameters['template']}") String template) {
        EasyExcelItemWriter<DemoOutput> easyExcelItemWriter = new EasyExcelItemWriter<>();
        easyExcelItemWriter.setResource(new FileSystemResource(writePath));
        easyExcelItemWriter.setTargetClz(DemoOutput.class);
        easyExcelItemWriter.setMaxSheetLines(10000);
        return easyExcelItemWriter;
    }

    @StepScope
    @Bean
    public DemoOutputItemWriter demoOutputItemWriter() {
        return new DemoOutputItemWriter();
    }

}
