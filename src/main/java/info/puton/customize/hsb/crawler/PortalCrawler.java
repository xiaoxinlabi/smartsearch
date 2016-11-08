package info.puton.customize.hsb.crawler;

import info.puton.customize.hsb.crawler.component.CustomKeyDuplicateRemover;
import info.puton.customize.hsb.crawler.pipeline.PortalPipeline;
import info.puton.customize.hsb.mocker.LoginMocker;
import info.puton.product.smartsearch.model.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taoyang on 2016/10/17.
 */
@Component()
public class PortalCrawler implements PageProcessor {

    @Autowired
    PortalPipeline portalPipeline;

    private Boolean crawlForAll;
    private String crawlDate;

    public void setCrawlForAll(Boolean crawlForAll) {
        this.crawlForAll = crawlForAll;
    }

    public void setCrawlDate(String crawlDate) {
        this.crawlDate = crawlDate;
    }

    private Site site;

    private LoginMocker loginMocker = new LoginMocker();

    public void findNextPage(Page page){
        if(crawlForAll){
            //下一翻页（如果有）
            String nextPageXPath1 = "//*[@id=\"mainContent\"]/table/tbody/tr/td/table/tbody/tr/td/div/div/div/div[2]/span[3]/a/@href";
            String nextPageXPath2 = "//*[@id=\"mainContent\"]/table/tbody/tr/td[2]/table/tbody/tr/td/div/div/div/div[2]/span[3]/a";
            String nextPageLink1 = page.getHtml().xpath(nextPageXPath1).get();
            String nextPageLink2 = page.getHtml().xpath(nextPageXPath2).get();
            page.addTargetRequest(nextPageLink1);
            page.addTargetRequest(nextPageLink2);
        }
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
//        System.out.println(description);

        if(!crawlForAll){
            Pattern p = Pattern.compile("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}");
            Matcher m = p.matcher(description);
            while (m.find()){
                String postDate = m.group();
                if(!postDate.equals(crawlDate)){
                    return;
                }
            }
        }

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

//            Map cookieMap = loginMocker.getCookie(host+url,params);
            Map cookieMap = loginMocker.getCookie();
            Set<String> keySet = cookieMap.keySet();
            for (String name : keySet) {
                site = site.addCookie(name, (String) cookieMap.get(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return site;
    }

    public void crawlForAll(String... urls){

        setCrawlForAll(true);

        Spider spider = Spider.create(this);
        spider.addPipeline(portalPipeline);
        spider.thread(5);
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new CustomKeyDuplicateRemover()));
        spider.addUrl(urls);
        spider.run();
    }

    public void crawlByDate(String date, String... urls){

        System.out.println("crawling for " + date);

        setCrawlForAll(false);
        setCrawlDate(date);
        Spider spider = Spider.create(this);
        spider.addPipeline(portalPipeline);
        spider.thread(5);
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new CustomKeyDuplicateRemover()));
        spider.addUrl(urls);
        spider.run();
    }

}
