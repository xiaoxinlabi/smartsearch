package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/29.
 */
public class BaseSearchResult {

    private String index;

    private String type;

    private String id;

    private Float score;

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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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
                '}';
    }
}
