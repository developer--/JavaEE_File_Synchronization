package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 4/28/17.
 */
@WebServlet
public class VideoPlaylistServlet extends HttpServlet {

    private static final String HEAD = "<head>\n" +
            "  <link href=\"http://vjs.zencdn.net/5.19.2/video-js.css\" rel=\"stylesheet\">\n" +
            "\n" +
            "  <!-- If you'd like to support IE8 -->\n" +
            "  <script src=\"http://vjs.zencdn.net/ie8/1.1.2/videojs-ie8.min.js\"></script>\n" +
            "</head>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String div = "  <div style=\"width: 200px;height: 200px; margin: 30px; float: left; \">" +
                "<video width=\"200px\" height=\"200px\" controls>" +
                   "<source src=\"/files/videos/1493303543658.mp4\" type=\"video/mp4\">"  +
                "</div>";
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0)
                div += "\n";
            content.append(div);
        }
        resp.getWriter().write(content.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
