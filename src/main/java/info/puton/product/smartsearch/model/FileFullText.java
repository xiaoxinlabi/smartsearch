package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class FileFullText {

    private String id;

    private String type;

    private String fileName;

    private String author;

    private String modifyDate;

    private String content;

    public FileFullText() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileFullText{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                ", author='" + author + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
