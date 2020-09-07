package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 10:43
 * @package com.atguigu.demo.excel
 */
public class TestExcal {

    public static void main(String[] args) {

        //1.实现excel写操作
        //设置excel写后的
        String fileName = "D:\\write.xlsx";
        //2.调用easyExcel中的方法实现写操作
        //EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getList());
        //System.out.println("写入成功");

        //读
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getList(){
        List<DemoData> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("姓名"+i);

            list.add(demoData);
        }

        return list;
    }

}
