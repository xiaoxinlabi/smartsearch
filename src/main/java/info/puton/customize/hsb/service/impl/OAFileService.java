package info.puton.customize.hsb.service.impl;

import info.puton.customize.hsb.fetcher.OAFileFetcher;
import info.puton.customize.hsb.service.OAFileHandler;
import info.puton.customize.hsb.webservice.OAFileClient;
import info.puton.product.smartsearch.service.FileHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 2016/11/4.
 */
@Service
public class OAFileService implements OAFileHandler {

    private String host = "http://zhoa.hsbank.com/";

    @Value("#{settings['oaFileLandingPath']}")
    private String oaFileLandingPath;

    @Autowired
    OAFileClient oaFileClient;

    @Autowired
    OAFileFetcher oaFileFetcher;

    @Autowired
    FileHandler fileHandler;

    @Override
    public void handleDocListJson(String docListJson, String dateStr) throws RemoteException {
        /*
        {
            Result: {
                ErrCode: '0',
                        TTotal: '13',
                        list: [
                {
                    DbName: 'oa2/recfile.nsf',
                            DocUnid: '6CB6E9995A8B572748257DC600276D8E',

                },
                {
                    DbName: 'oa2/signreport.nsf',
                            DocUnid: 'FB28831FE59B934A48257F0900311862',

                }
                ]
            }
        }
        */
        JSONObject jsonObject = new JSONObject(docListJson);
        JSONObject result = jsonObject.getJSONObject("Result");
        Long tTotal = Long.parseLong(result.getString("TTotal"));
        JSONArray list = result.getJSONArray("list");
        for (int i = 0; i < tTotal; i++) {
            JSONObject record = list.getJSONObject(i);
            String dbName = record.getString("DbName");
            String docUnid = record.getString("DocUnid");
            String docInfoJson = oaFileClient.getDocInfoJsonByUnid(dateStr, dbName, docUnid);
            handleDocInfoJson(docInfoJson, dateStr);
        }
    }

    @Override
    public void handleDocInfoJson(String docInfoJson, String dateStr) {
        /*
        {
            Result: {
                ErrCode: '0',
                        Id: '48257EFF0025C06F48257F060028F4B4',
                        Title: '',
                        Type: 'oa2/recfile.nsf',
                        Readers: 'hanxuejiao;MailUser;admin_hq;wangyusu;chentingting;xuyi;liushuai;zhoujuanjuan;fuyuling;zhangyeming;test;zhoujie2;huanghao5;yeweiming;xuyi4',
                        attModFlag: '0',
                        attachs: [
                {
                    Name: '徽商银行办公计算机资源申请单.docx',
                            url: 'oa2/recfile.nsf/0/48257EFF0025C06F48257F060028F4B5/$file/徽商银行办公计算机资源申请单.docx'
                },
                {
                    Name: '关于印发《徽商银行计算机网络管理办法》的通知.docx',
                            url: 'oa2/recfile.nsf/0/48257EFF0025C06F48257F060028F4B5/$file/关于印发《徽商银行计算机网络管理办法》的通知.docx'
                },
                {
                    Name: '徽商银行网络系统配置变更申请表.docx',
                            url: 'oa2/recfile.nsf/0/48257EFF0025C06F48257F060028F4B5/$file/徽商银行网络系统配置变更申请表.docx'
                }
                ]
            }
        }
        */
        JSONObject jsonObject = new JSONObject(docInfoJson);
        JSONObject result = jsonObject.getJSONObject("Result");
        String id = result.getString("Id");
        String readers = result.getString("Readers");
//        System.out.println("id : " + id);
//        System.out.println("readers : " + readers);
        JSONArray attachs = result.getJSONArray("attachs");
        for (int i = 0; i < attachs.length(); i++) {
            JSONObject file = attachs.getJSONObject(i);
            String name = file.getString("Name");
            String url = host + file.getString("url");
//            System.out.println("name : " + name);
//            System.out.println("url : " + url);
            String oaFileLocation = oaFileLandingPath + "/" + dateStr;
            try {
                oaFileFetcher.download(url, oaFileLocation);
                String oaFilePath = oaFileLocation + "/" + name;
                Map additional = new HashMap<>();
                String fileKey = DigestUtils.md5Hex(id+name);
                additional.put("fileKey", fileKey);
                additional.put("owner",readers);
                fileHandler.handleFile(oaFilePath, additional);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void handleFile(String dateStr) {

        String docListJson = null;
        try {
            docListJson = oaFileClient.getDocListJsonByDate(dateStr);
            handleDocListJson(docListJson, dateStr);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
