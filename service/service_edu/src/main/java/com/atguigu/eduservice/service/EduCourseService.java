package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程信息,包含其中的课程描述表所以需要VO对象
    CourseInfoVo getCourseInfoByCourseId(String courseId);

    //修改课程信息表
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //得到课程的全部信息CoursePublishVo
    CoursePublishVo getCoursePublish(String courseId);

    //根据courseId删除课程以及章节和小节
    void deleteCourseById(String courseId);
}
