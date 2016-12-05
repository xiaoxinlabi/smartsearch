package info.puton.product.smartsearch.service.impl;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.service.IConvertService;
import info.puton.product.smartsearch.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by taoyang on 2015/11/25.
 */
@Service
public class ConvertServiceImpl implements IConvertService {

    /**
     * 判断文件是否存在
     * @param fileName
     * @return boolean.
     */
    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * doc转pdf
     * @param docPath
     * @param pdfPath
     * @return pdfPath.
     */
    @Override
    public void doc2pdf(String docPath, String pdfPath) throws IOException {

        File inputFile = new File(docPath);
        File outputFile = new File(pdfPath);

        if(FileUtil.getFileSuffix(docPath).toLowerCase().equals(Type.PDF)){

            FileUtils.copyFile(inputFile, outputFile);

        }else{

            // connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);

            // close the connection
            connection.disconnect();

        }

    }

}
