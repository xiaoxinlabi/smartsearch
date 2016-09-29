package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
import info.puton.product.smartsearch.service.FileIndexer;
import info.puton.product.smartsearch.service.FileStorage;
import info.puton.product.smartsearch.service.SmartSearchHandler;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class SmartSearchService implements SmartSearchHandler {

    @Autowired
    FileExtractor fileExtractor;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    FileIndexer fileIndexer;

    @Override
    public void initSearch() throws Exception {
        fileStorage.initStorage();
        fileIndexer.initIndex();
    }

    @Override
    public void handleFile(String filePath) throws Exception {
        FileFullText fileFullText = null;
        fileFullText = fileExtractor.extract(new File(filePath));
        fileStorage.putFile(filePath, fileFullText.getId());
        fileIndexer.createDocument(fileFullText);
        System.out.println("File handled. name:" + fileFullText.getFileName());
    }
}
