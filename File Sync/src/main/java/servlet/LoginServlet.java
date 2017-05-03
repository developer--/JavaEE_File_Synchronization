package servlet;

import repository.manager.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

/**
 * Created by root on 4/27/17.
 */
@WebServlet
public class LoginServlet extends HttpServlet {

    private Connection connection;
    private HttpServletResponse resp;
    private HttpServletRequest request;
    private String isFromWeb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.request = req;
        this.resp = resp;
        final String userName = req.getParameter("username");
        final String password = req.getParameter("password");
        isFromWeb = req.getParameter("is_from_web");
        login(userName,password);
    }

    private void login(final String userName, final String password){
        try {
            connection = DBManager.getInstance().conectToDB();
            boolean registered = DBManager.getInstance().checkIfCanLogin(userName,password);
            final String sessionId = UUID.randomUUID().toString();

            if (registered){
                DBManager.getInstance().updateSessionId(userName,sessionId);
                if (isFromWeb != null) {
//                    long userId = DBManager.getInstance().getUserId(userName);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId",userName);
                    resp.sendRedirect("/api/videolist");
                }else {
                    resp.getWriter().write("{\"success\":true,\"token\":"+"\""+sessionId+"\"}");
                }
            }else {
                resp.getWriter().write("{\"success\":false,\"token\":"+"\""+" "+"\"}");
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
