package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 孔佳齐丶
 * @create 2020-09-05 15:09
 * @package com.atguigu.oss.service
 */
public interface OssService {
    String upLoadAvatar(MultipartFile file);
}
