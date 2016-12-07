package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
import info.puton.product.smartsearch.service.FileIndexer;
import info.puton.product.smartsearch.service.FileStorage;
import info.puton.product.smartsearch.service.FileHandler;
import info.puton.product.smartsearch.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class FileService implements FileHandler {

    @Autowired
    FileExtractor fileExtractor;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    FileIndexer fileIndexer;

    @Override
    public void initFile() throws Exception {
        fileIndexer.initFile();
        fileStorage.initFile();
    }

    @Override
    public void handleFile(String filePath, Map additional) throws Exception {
        String suffix = FileUtil.getFileSuffix(filePath);
        FileFullText fileFullText = fileExtractor.extract(new File(filePath), additional);
        fileStorage.putFile(filePath, fileFullText.getId());
        fileIndexer.addFile(fileFullText);
        System.out.println("File handled. Name: " + fileFullText.getFileName());
    }

}
