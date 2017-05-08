package repository.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by root on 5/4/17.
 */
@XmlRootElement
public class DeleteObjectIDModel {
    private String fileId;

    public String getFileId() {
        return fileId;
    }
}
