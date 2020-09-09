package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
@Api(description = "视频")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //添加小节
    @PostMapping("addVideoInfo")
    public R addVideoInfo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除 TODO 需要在删除的时候将视频也删除掉
    @DeleteMapping("deleteVideoInfo/{videoId}")
    public R deleteVideoInfo(@PathVariable String videoId){
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    //查
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo byId = eduVideoService.getById(videoId);
        return R.ok().data("videoInfo",byId);
    }

    //改
    @PostMapping("updateVideoInfo")
    public R updateVideoInfo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

