package info.puton.product.smartsearch.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by taoyang on 2016/9/19.
 */
@Repository
public class HBaseDao {

    Configuration conf;

    public HBaseDao() {
        Configuration HBASE_CONFIG = new Configuration();
        HBASE_CONFIG.addResource("hadoop/core-site.xml");
        HBASE_CONFIG.addResource("hadoop/hdfs-site.xml");
        HBASE_CONFIG.addResource("hadoop/hbase-site.xml");
        conf = HBaseConfiguration.create(HBASE_CONFIG);
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(
                    "hstdh",
                    "hadoop/hstdh.keytab");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create a table :table_name(columnFamily)
     * @param tableName
     * @param columnFamily
     * @throws Exception
     */
    public void createTable(String tableName, String columnFamily) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if(admin.tableExists(tableName)) {
            System.out.println("Table exists!");
            System.exit(0);
        }
        else {
            if(tableName.contains(":")){
                String[] arr = tableName.split(":");
                String namespace = arr[0];
                try{
                    admin.createNamespace(NamespaceDescriptor.create(namespace).build());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDesc);
            System.out.println("Table created. tableName:" + tableName);
        }
        admin.close();
    }

    /**
     * delete table ,caution!!!!!! ,dangerous!!!!!!
     * @param tableName
     * @return
     * @throws Exception
     */
    public boolean deleteTable(String tableName) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if(admin.tableExists(tableName)) {
            try {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                System.out.println("Table deleted. tableName:" + tableName);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                admin.close();
                return false;
            }
        }
        admin.close();
        return true;
    }

    /**
     * put a cell data into a row identified by rowKey,columnFamily,qualifier
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param data
     * @throws Exception
     */
    public void putCell(HTable table, String rowKey, String columnFamily, String qualifier, byte[] data) throws Exception{
        Put p1 = new Put(Bytes.toBytes(rowKey));
        p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), data);
        table.put(p1);
        System.out.println("Cell put. rowKey:" + rowKey);
    }

    /**
     * put a cell data into a row identified by rowKey,columnFamily,qualifier
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param data
     * @throws Exception
     */
    public void putCell(HTable table, String rowKey, String columnFamily, String qualifier, String data) throws Exception{
        putCell(table, rowKey, columnFamily, qualifier, Bytes.toBytes(data));
    }

    /**
     * put a cell data into a row identified by rowKey,columnFamily,qualifier
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param data
     * @throws Exception
     */
    public void putCell(String tableName, String rowKey, String columnFamily, String qualifier, byte[] data) throws Exception{
        HTable table = new HTable(conf, tableName);
        putCell(table, rowKey, columnFamily, qualifier, data);
    }

    /**
     * put a cell data into a row identified by rowKey,columnFamily,qualifier
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param qualifier
     * @param data
     * @throws Exception
     */
    public void putCell(String tableName, String rowKey, String columnFamily, String qualifier, String data) throws Exception{
        putCell(tableName, rowKey, columnFamily, qualifier, Bytes.toBytes(data));
    }

    /**
     * get a row identified by rowKey
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param rowKey
     * @throws Exception
     */
    public Result getRow(HTable table, String rowKey) throws Exception {
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        System.out.println("Get: "+result);
        return result;
    }

    /**
     * get a row identified by rowKey
     * @param tableName
     * @param rowKey
     * @throws Exception
     */
    public Result getRow(String tableName, String rowKey) throws Exception {
        HTable table = new HTable(conf, tableName);
        return getRow(table, rowKey);
    }

    /**
     * get a row identified by rowKey
     * @param table
     * @param rowKey
     * @throws Exception
     */
    public byte[] getBytesCell(HTable table, String rowKey, String columnFamily, String qualifier) throws Exception {
        Result r = getRow(table, rowKey);
        byte[] bytes = r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
        return bytes;
    }

    /**
     * get a row identified by rowKey
     * @param tableName
     * @param rowKey
     * @throws Exception
     */
    public byte[] getBytesCell(String tableName, String rowKey, String columnFamily, String qualifier) throws Exception {
        HTable table = new HTable(conf, tableName);
        return getBytesCell(table, rowKey, columnFamily, qualifier);
    }

    /**
     * get a row identified by rowKey
     * @param tableName
     * @param rowKey
     * @throws Exception
     */
    public String getStringCell(String tableName, String rowKey, String columnFamily, String qualifier) throws Exception {
        byte[] bytes = getBytesCell(tableName, rowKey, columnFamily, qualifier);
        return new String(bytes,"utf-8");
    }

    /**
     * delete a row identified by rowKey
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param rowKey
     * @throws Exception
     */
    public void deleteRow(HTable table, String rowKey) throws Exception {
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        table.delete(delete);
        System.out.println("Delete row: "+rowKey);
    }

    /**
     * delete a row identified by rowKey
     * @param tableName
     * @param rowKey
     * @throws Exception
     */
    public void deleteRow(String tableName, String rowKey) throws Exception {
        HTable table = new HTable(conf, tableName);
        deleteRow(table, rowKey);
    }

    /**
     * return all row from a table
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @throws Exception
     */
    public ResultScanner scanAll(HTable table) throws Exception {
        Scan s =new Scan();
        ResultScanner rs = table.getScanner(s);
        return rs;
    }

    /**
     * return all row from a table
     * @param tableName
     * @throws Exception
     */
    public ResultScanner scanAll(String tableName) throws Exception {
        HTable table = new HTable(conf, tableName);
        return scanAll(table);
    }

    /**
     * return a range of rows specified by startRow and endRow
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param startRow
     * @param endRow
     * @throws Exception
     */
    public ResultScanner scanRange(HTable table, String startRow, String endRow) throws Exception {
        Scan s =new Scan(Bytes.toBytes(startRow),Bytes.toBytes(endRow));
        ResultScanner rs = table.getScanner(s);
        return rs;
    }

    /**
     * return a range of rows specified by startRow and endRow
     * @param tableName
     * @param startRow
     * @param endRow
     * @throws Exception
     */
    public ResultScanner scanRange(String tableName, String startRow, String endRow) throws Exception {
        HTable table = new HTable(conf, tableName);
        return scanRange(table, startRow, endRow);
    }

    /**
     * return a range of rows filtered by specified condition
     * @param table, create by : HTable table = new HTable(conf, "tableName")
     * @param startRow
     * @param filter
     * @throws Exception
     */
    public ResultScanner scanFilter(HTable table, String startRow, Filter filter) throws Exception {
        Scan s =new Scan(Bytes.toBytes(startRow),filter);
        ResultScanner rs = table.getScanner(s);
        return rs;
    }

    /**
     * return a range of rows filtered by specified condition
     * @param tableName
     * @param startRow
     * @param filter
     * @throws Exception
     */
    public ResultScanner scanFilter(String tableName, String startRow, Filter filter) throws Exception {
        HTable table = new HTable(conf, tableName);
        return scanFilter(table, startRow, filter);
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        HBaseDao hbd = new HBaseDao();

        HTable table = new HTable(hbd.conf, "test_tb");
//        ResultScanner rs = hbd.scanRange("test_tb", "2013-07-10*", "2013-07-11*");
        ResultScanner rs = hbd.scanRange("test_tb", "100001", "100004");
//        ResultScanner rs = hbd.scanAll("test_tb");
        for(Result r:rs) {
            System.out.println("Scan: " + r);
        }
        table.close();

//        hbd.createTable("test_tb", "test_cf");
//        hbd.putCell("test_tb", "100001", "test_cf", "name", "yang");
//        hbd.putCell("test_tb", "100002", "test_cf", "name", "bolin");
//        hbd.putCell("test_tb", "100003", "test_cf", "name", "阳");
//        hbd.putCell("test_tb", "100004", "test_cf", "name", "波琳");
//        hbd.deleteRow("test_tb", "100003");
//        hbd.getRow("test_tb", "100004");
//        System.out.println(hbd.getStringCell("test_tb", "100004", "test_cf", "name"));
//        hbd.deleteTable("test_tb");

    }

}
