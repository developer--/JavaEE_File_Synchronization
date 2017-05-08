package repository.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by root on 5/5/17.
 */
@XmlRootElement
public class PassworChangeModel {
    @XmlElement public String username;
    @XmlElement public String oldPassword;
    @XmlElement public String newPassword;
}
