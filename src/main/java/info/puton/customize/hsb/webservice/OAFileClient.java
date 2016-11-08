package info.puton.customize.hsb.webservice;

import info.puton.customize.hsb.webservice.axis.*;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.stereotype.Component;
import java.rmi.RemoteException;

/**
 * Created by taoyang on 2016/11/1.
 */

@Component
public class OAFileClient {

    private long timeOutInMilliSeconds = 20000;

//    private String wsdlUrl = "http://zhoa.hsbank.com/oa2/searchdb.nsf/doSearchService?WSDL";

    public String getDocListJsonByDate(String date) throws RemoteException {
        DoSearchServiceStub searchServiceStub = new DoSearchServiceStub();
        searchServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeOutInMilliSeconds);
        searchServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED,false);
        GETDOCLISTSBYDATE getdoclistsbydate = new GETDOCLISTSBYDATE();
        getdoclistsbydate.setDATESTR(date);
        GETDOCLISTSBYDATEResponse getdoclistsbydateResponse = searchServiceStub.gETDOCLISTSBYDATE(getdoclistsbydate);
        String docListJson = getdoclistsbydateResponse.getGETDOCLISTSBYDATEReturn();
        return docListJson;
    }

    public String getDocInfoJsonByUnid(String dateStr,String dbName, String docUnid) throws RemoteException {
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

}
