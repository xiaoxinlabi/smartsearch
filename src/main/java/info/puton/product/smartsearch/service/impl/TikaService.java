package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
import info.puton.product.smartsearch.util.FileUtil;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class TikaService implements FileExtractor {

    @Override
    public FileFullText extract(File file) throws TikaException, SAXException, IOException {
        FileFullText fileFullText = new FileFullText();
        String fileKey = UUID.randomUUID().toString();
        fileFullText.setId(fileKey);//id
        fileFullText.setFileName(file.getName());//fileName
        fileFullText.setType(FileUtil.getFileSuffix(file.getName()));//type
        ContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        InputStream stream = TikaInputStream.get(file.toPath());
        parser.parse(stream, handler, metadata);
//            for (String name : metadata.names()) {
//                System.out.println(name + ":" + metadata.get(name));
//            }
        String author = metadata.get("Author");//author
        fileFullText.setAuthor(author);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = metadata.getDate(Property.get("Last-Modified"));
        String lastModified = sdf.format(date);
        fileFullText.setModifyDate(lastModified);//modifyDate
        String content = handler.toString();
        content = content
                .replaceAll("\\n+", " ")
                .replaceAll("\\r+", " ")
                .replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ");
        fileFullText.setContent(content);//content
        System.out.println("Metadata extracted. fileKey:" + fileKey);
        return fileFullText;
    }
}
