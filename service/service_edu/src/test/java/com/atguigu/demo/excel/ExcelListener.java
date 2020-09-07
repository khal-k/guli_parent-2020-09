package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author 孔佳齐丶
 * @create 2020-09-07 11:03
 * @package com.atguigu.demo.excel
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("*******"+demoData);
    }

    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
