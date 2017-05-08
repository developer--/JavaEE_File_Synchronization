package servlet;

import repository.manager.DBManager;

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
    static {
        DBManager.getInstance().conectToDB();
    }

    public static final String videoPath = "/root/glassfish4/glassfish/domains/domain1/docroot/files/videos/";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final String userName = request.getParameter("userName");
            final String password = request.getParameter("password");

//            String userName = "admin";
//            String password = "123";
            if (DBManager.getInstance().checkIfCanLogin(userName, password)) {

                final Part part = (Part) request.getParts().toArray()[1];
                final int substrIndex = part.getName().indexOf("_");
                final String folderName = part.getName().substring(0, substrIndex);
                new Thread(() -> {
                    byte[] buffer = new byte[1024 * 1024];
                    try {
                        File userDir = new File(videoPath + userName.replace("\"",""));
                        if (!userDir.exists())
                            userDir.mkdir();
                        File fileDir = new File(userDir.getPath() +File.separator + folderName);
                        if (!fileDir.exists())
                            fileDir.mkdir();
                        File targetFile = new File(fileDir.getPath() + File.separator + part.getName());
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
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("hi");
    }
}

