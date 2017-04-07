package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private long index = 0;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("videofile");
        InputStream fileContent = filePart.getInputStream();
        Files.copy(fileContent, new File("/home/user-00/videos/server/"+index).toPath(), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        index++;
//        response.sendRedirect("/merge");
    }
}