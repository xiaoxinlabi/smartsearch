package info.puton.customize.hsb.fetcher;

import info.puton.customize.hsb.axis.*;

import org.apache.axis2.transport.http.HTTPConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.rmi.RemoteException;

/**
 * Created by taoyang on 2016/11/1.
 */
public class FileFetcher {

    static long timeOutInMilliSeconds = 20000;

    static String wsdlUrl = "http://zhoa.hsbank.com/oa2/searchdb.nsf/doSearchService?WSDL";

    public static String getDocListJsonByDate(String date) throws RemoteException {
        DoSearchServiceStub searchServiceStub = new DoSearchServiceStub();
        searchServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeOutInMilliSeconds);
        searchServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED,false);
        GETDOCLISTSBYDATE getdoclistsbydate = new GETDOCLISTSBYDATE();
        getdoclistsbydate.setDATESTR(date);
        GETDOCLISTSBYDATEResponse getdoclistsbydateResponse = searchServiceStub.gETDOCLISTSBYDATE(getdoclistsbydate);
        String docListJson = getdoclistsbydateResponse.getGETDOCLISTSBYDATEReturn();
        return docListJson;
    }

    public static String getDocInfoJsonByUnid(String dateStr,String dbName, String docUnid) throws RemoteException {
        DoSearchServiceStub searchServiceStub = new DoSearchServiceStub();
        searchServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeOutInMilliSeconds);
        searchServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED,false);
        GETDOCINFOBYUNID getdocinfobyunid = new GETDOCINFOBYUNID();
        getdocinfobyunid.setDATESTR(dateStr);
        getdocinfobyunid.setDBNAME(dbName);
        getdocinfobyunid.setUNID(docUnid);
        GETDOCINFOBYUNIDResponse getdocinfobyunidresponse = searchServiceStub.gETDOCINFOBYUNID(getdocinfobyunid);
        String docInfoJson = getdocinfobyunidresponse.getGETDOCINFOBYUNIDReturn();
        return docInfoJson;
    }

    public static void handleDocListJson(String docListJson, String dateStr) throws RemoteException {
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
            String docInfoJson = getDocInfoJsonByUnid(dateStr, dbName, docUnid);
            handleDocInfoJson(docInfoJson);
        }
    }

    public static void handleDocInfoJson(String docInfoJson){
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
        System.out.println(id);
        System.out.println(readers);
        JSONArray attachs = result.getJSONArray("attachs");
        for (int i = 0; i < attachs.length(); i++) {
            JSONObject file = attachs.getJSONObject(i);
            String name = file.getString("Name");
            String url = file.getString("url");
            System.out.println(name);
            System.out.println(url);
        }

    }

    public static void main(String[] args) throws RemoteException {
        String dateStr = "2015-11-27";
        String docListJson = getDocListJsonByDate(dateStr);
        handleDocListJson(docListJson, dateStr);

    }

}
