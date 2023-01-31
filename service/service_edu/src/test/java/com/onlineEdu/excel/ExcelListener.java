package com.onlineEdu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;
import java.util.Set;

public class ExcelListener extends AnalysisEventListener<Dataing> {
    @Override
    public void invoke(Dataing dataing, AnalysisContext analysisContext) {
        System.out.println("*****" + dataing);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Set<Integer> key = headMap.keySet();
        for (Integer i : key) {
            System.out.print("===="+i);
            System.out.print("===="+headMap.get(i));
            System.out.println();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
