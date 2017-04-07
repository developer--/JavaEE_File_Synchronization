/**
 * Created by user-00 on 4/7/17.
 */
public class UploadModel {

    private String filePath;
    private boolean isUploaded = false;
    private int size = 0;
    private int position;

    public UploadModel(String filePath, boolean isUploaded, int size, int position) {
        this.filePath = filePath;
        this.isUploaded = isUploaded;
        this.size = size;
        this.position = position;
    }

    public UploadModel() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
