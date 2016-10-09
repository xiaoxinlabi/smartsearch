package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.FileFullText;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileExtractor {

    FileFullText extract(File file, Map additional) throws TikaException, SAXException, IOException;

}
