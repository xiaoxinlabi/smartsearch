package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class SearchResult<T> {

    private String type;

    private T detail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "type='" + type + '\'' +
                ", detail=" + detail +
                '}';
    }

}
