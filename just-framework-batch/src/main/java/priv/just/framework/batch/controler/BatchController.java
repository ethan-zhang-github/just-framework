package priv.just.framework.batch.controler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.batch.domain.DemoOutput;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("batch")
public class BatchController {

    @Resource
    private Job demoJob;
    @Resource
    private JobLauncher jobLauncher;

    @RequestMapping("test")
    public void test() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(demoJob, new JobParametersBuilder()
                .addString("readPath", "E:\\demo\\read.xlsx")
                .addString("writePath", "E:\\demo\\write.xlsx")
                .toJobParameters());
    }

    public static void main(String[] args) throws IOException {
        org.springframework.core.io.Resource resource = new FileSystemResource("E:\\demo\\1.xlsx");
        org.springframework.core.io.Resource template = new FileSystemResource("E:\\demo\\demo_fill.xlsx");

        ExcelWriterBuilder writerBuilder = EasyExcel.write(resource.getFile(), DemoOutput.class).withTemplate(template.getFile());

        ExcelWriter writer = writerBuilder.build();
        WriteSheet sheet = writerBuilder.sheet(1).build();

        List<DemoOutput> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoOutput item = new DemoOutput();
            item.setId(1);
            item.setMessage(UUID.randomUUID().toString());
            item.setFormatDate(DateUtils.format(new Date()));
            list.add(item);
        }

        writer.write(list, sheet);
        writer.finish();
    }

}
