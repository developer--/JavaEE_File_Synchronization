package servlet;

import repository.manager.DBManager;
import repository.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 5/1/17.
 */
@WebServlet
public class AccountsTableServlet extends HttpServlet {
    private final static String html = "<html>" +
            "<head> " +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
            "</head>"+
            "<body class=\"center\">"+
            "<table>";

    static {
        DBManager.getInstance().conectToDB();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final List<User> users = DBManager.getInstance().getAccounts();
        StringBuilder result = new StringBuilder(html);
        for (final User user : users) {
            result.append("<tr><td>")
                    .append(user.getId())
                    .append("</td><td>")
                    .append(user.getUserName())
                    .append("</td><td>")
                    .append(user.getPassword())
                    .append("</td><td>")
                    .append(user.getSessionId())
                    .append("</td></tr>");
        }
        result.append("</table></body></html>");
        resp.getWriter().write(result.toString());
    }
}
