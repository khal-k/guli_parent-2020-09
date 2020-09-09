package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //执行保存课程信息
    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        //1.调用eduCourseService执行课程保存方法,courseInfoVo
        //并返回当前保存的Id
        String id = eduCourseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程信息,包含其中的课程描述表所以需要VO对象
    @GetMapping("getCourseInfoByCourseId/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息表
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //得到课程的全部信息CoursePublishVo
    @GetMapping("getCoursePublish/{courseId}")
    public R getCoursePublish(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublish(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }


}

