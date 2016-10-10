package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class FileFullText extends BaseSearchResult {

    private String fileName;

    private Long size;

    private Long lastModified;

    private String content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileFullText() {
    }

    @Override
    public String toString() {
        return "FileFullText{" +
                "fileName='" + fileName + '\'' +
                ", size=" + size +
                ", lastModified='" + lastModified + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
