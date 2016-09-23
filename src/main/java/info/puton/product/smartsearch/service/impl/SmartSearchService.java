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
    public void handleFile() {

        String filePath = "D:/programming/java/product/smartsearch/src/test/resources/一碗阳春面.docx";

        FileFullText fileFullText = null;
        try {
            fileFullText = fileExtractor.extract(new File(filePath));
            fileStorage.putFile(filePath, fileFullText.getId());
            fileIndexer.createIndex(fileFullText);
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("File handled. name:" +fileFullText.getFileName());

    }
}
