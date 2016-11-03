package info.puton.customize.hsb.fetcher;

//import info.puton.customize.hsb.axis.GETDOCLISTSBYDATEDocument;
//import info.puton.customize.hsb.axis.GETDOCLISTSBYDATEResponseDocument;
//import info.puton.customize.hsb.axis.impl.GETDOCLISTSBYDATEDocumentImpl;
//import info.puton.customize.hsb.axis.service.DoSearchServiceStub;
//import org.apache.axis2.AxisFault;
import org.apache.xmlbeans.SchemaType;

import java.rmi.RemoteException;

/**
 * Created by taoyang on 2016/11/1.
 */
public class FileFetcher {

    public static void main(String[] args) {

//        try {
//            DoSearchServiceStub doSearchServiceStub = new DoSearchServiceStub("http://zhoa.hsbank.com/oa2/searchdb.nsf/getdoclistsbydate?wsdl");
//            GETDOCLISTSBYDATEDocument reqDoc = GETDOCLISTSBYDATEDocument.Factory.newInstance();
//            GETDOCLISTSBYDATEDocument.GETDOCLISTSBYDATE getdoclistsbydate = reqDoc.addNewGETDOCLISTSBYDATE();
//            getdoclistsbydate.setDATESTR("20151127");
//            reqDoc.setGETDOCLISTSBYDATE(getdoclistsbydate);
//            GETDOCLISTSBYDATEResponseDocument respDoc = doSearchServiceStub.gETDOCLISTSBYDATE(reqDoc);
////            String resultStr = respDoc.getGETDOCLISTSBYDATEResponse().getGETDOCLISTSBYDATEReturn().toString();
////            System.out.println(resultStr);
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

    }

}
