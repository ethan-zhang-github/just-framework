package priv.just.framework.batch.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DemoInput {

    @ExcelProperty("ID")
    private int id;

    @ExcelProperty("日期")
    private Date date;

    @ExcelProperty("信息")
    private String uuid;

}
