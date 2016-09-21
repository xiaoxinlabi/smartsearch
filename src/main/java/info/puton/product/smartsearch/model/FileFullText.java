package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/9/18.
 */
public class FileFullText {

    private String fileKey;

    private String fileName;

    private String author;

    private String modifyDate;

    private String content;

    public FileFullText() {
    }

    public FileFullText(String fileKey, String fileName, String author, String modifyDate, String content) {
        this.fileKey = fileKey;
        this.fileName = fileName;
        this.author = author;
        this.modifyDate = modifyDate;
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileFullText{" +
                "fileKey='" + fileKey + '\'' +
                ", fileName='" + fileName + '\'' +
                ", author='" + author + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
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


}
