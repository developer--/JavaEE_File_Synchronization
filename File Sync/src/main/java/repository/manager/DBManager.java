package repository.manager;

import org.jetbrains.annotations.Nullable;
import repository.models.User;

import java.sql.*;

/**
 * Created by root on 4/28/17.
 */
public class DBManager {
    private static DBManager instance;
    private Connection connection;
    private Statement statement;
    private static final String USER_TABLE_NAME = "TEST_TABLE";
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

    public void createIfNotExist(final String tableName){
        if (connection != null){
            try {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null,null,tableName.toUpperCase(),null);
                if (!rs.next()){
                    statement.executeUpdate("CREATE TABLE "+ tableName +" (" +
                            "  ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ("+"START WITH 1, INCREMENT BY 1), username VARCHAR(15), password VARCHAR(100)" + ")");
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
            try {
                statement.execute("INSERT INTO "+USER_TABLE_NAME +" (username,password) VALUES ('" + userName +"','" + password +"')");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
                    if (userNameDd.equals(userName) && passwordDb.equals(password)) {
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

    public void closeConnection(){
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
