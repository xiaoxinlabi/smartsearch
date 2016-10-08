package info.puton.product.smartsearch.dao;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/9/30.
 */
public class SmartSearchDaoTest extends TestSupport {

    @Autowired
    SmartSearchDao smartSearchDao;

    @Test
    public void testHighlightQuery() throws Exception {

//        System.out.println(smartSearchDao.highlightQuery("陶阳"));
//        System.out.println(smartSearchDao.highlightQuery("阳春面"));
//        System.out.println(smartSearchDao.highlightQuery("检索"));
        System.out.println(smartSearchDao.highlightQuery("简介"));

    }
}