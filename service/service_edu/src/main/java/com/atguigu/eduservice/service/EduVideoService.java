package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
public interface EduVideoService extends IService<EduVideo> {

    //2.根据courseId删除小节 TODO 删除对应视频信息
    void deleteVideoByCourseId(String courseId);
}
