package info.puton.customize.hsb.crawler.component;

import com.google.common.collect.Sets;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by taoyang on 2016/10/18.
 */
public class CustomKeyDuplicateRemover implements DuplicateRemover {

    private Set<String> keys = Sets.newSetFromMap(new ConcurrentHashMap());

    @Override
    public boolean isDuplicate(Request request, Task task) {
        return !this.keys.add(this.getKey(request));
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        this.keys.clear();
    }

    protected String getKey(Request request) {
        String url = request.getUrl();
//        String identifier = "HSContent";
        String key = url;
//        if (url.contains(identifier)){
//            key = url.substring(url.indexOf(identifier) + identifier.length());
//        }
        key = url.substring(url.length()-32);
        System.out.println("key : " + key);
        return key;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return this.keys.size();
    }
}
