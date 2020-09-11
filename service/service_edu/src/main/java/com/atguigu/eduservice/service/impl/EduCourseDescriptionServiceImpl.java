package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //根据courseId删除描述信息
    @Override
    public void deleteDescriptionInfo(String courseId) {
        int i = baseMapper.deleteById(courseId);
    }
}
