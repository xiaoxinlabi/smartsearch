package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class FileFullText extends BaseSearchResult {

    private String fileName;

    private String author;

    private String modifyDate;

    private String content;

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

    public FileFullText() {
    }

    @Override
    public String toString() {
        return super.toString() + " " + "FileFullText{" +
                "fileName='" + fileName + '\'' +
                ", author='" + author + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
