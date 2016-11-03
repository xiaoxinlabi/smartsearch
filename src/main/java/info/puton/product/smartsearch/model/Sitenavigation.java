package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/10/12.
 */
public class Sitenavigation extends BaseSearchResult {

    private String title;

    private String alias;

    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Sitenavigation() {
    }

    @Override
    public String toString() {
        return "SiteNavigator{" +
                "title='" + title + '\'' +
                ", alias='" + alias + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
