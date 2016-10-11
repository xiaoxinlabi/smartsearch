package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
import info.puton.product.smartsearch.service.FileIndexer;
import info.puton.product.smartsearch.service.FileStorage;
import info.puton.product.smartsearch.service.SmartSearchHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

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
    public void initFile() throws Exception {
        fileStorage.initFile();
        fileIndexer.initFile();
    }

    @Override
    public void handleFile(String filePath, Map additional) throws Exception {
        FileFullText fileFullText = fileExtractor.extract(new File(filePath), additional);
        fileStorage.putFile(filePath, fileFullText.getId());
        fileIndexer.addFile(fileFullText);
        System.out.println("File handled. name:" + fileFullText.getFileName());
    }

    @Override
    public void handleAddress(Address address) throws Exception {

    }
}
