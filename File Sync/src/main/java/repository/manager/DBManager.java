package repository.manager;

import org.jetbrains.annotations.Nullable;
import repository.models.UploadedFileModel;
import repository.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/28/17.
 */
public class DBManager {
    private static DBManager instance;
    private Connection connection;
    private static Statement statement;
    public static final String USER_TABLE_NAME = "TEST_TABLE_1";
    public static final String VIDEO_TABLE_NAME = "videos_table_3";

    public static final String users_table_sql = "CREATE TABLE " + USER_TABLE_NAME +" (" +
            "  ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ("+"START WITH 1, INCREMENT BY 1), username VARCHAR(15), password VARCHAR(100), session_id VARCHAR(100)" + ")";
    public static final String video_table_sql = "CREATE TABLE " + VIDEO_TABLE_NAME + " ( ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( START WITH 1, INCREMENT BY 1 ), session_id VARCHAR(100), user_name VARCHAR(50), fullPath VARCHAR(300), fileName VARCHAR(100), folderName VARCHAR(200) )";


    private DBManager(){}
    public static DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    @Nullable
    public Connection conectToDB(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:drive;create=true");
            statement = connection.createStatement();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createIfNotExist(final String tableName, final String sql){
        if (connection != null){
            try {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null,null,tableName.toUpperCase(),null);
                if (!rs.next()){
                    statement.executeUpdate(sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean registerAccount(final User user){
        if (statement != null){
            final String userName = user.getUserName();
            final String password = user.getPassword();
            final String session_id = user.getSessionId();
            try {
                statement.execute("INSERT INTO "+USER_TABLE_NAME +" (username,password,session_id) VALUES ('" + userName +"','" + password +"','"+session_id +"')");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void updateSessionId(final String userName, String sessionId){
        if (connection != null){
            try {
                PreparedStatement prepSt = connection.prepareStatement("UPDATE "+USER_TABLE_NAME +" SET session_id = ? WHERE username = ?");
                prepSt.setString(1,sessionId);
                prepSt.setString(2,userName);
                prepSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkIfUserExist(final String userName){
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM "+USER_TABLE_NAME+"");
            boolean exist = false;
            if (resultSet != null) {
                while (resultSet.next()) {
                    final String userNameDd = resultSet.getString("username");
                    if (userNameDd.equals(userName)) {
                        exist = true;
                        break;
                    } else {
                        exist = false;
                    }
                }
                return exist;
            } else {
                return false;
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkIfCanLogin(final String userName, final String password){
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM " + USER_TABLE_NAME);
            boolean registered = false;
            if (resultSet != null) {
                while (resultSet.next()) {
                    final String userNameDd = resultSet.getString("username");
                    final String passwordDb = resultSet.getString("password");
                    if (userNameDd.equals(userName.replace("\"","")) && passwordDb.equals(password.replace("\"",""))) {
                        registered = true;
                        break;
                    } else {
                        registered = false;
                    }
                }
                return registered;
            } else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAccounts(){
        final List<User> users = new ArrayList<>();
        try {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + USER_TABLE_NAME);
            while (resultSet.next()){
                final String userName = resultSet.getString("username");
                final long id = resultSet.getLong("id");
                final String password = resultSet.getString("password");
                final String sessionId = resultSet.getString("session_id");
                final User user = new User(id,userName,password,sessionId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public long getUserId(final String userName){
        try {
            final ResultSet resultSet = statement.executeQuery("SELECT ID from " + USER_TABLE_NAME + " WHERE username = '" + userName + "'");
            while (resultSet.next()){
                return resultSet.getLong("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void closeConnection(){
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void saveFile(String sessionId,String userName,String fullPath, String folderName, String fileName) {
        if (connection != null){
            try {
                PreparedStatement prst = connection.prepareStatement("INSERT INTO " + VIDEO_TABLE_NAME + " (session_id,user_name,fullPath, folderName,fileName) VALUES (?,?,?,?,?)");
                prst.setString(1,sessionId);
                prst.setString(2,userName);
                prst.setString(3,fullPath);
                prst.setString(4,folderName);
                prst.setString(5,fileName);
                prst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UploadedFileModel> getUploadidFiles(final String userName){
        final List<UploadedFileModel> files = new ArrayList<>();
        try {
            if (connection.isClosed())
                conectToDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (statement != null){
            try {
                final String sql = "SELECT * FROM "+ VIDEO_TABLE_NAME + " where user_name = ?";
                PreparedStatement prst = connection.prepareStatement(sql);
                prst.setString(1,userName);
                ResultSet rst = prst.executeQuery();
                while (rst.next()){
                    final String fileName = rst.getString("fileName");
                    final String folderName = rst.getString("folderName");
                    final String fullPath = rst.getString("fullPath");
                    final UploadedFileModel model = new UploadedFileModel(fileName,"",fullPath,userName,folderName);
                    files.add(model);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

}
