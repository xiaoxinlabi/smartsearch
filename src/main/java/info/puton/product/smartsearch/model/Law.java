package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/10/11.
 */
public class Law extends BaseSearchResult {

    private String id; //账号

    private String lawname; //法律名

    private String itemtitle; //条目标题

    private String itemcontent;  //条目内容

    private String content; //高亮内容

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle;
    }

    public String getItemcontent() {
        return itemcontent;
    }

    public void setItemcontent(String itemcontent) {
        this.itemcontent = itemcontent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Law() {
    }

    @Override
    public String toString() {
        return "Law{" +
                "id='" + id + '\'' +
                ", lawname='" + lawname + '\'' +
                ", itemtitle='" + itemtitle + '\'' +
                ", itemcontent='" + itemcontent + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
