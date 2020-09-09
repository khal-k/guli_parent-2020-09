package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description="章节管理")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //获取章节和小节信息
    @GetMapping("getAllChapterVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable("courseId") String courseId){
        //调用service查找所有章节和小结信息
        List<ChapterVo> list = eduChapterService.getAllChapterVideo(courseId);
        return R.ok().data("allChapterVideoList",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //查询章节信息
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter byId = eduChapterService.getById(chapterId);
        return R.ok().data("chapterInfo",byId);
    }

    //修改章节信息
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节信息
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b = eduChapterService.deleteChapterInfoByChapterId(chapterId);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

