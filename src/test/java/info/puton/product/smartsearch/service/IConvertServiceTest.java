package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/11.
 */
public class IConvertServiceTest extends TestSupport {

    @Autowired
    IConvertService convertService;

    @Test
    public void testDoc2pdf() throws Exception {

       String docPath = "src/test/resources/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.doc";
       String pdfPath = "E:/tmp/yang/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.pdf";

        convertService.doc2pdf(docPath, pdfPath);

    }
}