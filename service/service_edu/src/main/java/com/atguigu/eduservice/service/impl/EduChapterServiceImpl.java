package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.QueryResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getAllChapterVideo(String courseId) {
        //1.查询所以章节信息
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();

        //①,根据courseId查询所有信息
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapper);

        //2.查询所有video小节信息
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        //3.新建一个章节信息列表用来存放最终的chapterVo信息
        List<ChapterVo> finalChapterList = new ArrayList<>();

        //4.封装章节信息列表
        for (EduChapter eduChapter : eduChaptersList) {
            //①,使用工具类将每个章节信息转换为ChapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            //②,将章节信息存入最终集合中
            finalChapterList.add(chapterVo);

            //③,获取当前的章节信息的id用于找到对应的小节对象
            String chapterId = eduChapter.getId();

            //5.封装小节信息列表

            //先新建一个videoVo的list集合
            List<VideoVo> videoVoList = new ArrayList<>();

            //①,先遍历所有的video小节信息列表
            for (int i = 0; i < eduVideoList.size(); i++) {
                //②,判断当前遍历的小节信息是否是当前chapter中的
                EduVideo eduVideo =eduVideoList.get(i);
                if(Objects.equals(chapterId, eduVideo.getChapterId())){
                    //③,是,则使用工具类转换为VO对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //④,将赋值后的videoVo添加到videoVoList中
                    videoVoList.add(videoVo);
                }
            }
            //⑤,将videoVoList存入到当前在遍历的chapterVo中
            chapterVo.setChildren(videoVoList);
        }
        return finalChapterList;
    }

    //删除章节信息
    @Override
    public boolean deleteChapterInfoByChapterId(String chapterId) {
        //1.查找小节信息
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        //2.使用count查找
        int count = eduVideoService.count(wrapper);
        //3.判断章节信息下是否存在小节信息
        if(count>0){
            throw new GuliException(20001,"不能删除");
        }
        int i = baseMapper.deleteById(chapterId);
        //判断删除结果并返回
        return i>0;
    }
}
