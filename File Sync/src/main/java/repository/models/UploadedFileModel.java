package repository.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by root on 5/1/17.
 */
@XmlRootElement
public class UploadedFileModel {

    private long videoId;
    private String fileName;
    private String sessionId;
    private String folderName;
    private String userName;
    private String fullPath;

    public UploadedFileModel(long videoId,String fileName,String fullPath, String userName,String folderName){
        this.videoId = videoId;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.folderName = folderName;
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getVideoId() {
        return videoId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getUserName() {
        return userName;
    }
}
