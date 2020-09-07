package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.ConverterUtils;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 11:49
 * @package com.atguigu.eduservice.listener
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //SubjectExcelListener不能交个spring进行管理,需要自己new,不能注入其他对象
    //不能实现数据库操作
    //创建有参无参构造方法,在调用时传入当前正在操作的service
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容,一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }

        //1.一行一行读取,每次读取有两个值,第一个值一级分类,第二个值二级分类
        //读取的内容调用方法判断为一级还是二级
        EduSubject existOneSubject = this.exitOneSubject(subjectService,subjectData.getOneSubjectName());
        //获取的为空时,证明可以添加且不重复,则将其保存在...中
        if(existOneSubject==null){
            //2.新建当前科目对象
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            //3.保存
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        //判断是否为二级
        EduSubject existTwoSubject = this.exitTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if(existTwoSubject==null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            //保存
            subjectService.save(existTwoSubject);
        }
    }

    //判读一级分类不能重置添加
    private EduSubject exitOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject exitTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        this.invokeHeadMap(ConverterUtils.convertToStringMap(headMap, context), context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
