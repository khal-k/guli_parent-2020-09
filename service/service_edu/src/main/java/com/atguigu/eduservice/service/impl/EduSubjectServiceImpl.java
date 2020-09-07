package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.entity.vo.SubjectData;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //1.的到文件的输入流
            InputStream inputStream = file.getInputStream();
            //2.读取添加在监听器中
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubjectWithChildren() {
        //1.获取所有一级集合
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        //①调用mp封装好的baseMapper
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

        //2.获取所欲二级集合
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        //3.设置一个总的集合,用于返回
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //4.封装一级集合
        //遍历一级集合
        for (EduSubject eduSubject : oneSubjectList) {
            //①创建oneSubject对象,用来接收一级集合中的title和id值
            OneSubject oneSubject = new OneSubject();

            //②,使用UtilsBean方法,将源对象赋值给目标对象
            BeanUtils.copyProperties(eduSubject, oneSubject);

            //③,将赋值后的目标对象存入总的集合
            finalSubjectList.add(oneSubject);

            //5.封装二级集合
            //先新建一个二级的集合
            List<TwoSubject> twoSubjectsList = new ArrayList<>();

            //①,遍历二级集合
            for (int i = 0; i < twoSubjectList.size(); i++) {
                //②,得到二级集合中每个对象
                EduSubject twoFinalSubject = twoSubjectList.get(i);
                //③.判断该集合中的pid与当前正在遍历的一级集合对象的id是否相等
                if(Objects.equals(eduSubject.getId(), twoFinalSubject.getParentId())){
                    //④,如果相等,则将twoFinalSubject使用工具类赋值个TwoSubject
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoFinalSubject,twoSubject);
                    //⑤,将该一级集合的字对象存入到twoSubjectsList
                    twoSubjectsList.add(twoSubject);
                }
            }
            //⑥,存入最终集合
            oneSubject.setChildrenList(twoSubjectsList);
        }

        //5.封装二级集合---------------failed
        //①,二级集合
        /*for (EduSubject eduSubject : twoSubjectList) {
            //①创建twoSubject对象,用来接收二级集合中的title和id值
            TwoSubject twoSubject = new TwoSubject();

            //②,使用UtilsBean方法,将源对象赋值给目标对象
            //BeanUtils.copyProperties(eduSubject, twoSubject);
            //初始化一个二级集合
            List<TwoSubject> twoChildrenList = new ArrayList<>();

            OneSubject oneSubjectFinal = new OneSubject();

            //③,再次遍历存入一级集合的最终集合
            for (OneSubject oneSubject : finalSubjectList) {

                //④,判断当前赋值的二级目标对象的pid是否为一级的id
                if(Objects.equals(eduSubject.getParentId(), oneSubject.getId())){

                    //⑤,如果相等,则将其放入转换为twoSubject对象后存入
                    BeanUtils.copyProperties(eduSubject, twoSubject);
                    twoChildrenList.add(twoSubject);
                }
            }
            //⑥,将二级集合存入到该一级集合下的属性
            oneSubjectFinal.setChildrenList(twoChildrenList);
            finalSubjectList.add(oneSubjectFinal);
        }*/

        return finalSubjectList;
    }
}
