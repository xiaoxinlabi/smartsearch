package info.puton.product.smartsearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by taoyang on 2016/9/20.
 */
public class FileUtil {

    public static String getFullFileName(String filePath){
        File tempFile =new File(filePath.trim());
        String fullFileName = tempFile.getName();
        return fullFileName;
    }

    public static String getFileSuffix(String filePath){
        String fullFileName = getFullFileName(filePath);
        String[] units  = fullFileName.split("\\.");
        String suffix = units [units .length-1];
        return suffix;
    }

    public static String getFileName(String filePath){
        String fullFileName = getFullFileName(filePath);
        String suffix = getFileSuffix(filePath);
        String fileName = fullFileName.substring(0,fullFileName.length()-1-suffix.length());
        return fileName;
    }

    public static byte[] getFileBytes(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));//新建一个FileInputStream对象
        byte[] b = new byte[fis.available()];// 新建一个字节数组
        fis.read(b);// 将文件中的内容读取到字节数组中
        fis.close();
        return b;
    }

}
