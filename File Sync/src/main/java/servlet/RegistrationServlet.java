package servlet;

import repository.manager.DBManager;
import repository.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 4/27/17.
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    static {
        DBManager.getInstance().conectToDB();
        DBManager.getInstance().createIfNotExist("TEST_TABLE");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String userName = req.getParameter("user_name");
        final String password = req.getParameter("password");
        final String repeatPassword = req.getParameter("repeat_pass");
        if (DBManager.getInstance().checkIfUserExist(userName)){
            resp.getWriter().write("already exist");
        }else {
            if (password.equals(repeatPassword)) {
                final User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                final boolean isRegistered = DBManager.getInstance().registerAccount(user);
                resp.getWriter().write(isRegistered+"");
            }else {
                resp.getWriter().write("password does not matches");
            }
        }

    }
}
