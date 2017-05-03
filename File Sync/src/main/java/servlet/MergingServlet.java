package servlet;

import manager.FileManager;
import repository.manager.DBManager;

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

    static {
        DBManager.getInstance().conectToDB();
        DBManager.getInstance().createIfNotExist(DBManager.VIDEO_TABLE_NAME, DBManager.video_table_sql);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String userName = req.getParameter("userName");
        final String folderName = req.getParameter("folderName");
        startMergingFiles(userName,folderName);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private void startMergingFiles(final String username, final String folderName){
        try {
            File[] files = new File(UploadServlet.videoPath + File.separator + folderName).listFiles();
            if (files != null && files.length > 0) {
                final String fullPath = UploadServlet.videoPath + File.separator + folderName + File.separator + files[0].getName();
                final String fileName = String.valueOf(System.currentTimeMillis());
                if (new File(fullPath).exists()) {
                    FileManager.mergeFiles(fullPath,
                            UploadServlet.videoPath + File.separator + folderName + File.separator + fileName + ".mp4");
                    DBManager.getInstance().saveFile("", username, fullPath, folderName, fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}