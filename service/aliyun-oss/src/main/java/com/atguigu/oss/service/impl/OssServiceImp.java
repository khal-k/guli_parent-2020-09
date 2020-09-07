package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 孔佳齐丶
 * @create 2020-09-05 15:09
 * @package com.atguigu.oss.service.impl
 */
@Service
public class OssServiceImp implements OssService {

    /**
     * 实现上传文件到
     * @param file
     * @return
     */
    @Override
    public String upLoadAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //调用oss的方法实现上传
            //获取文件名称
            //设置上传到oss文件夹名

            //设置上传文件名称前缀
            String fileUUIDName = UUID.randomUUID().toString().replace("-", "");

            //得到文件名称后缀
            String extensionName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            //拼接文件名称和后缀名
            String proFileName = fileUUIDName+extensionName;

            String folderName = new DateTime().toString("yyyy/MM/dd");
            //String originalFilename = file.getOriginalFilename();
            String objectName = folderName + "/" + proFileName;

            ossClient.putObject(bucketName, objectName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //上传之后的路径返回
            //https://kong-scwproject-2020-8-29.oss-cn-beijing.aliyuncs.com/20200829/cf049463d9ad4a17bc8c4fdca481e6ae.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+objectName;
            //手动拼写路径

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}













