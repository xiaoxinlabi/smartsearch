package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
import info.puton.product.smartsearch.service.SmartSearchHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class SmartSearchService implements SmartSearchHandler {

    @Autowired
    FileExtractor fileExtractor;

    @Override
    public void handleFile() {

        String filePath = "D:/programming/java/product/smartsearch/src/test/resources/一碗阳春面.docx";

        FileFullText fileFullText = fileExtractor.extract(new File(filePath));

        System.out.println(fileFullText);

    }
}
