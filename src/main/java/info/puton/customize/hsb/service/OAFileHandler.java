package info.puton.customize.hsb.service;

import java.rmi.RemoteException;

/**
 * Created by taoyang on 2016/11/4.
 */
public interface OAFileHandler {

    void handleFile(String dateStr);

    void handleDocListJson(String docListJson, String dateStr) throws RemoteException;

    void handleDocInfoJson(String docInfoJson, String dateStr);

}
