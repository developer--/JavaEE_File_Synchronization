package servlet;

import manager.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
/**
 * Created by user-00 on 4/7/17.
 */

@WebServlet("/merge")
public class MergingServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String folderName = req.getParameter("folderName");
        startMergingFiles(folderName);
    }

    private void startMergingFiles(final String folderName){
        try {
            File[] files = new File(UploadServlet.videoPath + File.separator + folderName).listFiles();
            if (files != null && files.length > 0) {
                FileManager.mergeFiles(UploadServlet.videoPath + File.separator + folderName + File.separator + files[0].getName(), UploadServlet.videoPath + File.separator + folderName + File.separator + System.currentTimeMillis() + ".mp4");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}