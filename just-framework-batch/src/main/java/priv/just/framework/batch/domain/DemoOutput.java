package priv.just.framework.batch.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ContentRowHeight(20)
@HeadRowHeight(30)
@ColumnWidth(10)
public class DemoOutput {

    @ExcelProperty("ID")
    private int id;

    @ColumnWidth(20)
    @ExcelProperty("日期")
    private String formatDate;

    @ColumnWidth(50)
    @ExcelProperty("信息")
    private String message;

}
