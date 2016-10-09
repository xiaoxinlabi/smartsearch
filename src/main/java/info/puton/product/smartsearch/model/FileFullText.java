package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class FileFullText extends BaseSearchResult {

    private String fileName;

    private Long size;

    private String author;

    private String lastModified;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
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
                ", size='" + size + '\'' +
                ", author='" + author + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
