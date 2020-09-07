package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 孔佳齐丶
 * @create 2020-09-05 15:09
 * @package com.atguigu.oss.controller
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin    //解决跨域问题
public class OssController {
    @Autowired
    private OssService ossService;

    //上传的方法
    @PostMapping
    public R uploadFile(MultipartFile file){
        //获取到上传的文件
        String url = ossService.upLoadAvatar(file);

        //返回上传的路径
        return R.ok().data("url",url);
    }
}
