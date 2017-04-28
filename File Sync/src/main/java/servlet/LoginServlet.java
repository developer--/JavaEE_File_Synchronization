package servlet;

import repository.manager.DBManager;

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

            connection = DBManager.getInstance().conectToDB();
            boolean registered = DBManager.getInstance().checkIfCanLogin(userName,password);

            if (registered){
                resp.getWriter().write("success");
            }else {
                resp.getWriter().write("login failed");
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

}
