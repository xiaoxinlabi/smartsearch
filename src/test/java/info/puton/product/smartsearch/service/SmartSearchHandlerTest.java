package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.FileFullText;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by taoyang on 2016/9/21.
 */
public class SmartSearchHandlerTest extends TestSupport {

    @Autowired
    FileExtractor fileExtractor;

    @Autowired
    FileIndexer fileIndexer;

    @Autowired
    FileStorage fileStorage;

    @Test
    public void testHandleFile() throws Exception {

        String filePath = "D:/programming/java/product/smartsearch/src/test/resources/一碗阳春面.docx";

        FileFullText fileFullText = fileExtractor.extract(new File(filePath));

        System.out.println(fileFullText);

    }
}