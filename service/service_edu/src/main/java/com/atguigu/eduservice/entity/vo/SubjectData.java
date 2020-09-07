package com.atguigu.eduservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 11:41
 * @package com.atguigu.eduservice.entity
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private int oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
