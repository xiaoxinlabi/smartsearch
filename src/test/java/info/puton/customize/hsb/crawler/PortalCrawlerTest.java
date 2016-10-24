package info.puton.customize.hsb.crawler;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/24.
 */
public class PortalCrawlerTest extends TestSupport {

    @Autowired
    PortalCrawler portalCrawler;

    String[] urls = {

//            "http://portal.hsbank.com/wps/myportal/tupianxinwen",//图片新闻
//                "http://portal.hsbank.com/wps/myportal/tongzhigonggao",//通知公告
//                "http://portal.hsbank.com/wps/myportal/huihangdongtai",//徽行要闻
//                "http://portal.hsbank.com/wps/myportal/lingdaoricheng",//领导动态
//                "http://portal.hsbank.com/wps/myportal/xinxikuaibao",//信息快递
//                "http://portal.hsbank.com/wps/myportal/bumenzhuanlan",//部门专栏
//                "http://portal.hsbank.com/wps/myportal/zuixinjianbao",//最新简报
//                "http://portal.hsbank.com/wps/myportal/gongzuojiaoliu",//工作交流
//                "http://portal.hsbank.com/wps/myportal/qiyewenhuajianshe",//企业文化建设
//
//                "http://portal.hsbank.com/wps/myportal/420xiangmu",//“四二〇”项目
//                "http://portal.hsbank.com/wps/myportal/gaigetuijinnian",//改革推进年
//                "http://portal.hsbank.com/wps/myportal/liangxueyizuo",//“两学一做”学习教育
//                "http://portal.hsbank.com/wps/myportal/puhuijinrong",//普惠金融
//                "http://portal.hsbank.com/wps/myportal/826gongcheng",//“826”工程
//                "http://portal.hsbank.com/wps/myportal/heguijianshenian",//合规建设年
//                "http://portal.hsbank.com/wps/myportal/heguineikongxinxifabu",//内控体系建设
//                "http://portal.hsbank.com/wps/myportal/baomixuanchuanjiaoyu",//保密宣传教育
//                "http://portal.hsbank.com/wps/myportal/dangqunluxian",//党的群众路线教育实践活动
//                "http://portal.hsbank.com/wps/myportal/wangdianzhuanxingzhuanti",//网点转型专题
//                "http://portal.hsbank.com/wps/myportal/sanyansanshi",//“三严三实”专题教育
//                "http://portal.hsbank.com/wps/myportal/lianggeyipizhuanxianghuodong",//“两个一批”专项活动

    };

    @Test
    public void testCrawlForAll() throws Exception {

        portalCrawler.crawlForAll(urls);

    }

    @Test
    public void testCrawlByDate() throws Exception {

        portalCrawler.crawlByDate("2016-10-18", urls);

    }
}