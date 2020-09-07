package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 16:26
 * @package com.atguigu.eduservice.entity.subject
 */
@Data
//一级分类
public class OneSubject {
    private String id;
    private String title;

    //初始化,如果没有创建为空值的集合
    private List<TwoSubject> childrenList = new ArrayList<>();
}
