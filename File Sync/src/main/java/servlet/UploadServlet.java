package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    public static final String videoPath = "/root/Documents/videos/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Part part = (Part) request.getParts().toArray()[1];
        final int substrIndex = part.getName().indexOf("_");
        final String folderName = part.getName().substring(0,substrIndex);
        new Thread(() -> {
            byte[] buffer = new byte[1024 * 1024];
            try {
                File dir = new File(videoPath + folderName);
                if (!dir.exists())
                    dir.mkdir();
                File targetFile = new File(dir.getPath() +File.separator + part.getName());
                try (BufferedInputStream bis = new BufferedInputStream(part.getInputStream())) {
                    int tmp;
                    while ((tmp = bis.read(buffer)) > 0) {
                        try (FileOutputStream out = new FileOutputStream(targetFile)) {
                            out.write(buffer, 0, tmp);
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            }
        }).start();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("hi");
    }
}

