package info.puton.product.smartsearch.service;

import java.net.ConnectException;

/**
 * Created by taoyang on 2015/11/25.
 */
public interface IConvertService {

    void doc2pdf(String docPath, String pdfPath) throws ConnectException;

}
