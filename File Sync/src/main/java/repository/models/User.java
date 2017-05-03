package repository.models;

/**
 * Created by root on 4/27/17.
 */
public class User {
    private long id;
    private String userName;
    private String password;
    private String sessionId;

    public User(final long id, final String userName, final String password, final String sessionId){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.sessionId = sessionId;

    }

    public User(){}

    public long getId() {
        return id;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
