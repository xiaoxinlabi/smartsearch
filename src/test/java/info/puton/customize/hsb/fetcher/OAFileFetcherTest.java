package info.puton.customize.hsb.fetcher;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/11/8.
 */
public class OAFileFetcherTest extends TestSupport {

    @Autowired
    OAFileFetcher oaFileFetcher;

    @Test
    public void testDownload() throws Exception {

        String url = "http://zhoa.hsbank.com/oa2/recfile.nsf/0/48257EFF0025C06F48257F060028F4B5/$file/徽商银行办公计算机资源申请单.docx";

        oaFileFetcher.download(url,"E://tmp/down/balabala");

    }
}