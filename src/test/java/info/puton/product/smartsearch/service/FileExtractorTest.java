package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.FileFullText;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/8.
 */
public class FileExtractorTest extends TestSupport {

    @Autowired
    FileExtractor fileExtractor;

    @Test
    public void testExtract() throws Exception {
        Map additional = new HashMap();
//        String filePath = "src/test/resources/段落.txt";
        String filePath = "src/test/resources/HyperbaseManual_T00146x-04-012_2016-08-26.pdf";
//        String filePath = "src/test/resources/ESManual13.pdf";
//        String filePath = "src/test/resources/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.doc";
//        String filePath = "src/test/resources/3-中期检查 项目所获成果.xls";
        FileFullText fileFullText = fileExtractor.extract(new File(filePath), additional);
        System.out.println(fileFullText);
    }
}