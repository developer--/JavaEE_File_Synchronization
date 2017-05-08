package servlet;

import repository.manager.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by root on 4/28/17.
 */
//@WebServlet
public class VideoPlaylistServlet extends HttpServlet {

    static {
        DBManager.getInstance().conectToDB();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
//        if (session != null && session.getAttribute("userId") != null){
//            req.getRequestDispatcher("/videos.jsp").include(req,resp);
//
//            final String userId = (String) req.getSession(false).getAttribute("userId");
//            final List<UploadedFileModel> files = DBManager.getInstance().getUploadidFiles(userId);
//
//            final String div = "<div style=\"width: 200px;height: 200px; margin: 30px; float: left; \">";
//            final String videoHtml = "<video width=\"200px\" height=\"200px\" controls><source src=\"/files/videos/";
//
//            final StringBuilder content = new StringBuilder();
//
//            for (int i = 0; i < files.size(); i++) {
//                final File file = new File(new File(files.get(i).getFullPath()).getParentFile() + File.separator + files.get(i).getFileName() + ".mp4");
//
//                if (file.exists()) {
//                    String video = div +"<p style=\"text-align: center\">"+files.get(i).getVideoId()+"</p><br><button onclick=\"window.location.href='/api/rest/hello?videoId="+files.get(i).getVideoId()+"'\">Delete</button>" +videoHtml + files.get(i).getUserName() + File.separator + files.get(i).getFolderName() + File.separator + files.get(i).getFileName() + ".mp4\" type=\"video/mp4\"></div>";
//                    if (i % 5 == 0)
//                        video += "\n";
//                    content.append(video);
//                }
//            }
//            resp.getWriter().write(content.toString());
//            resp.getWriter().write(String.valueOf(userId));
//        }else {
//            resp.sendRedirect("/api/login");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
