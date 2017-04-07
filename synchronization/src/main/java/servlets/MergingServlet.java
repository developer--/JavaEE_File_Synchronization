package servlets;

import model.FileManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user-00 on 4/7/17.
 */

@WebServlet("/merge")
public class MergingServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
        startMergingFiles();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/merg.jsp").include(req, resp);
    }

    private void startMergingFiles(){
        try {
            FileManager.mergeFiles("/home/user-00/videos/server/1.mp4.001","/home/user-00/videos/server/origin.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
