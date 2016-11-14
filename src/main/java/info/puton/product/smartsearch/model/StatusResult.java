package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/11/14.
 */
public class StatusResult {

    private String status;

    private String description;

    public StatusResult() {
    }

    public StatusResult(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StatusResult{" +
                "status=" + status +
                ", description='" + description + '\'' +
                '}';
    }

}
