package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
public class FileHandlerTest extends TestSupport {

    @Autowired
    FileHandler fileHandler;

    @Test
    public void testInitFile() throws Exception {
        fileHandler.initFile();
    }

    @Test
    public void testHandleFile() throws Exception {
        Map additional = new HashMap();
        additional.put("owner","public");
        additional.put("group","public");
        String filePath1 = "src/test/resources/一碗阳春面.docx";
        fileHandler.handleFile(filePath1, additional);
        String filePath2 = "src/test/resources/Hbase简介.pptx";
        fileHandler.handleFile(filePath2, additional);
        String filePath3 = "src/test/resources/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.doc";
        fileHandler.handleFile(filePath3, additional);
        String filePath4 = "src/test/resources/2-华东理工大学“大学生创新创业训练计划”项目中期检查报告（创新训练类）.doc";
        fileHandler.handleFile(filePath4, additional);
        String filePath5 = "src/test/resources/3-中期检查 项目所获成果.xls";
        fileHandler.handleFile(filePath5, additional);
        String filePath6 = "src/test/resources/4-中期检查 优秀项目推荐表.doc";
        fileHandler.handleFile(filePath6, additional);
        String filePath7 = "src/test/resources/5-中期检查 不合格项目整改方案和下阶段计划.doc";
        fileHandler.handleFile(filePath7, additional);
        String filePath8 = "src/test/resources/6-中期检查 评分表.doc";
        fileHandler.handleFile(filePath8, additional);
        String filePath9 = "src/test/resources/7-项目负责人变更申请表.doc";
        fileHandler.handleFile(filePath9, additional);
        String filePath10 = "src/test/resources/8-项目组成员变更申请表.doc";
        fileHandler.handleFile(filePath10, additional);
        String filePath11 = "src/test/resources/ESManual13.pdf";
        fileHandler.handleFile(filePath11, additional);
    }

}