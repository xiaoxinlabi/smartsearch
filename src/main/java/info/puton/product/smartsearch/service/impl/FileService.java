package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.exception.FileException;
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

    private String[] allFileTypes = {Type.DOC, Type.DOCX, Type.XLS, Type.XLSX, Type.PPT, Type.PPTX, Type.PDF, Type.TXT};

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
        if(Arrays.asList(allFileTypes).contains(suffix.toLowerCase())){
            FileFullText fileFullText = fileExtractor.extract(new File(filePath), additional);
            fileStorage.putFile(filePath, fileFullText.getId());
            fileIndexer.addFile(fileFullText);
            System.out.println("File handled. name:" + fileFullText.getFileName());
        } else {
            System.out.println("File ignored :" + filePath);
            throw new FileException("不支持该文件类型！");
        }

    }

}
