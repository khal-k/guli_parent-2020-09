package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getAllChapterVideo(String courseId);

    //删除章节信息
    boolean deleteChapterInfoByChapterId(String chapterId);

    //1.根据courseId删除章节
    void deleteChapterByCourseId(String courseId);
}
