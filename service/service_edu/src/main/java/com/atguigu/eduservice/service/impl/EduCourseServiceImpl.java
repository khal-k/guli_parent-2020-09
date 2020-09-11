package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    //注入EduVideoService
    @Autowired
    private EduVideoService eduVideoService;

    //注入EduChapterService
    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.新建一个Course对象,并使用...赋值
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        //2.调用baseMapper 将eduCourse插入数据库
        int insert = baseMapper.insert(eduCourse);

        //3.判断是否插入成功
        if(insert<1){
            throw  new GuliException(20001,"课程对象插入失败");
        }

        //4.因为courseInfoVo有描述类的值,所有在插入描述类的时候需要将其一对一的课程类id插入到描述类中
        String cid = eduCourse.getId();

        //5,新建对象并赋值
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);

        //6.调用eduCourseDescriptionService将对象插入数据库
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    //根据课程id查询课程信息,包含其中的课程描述表所以需要VO对象
    @Override
    public CourseInfoVo getCourseInfoByCourseId(String courseId) {
        //1,根据courseId获取course对象
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //2.使用工具类
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //3.根据课程id获取描述表信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);

        //4.将描述信息表设置进courseInfoVo
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息表
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.使用工具类将CourseInfoVo转换为实体类
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        //2.调用baseMapper进行修改
        int i = baseMapper.updateById(eduCourse);
        if(i<1){
            throw new GuliException(20001,"修改课程信息失败");
        }

        //3.获取描述列的id和描述信息
        EduCourseDescription description = new EduCourseDescription();
        String id = courseInfoVo.getId();
        description.setId(id);
        description.setDescription(courseInfoVo.getDescription());

        //4.调用service方法,修改描述信息类
        boolean b = eduCourseDescriptionService.updateById(description);
    }

    //得到课程的全部信息CoursePublishVo
    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    //根据courseId删除课程以及章节和小节
    @Override
    public void deleteCourseById(String courseId) {
        //1.根据courseId删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //2.根据courseId删除小节
        eduVideoService.deleteVideoByCourseId(courseId);

        //3.删除描述
        eduCourseDescriptionService.deleteDescriptionInfo(courseId);

        //4.根据..删除课程信息
        baseMapper.deleteById(courseId);

    }
}
