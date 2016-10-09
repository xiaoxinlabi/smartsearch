package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by taoyang on 2016/9/21.
 */
public class SmartSearchHandlerTest extends TestSupport {

    @Autowired
    SmartSearchHandler smartSearchHandler;

    @Test
    public void testInitFile() throws Exception {
        smartSearchHandler.initFile();
    }

    @Test
    public void testHandleFile() throws Exception {
        String filePath1 = "src/test/resources/一碗阳春面.docx";
        smartSearchHandler.handleFile(filePath1);
        String filePath2 = "src/test/resources/Hbase简介.pptx";
        smartSearchHandler.handleFile(filePath2);
        String filePath3 = "src/test/resources/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.doc";
        smartSearchHandler.handleFile(filePath3);
        String filePath4 = "src/test/resources/2-华东理工大学“大学生创新创业训练计划”项目中期检查报告（创新训练类）.doc";
        smartSearchHandler.handleFile(filePath4);
        String filePath5 = "src/test/resources/3-中期检查 项目所获成果.xls";
        smartSearchHandler.handleFile(filePath5);
        String filePath6 = "src/test/resources/4-中期检查 优秀项目推荐表.doc";
        smartSearchHandler.handleFile(filePath6);
        String filePath7 = "src/test/resources/5-中期检查 不合格项目整改方案和下阶段计划.doc";
        smartSearchHandler.handleFile(filePath7);
        String filePath8 = "src/test/resources/6-中期检查 评分表.doc";
        smartSearchHandler.handleFile(filePath8);
        String filePath9 = "src/test/resources/7-项目负责人变更申请表.doc";
        smartSearchHandler.handleFile(filePath9);
        String filePath10 = "src/test/resources/8-项目组成员变更申请表.doc";
        smartSearchHandler.handleFile(filePath10);
    }

}