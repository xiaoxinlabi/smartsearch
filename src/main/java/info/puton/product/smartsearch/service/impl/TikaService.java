package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileExtractor;
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
    public FileFullText extract(File file) {
        FileFullText fileFullText = new FileFullText();
        String fileKey = UUID.randomUUID().toString();
        fileFullText.setFileKey(fileKey);//fileKey
        fileFullText.setFileName(file.getName());//fileName
        ContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try {
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
            fileFullText.setContent(content);//content
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileFullText;
    }
}
