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
import java.util.Map;
import java.util.UUID;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class TikaService implements FileExtractor {

    @Override
    public FileFullText extract(File file, Map additional) throws TikaException, SAXException, IOException {
        FileFullText fileFullText = new FileFullText();
        String fileKey = UUID.randomUUID().toString();
        fileFullText.setId(fileKey);//id
        fileFullText.setOwner((String) additional.get("owner"));//owner
        fileFullText.setGroup((String) additional.get("group"));//group
        fileFullText.setTimestamp(System.currentTimeMillis());//timestamp

        fileFullText.setFileName(file.getName());//fileName
        fileFullText.setType(FileUtil.getFileSuffix(file.getName()));//type
        fileFullText.setSize(file.length());//size
        ContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        InputStream stream = TikaInputStream.get(file.toPath());
        parser.parse(stream, handler, metadata);
            for (String name : metadata.names()) {
                System.out.println(name + ":" + metadata.get(name));
            }
        String lastModified = metadata.get(Property.get("Last-Modified"));
        String creationDate = metadata.get(Property.get("Creation-Date"));
        if(lastModified!=null){

        }else if(creationDate!=null){
            lastModified = creationDate;
        }else{

        }
        fileFullText.setLastModified(lastModified);//lastModified
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
