package repository.models;

/**
 * Created by root on 5/1/17.
 */
public class UploadedFileModel {

    private String fileName;
    private String sessionId;
    private String folderName;
    private String userName;
    private String fullPath;

    public UploadedFileModel(String fileName,String sessionId,String fullPath, String userName,String folderName){
        this.fileName = fileName;
        this.sessionId = sessionId;
        this.fullPath = fullPath;
        this.folderName = folderName;
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
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
