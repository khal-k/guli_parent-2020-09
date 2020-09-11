package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;



import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-09 22:03
 * @package com.atguigu.vodtest
 */
public class Test {
    public static void main(String[] args) throws ClientException {
        //根据视频id获取视频播放凭证
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GEvURKFYLQL1MApFYVN"
                ,"9Z71vgaP7ku07LhE1znrX4R7o3cPsq");
       //System.out.println(client);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向Request对象里面设置视频id
        request.setVideoId("642452d42e4f46999fce3d23bece4826");

        //调用初始化对象里面的方法,传递request,获取数据
        response = client.getAcsResponse(request);

        System.out.println(response.getPlayAuth());

    }

    /*public static void getPlayUrl() throws Exception{
        //1.根据视频ID获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GEvURKFYLQL1MApFYVN"
                ,"9Z71vgaP7ku07LhE1znrX4R7o3cPsq");

        //创建获取视频地址request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse response = new com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse();

        //向Request对象里面设置视频id
        request.setVideoId("642452d42e4f46999fce3d23bece4826");

        //调用初始化对象里面的方法,传递request,获取数据
        response = client.getAcsResponse(request);


        List<com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }*/
}


















