package info.puton.customize.hsb.scheduler.job;

import info.puton.customize.hsb.crawler.PortalCrawler;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by taoyang on 2016/10/20.
 */
@Component()
public class CrawlPortalJob extends QuartzJobBean {

    @Autowired
    PortalCrawler portalCrawler = new PortalCrawler();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String[] urls = {

//                "http://portal.hsbank.com/wps/myportal/tupianxinwen",//图片新闻
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date0 = new Date();//当前
        Date date1 = calendar.getTime();//前一天
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        portalCrawler.crawlByDate(df.format(date0),urls);
        portalCrawler.crawlByDate(df.format(date1),urls);

    }
}
