package priv.just.framework.batch.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoOutput {

    @ExcelProperty("ID")
    private int id;

    @ExcelProperty("日期")
    private String formatDate;

    @ExcelProperty("信息")
    private String message;

}
