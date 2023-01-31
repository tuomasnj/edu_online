package com.onlineEdu.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    @Test
    public void write() {
        //实现Excel的写入操作
        String file = "C:\\Users\\admin\\Desktop\\write.xlsx";
        List<Dataing> stuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Dataing data = new Dataing();
            data.setSno(i);
            data.setSname("小明"+i);
            stuList.add(data);
        }
        EasyExcel.write(file, Dataing.class).sheet("学生表").doWrite(stuList);
    }

    @Test
    public void read(){
        String file = "C:\\Users\\admin\\Desktop\\write.xlsx";
        EasyExcel.read(file, Dataing.class,new ExcelListener()).sheet("学生表").doRead();
    }
}
