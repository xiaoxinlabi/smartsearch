package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.FileFullText;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/8.
 */
public class FileExtractorTest extends TestSupport {

    @Autowired
    FileExtractor fileExtractor;

    @Test
    public void testExtract() throws Exception {
        String filePath3 = "src/test/resources/1-关于开展2014年度“大学生创新创业训练计划”项目中期检查的通知.doc";
        FileFullText fileFullText = fileExtractor.extract(new File(filePath3));
        System.out.println(fileFullText);
    }
}