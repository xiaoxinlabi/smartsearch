package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.dao.HBaseDao;
import info.puton.product.smartsearch.service.HBaseFileOperator;
import info.puton.product.smartsearch.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by taoyang on 2016/9/20.
 */
@Service
public class HBaseService implements HBaseFileOperator {

    @Autowired
    HBaseDao hbd;

    static class TABLE {
        static String FILE = "file_tb";
    }

    static class COLUMN_FAMILY {
        static String FILE = "file_cf";
    }

    static class COLUMN_QUALIFIER {
        static String NAME = "name";
        static String TYPE = "type";
        static String DATA = "data";
    }

    public void deleteTable() throws Exception {
        hbd.deleteTable(TABLE.FILE);
    }

    public void createTable() throws Exception {
        hbd.createTable(TABLE.FILE, COLUMN_FAMILY.FILE);
    }

    public void putFile(String filePath, String key) throws Exception {
//        HBaseDao.createTable(TABLE.FILE, COLUMN_FAMILY.FILE);
        String name = FileUtil.getFileName(filePath);
        String type = FileUtil.getFileSuffix(filePath);
        byte[] data = FileUtil.getFileBytes(filePath);
        hbd.putCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.NAME, name);
        hbd.putCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.TYPE, type);
        hbd.putCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.DATA, data);
    }

    public void getFile(String fileLocation, String key) throws Exception {
        String name = hbd.getStringCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.NAME);
        String type = hbd.getStringCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.TYPE);
        byte[] data = hbd.getBytesCell(TABLE.FILE, key, COLUMN_FAMILY.FILE, COLUMN_QUALIFIER.DATA);
        String filePath = fileLocation + "/" + name + "." + type;
        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file, data);
    }

    public static void main(String[] args) {
        try {

            HBaseFileOperator hbfo = new HBaseService();

//            HBaseFileStore.deleteTable();

            String filePath = "D:/programming/java/practice/hbase-practice/src/main/resources/一碗阳春面.docx";

//            putFile(filePath, "a00002");

            hbfo.getFile("E:/tmp/yang", "a00002");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
