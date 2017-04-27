package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Created by root on 4/27/17.
 */
@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.resp = resp;
        final String userName = req.getParameter("username");
        final String password = req.getParameter("password");
        readDataBase(userName,password);
    }

    private void readDataBase(final String userName, final String password){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:drive;create=true");
            statement = connection.createStatement();
            createIfNotExist("ACCOUNTS");
            resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS");
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
                if (registered){
                    resp.getWriter().write("success");
                }else {
                    resp.getWriter().write("login failed");
                }
            } else {
                resp.getWriter().write("database is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resp.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }finally {
            if (connection != null)
                try {
                    connection.close();
                }catch (Exception e){
                   e.printStackTrace();
                }
        }
    }
    private void createIfNotExist(final String tableName){
        if (connection != null){
            try {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null,null,tableName.toUpperCase(),null);
                if (!rs.next()){
                    statement.executeUpdate("CREATE TABLE " + tableName + " (ID INT, username VARCHAR(15), password VARCHAR(20) )");
                }
                statement.execute("INSERT INTO " + tableName + " VALUES (1, '123','123')");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
