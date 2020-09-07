package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 10:22
 * @package com.atguigu.demo.excel
 */
@Data
public class DemoData {
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名" , index = 1)
    private String sname;
}
