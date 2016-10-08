package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/29.
 */
public class BaseSearchResult<T> {

    private String index;

    private String type;

    private String id;

    private Double score;

    private T source;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public BaseSearchResult(String index, String type, String id, Double score, T source) {
        this.index = index;
        this.type = type;
        this.id = id;
        this.score = score;
        this.source = source;
    }

    public BaseSearchResult() {
    }

    @Override
    public String toString() {
        return "BaseSearchResult{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", score=" + score +
                ", source=" + source +
                '}';
    }
}
