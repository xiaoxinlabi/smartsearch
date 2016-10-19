package info.puton.customize.hsb.crawler;

import info.puton.customize.hsb.crawler.component.CustomKeyDuplicateRemover;
import info.puton.customize.hsb.crawler.pipeline.PortalPipeline;
import info.puton.customize.hsb.mocker.LoginMocker;
import info.puton.product.smartsearch.model.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by taoyang on 2016/10/17.
 */
public class PortalCrawler implements PageProcessor {

    private String host = "http://portal.hsbank.com";
    private String userid = "wushuang";
    private String password = "11111111";

    private Site site;

    private LoginMocker loginMocker = new LoginMocker();

    public void findNextPage(Page page){
        //下一翻页（如果有）
        String nextPageXPath1 = "//*[@id=\"mainContent\"]/table/tbody/tr/td/table/tbody/tr/td/div/div/div/div[2]/span[3]/a/@href";
        String nextPageXPath2 = "//*[@id=\"mainContent\"]/table/tbody/tr/td[2]/table/tbody/tr/td/div/div/div/div[2]/span[3]/a";
        String nextPageLink1 = page.getHtml().xpath(nextPageXPath1).get();
        String nextPageLink2 = page.getHtml().xpath(nextPageXPath2).get();
        page.addTargetRequest(nextPageLink1);
        page.addTargetRequest(nextPageLink2);
    }

    public void findNextSide(Page page){
        //下一侧栏（如果有）
        String nextSideSelector = "#mainContent > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td > div > div > div > div.sidebar-block-con";
        String nextSideRegex = ".*WCM_GLOBAL_CONTEXT.*";
        page.addTargetRequests(page.getHtml().css(nextSideSelector).links().regex(nextSideRegex).all());
    }

    public void findTarget(Page page){
        //当前目标列表
        String linkArea1Selector = "#mainContent > table > tbody > tr > td > table > tbody > tr > td > div > div";
        String linkArea2Selector = "#mainContent > table > tbody > tr > td:nth-child(2) > table > tbody > tr > td > div > div";
        String linkRegex = ".*HSContent.*";
        page.addTargetRequests(page.getHtml().css(linkArea1Selector).links().regex(linkRegex).all());
        page.addTargetRequests(page.getHtml().css(linkArea2Selector).links().regex(linkRegex).all());
    }

    public void handleTarget(Page page){
        String url = page.getUrl().get();
        String title = page.getHtml().css("#mainContent > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > div > h3", "text").get();
//            String keywords = page.getHtml().xpath("/html/head/meta[@name=\"keywords\"]/@content").toString();
        String description = page.getHtml().css("#mainContent > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > div > p > script").get();
        description = description.substring(description.indexOf("str=\"")+5,description.indexOf("\";"));
        String content = page.getHtml().css("#mainContent > table > tbody > tr > td > table > tbody > tr:nth-child(3)").xpath("allText()").get();
        content = content
                .replaceAll("\\n+", " ")
                .replaceAll("\\r+", " ")
                .replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ");

        Website website = new Website();
        website.setUrl(url);
        website.setTitle(title);
//            website.setKeywords(keywords);
        website.setDescription(description);
        website.setContent(content);
        if(title!=null && !"".equals(title)){
            page.putField("website", website);
        }
    }

    @Override
    public void process(Page page) {

//        System.out.println(page.toString());

        if(page.getUrl().regex(".*HSContent.*").match() && !page.getUrl().regex(".*WCM_PORTLET.*").match()) {
            //最终
            handleTarget(page);
        } else if (page.getUrl().regex(".*WCM_Page.*").match()){
            //翻页
            findTarget(page);
            findNextPage(page);
        } else if (page.getUrl().regex(".*WCM_PORTLET.*").match()){
            //侧栏页面右边寻列表
            findTarget(page);
        } else if (page.getUrl().regex(".*myportal.*").match()) {
            //首次
            findNextPage(page);
            findNextSide(page);
            findTarget(page);
        }

    }

    @Override
    public Site getSite() {
        try {
            site = Site.me()
                    .setRetryTimes(3)
                    .setSleepTime(1000);
            String html = loginMocker.getHtmlByUrl(host+"/wps/myportal/");
            Document doc = Jsoup.parse(html);
            Element form = doc.select("body > form").first();
            String url = form.attr("action");
//            System.out.println(url);
            Map params = new HashMap();
            params.put("userid",userid);
            params.put("password",password);
            Map cookieMap = loginMocker.getCookie(host+url,params);
            Set<String> keySet = cookieMap.keySet();
            for (String name : keySet) {
                site = site.addCookie(name, (String) cookieMap.get(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static void main(String[] args) {

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

//                "http://portal.hsbank.com/wps/myportal/420xiangmu",//“四二〇”项目
//                "http://portal.hsbank.com/wps/myportal/gaigetuijinnian",//改革推进年
//                "http://portal.hsbank.com/wps/myportal/liangxueyizuo",//“两学一做”学习教育
//                "http://portal.hsbank.com/wps/myportal/puhuijinrong",//普惠金融
                "http://portal.hsbank.com/wps/myportal/826gongcheng",//“826”工程
//                "http://portal.hsbank.com/wps/myportal/heguijianshenian",//合规建设年
//                "http://portal.hsbank.com/wps/myportal/heguineikongxinxifabu",//内控体系建设
//                "http://portal.hsbank.com/wps/myportal/baomixuanchuanjiaoyu",//保密宣传教育
//                "http://portal.hsbank.com/wps/myportal/dangqunluxian",//党的群众路线教育实践活动
//                "http://portal.hsbank.com/wps/myportal/wangdianzhuanxingzhuanti",//网点转型专题
//                "http://portal.hsbank.com/wps/myportal/sanyansanshi",//“三严三实”专题教育
//                "http://portal.hsbank.com/wps/myportal/lianggeyipizhuanxianghuodong",//“两个一批”专项活动

        };

        Spider spider = Spider.create(new PortalCrawler());
        spider.addPipeline(new PortalPipeline());
        spider.thread(5);
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new CustomKeyDuplicateRemover()));
        spider.addUrl(urls);
        spider.run();
    }

}
