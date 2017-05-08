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
        final String mimeType = req.getParameter("mime_type");
        startMergingFiles(userName,folderName,mimeType);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private void startMergingFiles(final String username, final String folderName, final String mimeType){
        try {
            File[] files = new File(UploadServlet.videoPath + username + File.separator + folderName).listFiles();
            if (files != null && files.length > 0) {
                final String fullPath = UploadServlet.videoPath + username + File.separator + folderName + File.separator + files[0].getName();
                final String fileName = String.valueOf(System.currentTimeMillis());
                if (new File(fullPath).exists()) {
                    FileManager.mergeFiles(fullPath,
                            UploadServlet.videoPath + username + File.separator + folderName + File.separator + fileName + mimeType);
                    DBManager.getInstance().saveFile("", username, fullPath, folderName, fileName);
                }
                final File folder = new File( UploadServlet.videoPath + username + File.separator + folderName);
                if (folder.exists()){
                    final File [] childs = folder.listFiles();
                    if (childs != null && childs.length > 1){
                        final File f1 = childs[0];
                        final File f2 = childs[1];
                        if (f1.length() >= f2.length()){
                            f2.delete();
                        }else {
                            f1.delete();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}